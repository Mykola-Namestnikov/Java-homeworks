package ua.university.sms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.model.dto.TeacherRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TeacherControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateTeacherSuccessfully() throws Exception {
        TeacherRequest request = new TeacherRequest("Олександр Петренко", "olexandr.p@university.ua", "Кафедра Математики");
        mockMvc.perform(post("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Олександр Петренко"))
                .andExpect(jsonPath("$.email").value("olexandr.p@university.ua"))
                .andExpect(jsonPath("$.department").value("Кафедра Математики"));
    }

    @Test
    public void shouldReturnBadRequestWhenTeacherEmailIsInvalid() throws Exception {
        TeacherRequest request = new TeacherRequest("Олександр Петренко", "wrong-email-format", "Кафедра Математики");
        mockMvc.perform(post("/api/teachers")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(request)))
                 .andExpect(status().isBadRequest()) // Очікуємо 400 Bad Request від GlobalExceptionHandler
                 .andExpect(jsonPath("$.message").value("Помилка валідації вхідних даних"))
                 .andExpect(jsonPath("$.details.email").exists());
    }

    @Test
    public void shouldReturnNotFoundForNonExistingTeacher() throws Exception {
        mockMvc.perform(get("/api/teachers/9999"))
                .andExpect(status().isNotFound());
    }
}
