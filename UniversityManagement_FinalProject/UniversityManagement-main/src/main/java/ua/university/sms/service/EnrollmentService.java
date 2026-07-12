package ua.university.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.EnrollmentRequest;
import ua.university.sms.model.dto.EnrollmentResponse;
import ua.university.sms.model.dto.StudentResponse;
import ua.university.sms.model.entity.Course;
import ua.university.sms.model.entity.Enrollment;
import ua.university.sms.model.entity.Student;
import ua.university.sms.repository.CourseRepository;
import ua.university.sms.repository.EnrollmentRepository;
import ua.university.sms.repository.StudentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public EnrollmentResponse enrollStudent(EnrollmentRequest request) {
        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Студента не знайдено"));
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Курс не знайдено"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setPaid(false); // Нове зарахування не оплачене за замовчуванням
        enrollment.setGrade(null);  // Оцінки ще немає

        Enrollment saved = enrollmentRepository.save(enrollment);
        return mapToResponse(saved);
    }

    @Transactional
    public EnrollmentResponse setGrade(Long enrollmentId, Double grade) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Зарахування не знайдено"));

        enrollment.setGrade(grade);
        enrollmentRepository.save(enrollment);

        // Перерахунок GPA студента
        Student student = enrollment.getStudent();
        List<Enrollment> studentEnrollments = enrollmentRepository.findByStudentId(student.getId());

        double sum = 0;
        int count = 0;
        for (Enrollment e : studentEnrollments) {
            if (e.getGrade() != null) {
                sum += e.getGrade();
                count++;
            }
        }

        if (count > 0) {
            student.setGpa(sum / count);
            studentRepository.save(student);
        }

        return mapToResponse(enrollment);
    }

    @Transactional
    public EnrollmentResponse updatePaymentStatus(Long enrollmentId, Boolean paid) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Зарахування не знайдено"));

        enrollment.setPaid(paid);
        return mapToResponse(enrollmentRepository.save(enrollment));
    }

    // Блок D3: Список студентів, які мають хоча б один неоплачений курс
    public List<StudentResponse> getStudentsWithUnpaidCourses() {
        return enrollmentRepository.findStudentsWithUnpaidCourses().stream()
                .map(student -> new StudentResponse(
                        student.getId(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getStudyYear(),
                        student.getStatus(),
                        student.getGpa()
                )).toList();
    }

    public Double getAverageGradeByCourse(Long courseId) {
        Double avg = enrollmentRepository.getAverageGradeByCourse(courseId);
        return avg != null ? avg : 0.0;
    }

    private EnrollmentResponse mapToResponse(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getStudent().getFullName(),
                enrollment.getCourse().getId(),
                enrollment.getCourse().getTitle(),
                enrollment.getGrade(),
                enrollment.getPaid()
        );
    }
}