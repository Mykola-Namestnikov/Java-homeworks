package ua.university.sms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.model.dto.CourseRequest;
import ua.university.sms.model.entity.Teacher;
import ua.university.sms.repository.TeacherRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CourseControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void shouldCreateCourseSuccessfully() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setFullName("Андрій Шевченко");
        teacher.setEmail("sheva@university.ua");
        teacher.setDepartment("Кафедра Фізичного Виховання");
        teacher = teacherRepository.save(teacher);

        CourseRequest courseRequest = new CourseRequest("Алгоритми та структури даних", 6, teacher.getId());

        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Алгоритми та структури даних"))
                .andExpect(jsonPath("$.credits").value(6))
                .andExpect(jsonPath("$.teacherId").value(teacher.getId()));
    }

    @Test
    public void shouldReturnBadRequestWhenCreditsAreTooHigh() throws Exception {
        CourseRequest courseRequest = new CourseRequest("Супер Складний Курс", 15, 1L);

        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.credits").exists());
    }
}
