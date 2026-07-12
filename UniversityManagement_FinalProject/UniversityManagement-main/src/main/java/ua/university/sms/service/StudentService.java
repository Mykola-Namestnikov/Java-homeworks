package ua.university.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.StudentRequest;
import ua.university.sms.model.dto.StudentResponse;
import ua.university.sms.model.entity.Student;
import ua.university.sms.repository.StudentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        Student student = new Student();
        student.setFullName(request.fullName());
        student.setEmail(request.email());
        student.setStudyYear(request.studyYear());
        student.setStatus(request.status());
        student.setGpa(0.0); // Инициализация согласно требованиям

        Student savedStudent = studentRepository.save(student);
        return mapToResponse(savedStudent);
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Студента з ID " + id + " не знайдено"));
        return mapToResponse(student);
    }

    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Студента з ID " + id + " не знайдено"));

        student.setFullName(request.fullName());
        student.setEmail(request.email());
        student.setStudyYear(request.studyYear());
        student.setStatus(request.status());

        return mapToResponse(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Студента з ID " + id + " не знайдено");
        }
        studentRepository.deleteById(id);
    }

    // Методы фильтрации и поиска для Блока D
    public List<StudentResponse> getStudentsByStatus(String status) {
        return studentRepository.findByStatus(status).stream().map(this::mapToResponse).toList();
    }

    public List<StudentResponse> getStudentsByYear(Integer year) {
        return studentRepository.findByStudyYear(year).stream().map(this::mapToResponse).toList();
    }

    public List<StudentResponse> searchStudents(String query) {
        // Ищем либо по имени, либо по email
        List<Student> students = studentRepository.findByFullNameContainingIgnoreCase(query);
        if (students.isEmpty()) {
            students = studentRepository.findByEmailContainingIgnoreCase(query);
        }
        return students.stream().map(this::mapToResponse).toList();
    }

    public List<StudentResponse> getTopStudents() {
        return studentRepository.findTop10ByOrderByGpaDesc().stream().map(this::mapToResponse).toList();
    }

    private StudentResponse mapToResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getFullName(),
                student.getEmail(),
                student.getStudyYear(),
                student.getStatus(),
                student.getGpa()
        );
    }
}