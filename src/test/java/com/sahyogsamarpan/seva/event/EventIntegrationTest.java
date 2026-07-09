package com.sahyogsamarpan.seva.event;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.sahyogsamarpan.seva.user.Role;
import com.sahyogsamarpan.seva.user.User;
import com.sahyogsamarpan.seva.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        userRepository.deleteAll();

        User admin = new User();
        admin.setEmail("admin@test.com");
        admin.setPassword(passwordEncoder.encode("password123"));
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);
        userRepository.save(admin);
    }

    @Test
    void listEventsIsPublic() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createEventRequiresAuthentication() throws Exception {
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleEventJson("sample-event")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminCanCreateEvent() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(post("/api/events")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleEventJson("maharashtra-day-2026")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("maharashtra-day-2026"))
                .andExpect(jsonPath("$.title").value("Sample Event"));

        mockMvc.perform(get("/api/events/maharashtra-day-2026"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateLabel").value("1 May 2026"));
    }

    private String loginAndGetToken() throws Exception {
        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"admin@test.com","password":"password123"}
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return response.replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");
    }

    private String sampleEventJson(String slug) {
        return """
                {
                  "slug": "%s",
                  "title": "Sample Event",
                  "titleMr": "नमुना कार्यक्रम",
                  "date": "2026-05-01",
                  "dateLabel": "1 May 2026",
                  "location": "Ahilyanagar",
                  "photo": "/images/our-work/sample.png",
                  "description": ["First paragraph.", "Second paragraph."]
                }
                """.formatted(slug);
    }
}
