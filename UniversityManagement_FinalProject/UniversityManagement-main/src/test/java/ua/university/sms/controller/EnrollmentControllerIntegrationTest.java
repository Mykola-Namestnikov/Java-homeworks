package ua.university.sms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.model.dto.EnrollmentRequest;
import ua.university.sms.model.entity.Course;
import ua.university.sms.model.entity.Student;
import ua.university.sms.model.entity.Enrollment;
import ua.university.sms.repository.CourseRepository;
import ua.university.sms.repository.StudentRepository;
import ua.university.sms.repository.EnrollmentRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EnrollmentControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    public void shouldEnrollStudentToCourse() throws Exception {
        Student student = new Student();
        student.setFullName("Максим Бондар");
        student.setEmail("max@mail.com");
        student.setStudyYear(2);
        student.setStatus("Active");
        student.setGpa(0.0);
        student = studentRepository.save(student);

        Course course = new Course();
        course.setTitle("Бази Даних");
        course.setCredits(4);
        course = courseRepository.save(course);

        EnrollmentRequest request = new EnrollmentRequest(student.getId(), course.getId());

        mockMvc.perform(post("/api/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value(student.getId()))
                .andExpect(jsonPath("$.courseId").value(course.getId()))
                .andExpect(jsonPath("$.paid").value(false)); // За замовчуванням має бути false
    }

    @Test
    public void shouldUpdateGradeAndRecalculateStudentGpa() throws Exception {
        Student student = new Student();
        student.setFullName("Ірина Кравченко");
        student.setEmail("ira@mail.com");
        student.setStudyYear(3);
        student.setStatus("Active");
        student.setGpa(0.0);
        student = studentRepository.save(student);

        Course course = new Course();
        course.setTitle("Веб-програмування");
        course.setCredits(5);
        course = courseRepository.save(course);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setPaid(true);
        enrollment = enrollmentRepository.save(enrollment);

        mockMvc.perform(put("/api/enrollments/" + enrollment.getId() + "/grade")
                .param("grade", "4.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value(4.5));

        Student finalStudent = student;
        mockMvc.perform(post("/api/enrollments"))
                .andDo(result -> {
                    Student updatedStudent = studentRepository.findById(finalStudent.getId()).orElseThrow();
                    assert updatedStudent.getGpa() == 4.5;
                });
    }

    @Test
    public void shouldUpdatePaymentStatus() throws Exception {
        Student student = new Student();
        student.setFullName("Тест"); student.setEmail("test@test.com"); student.setStudyYear(1); student.setStatus("Active");
        student = studentRepository.save(student);
        Course course = new Course(); course.setTitle("Тест Курс"); course.setCredits(2);
        course = courseRepository.save(course);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student); enrollment.setCourse(course); enrollment.setPaid(false);
        enrollment = enrollmentRepository.save(enrollment);

        mockMvc.perform(put("/api/enrollments/" + enrollment.getId() + "/payment")
                .param("paid", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paid").value(true));
    }
}
