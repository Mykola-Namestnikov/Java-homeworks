package ua.university.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.CourseRequest;
import ua.university.sms.model.dto.CourseResponse;
import ua.university.sms.model.entity.Course;
import ua.university.sms.model.entity.Teacher;
import ua.university.sms.repository.CourseRepository;
import ua.university.sms.repository.TeacherRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    // Конструктор для впровадження залежностей (щоб уникнути помилки ініціалізації фінальних полів)
    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        // Перевіряємо, чи існує викладач
        Teacher teacher = teacherRepository.findById(request.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Викладача з ID " + request.teacherId() + " не знайдено"));

        Course course = new Course();
        course.setTitle(request.title());
        course.setCredits(request.credits());
        course.setTeacher(teacher);

        Course savedCourse = courseRepository.save(course);
        return mapToResponse(savedCourse);
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Курс з ID " + id + " не знайдено"));
        return mapToResponse(course);
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Курс з ID " + id + " не знайдено"));

        Teacher teacher = teacherRepository.findById(request.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Викладача з ID " + request.teacherId() + " not found"));

        course.setTitle(request.title());
        course.setCredits(request.credits());
        course.setTeacher(teacher);

        return mapToResponse(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Курс з ID " + id + " не знайдено");
        }
        courseRepository.deleteById(id);
    }

    // Блок D6: Фільтрація курсів за викладачем
    public List<CourseResponse> getCoursesByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId).stream().map(this::mapToResponse).toList();
    }

    // Блок D6: Фільтрація курсів за кількістю кредитів
    public List<CourseResponse> getCoursesByCredits(Integer credits) {
        return courseRepository.findByCredits(credits).stream().map(this::mapToResponse).toList();
    }

    private CourseResponse mapToResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getCredits(),
                course.getTeacher() != null ? course.getTeacher().getId() : null
        );
    }
}