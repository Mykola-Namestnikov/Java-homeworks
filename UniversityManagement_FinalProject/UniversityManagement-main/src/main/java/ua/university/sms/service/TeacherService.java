package ua.university.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.TeacherRequest;
import ua.university.sms.model.dto.TeacherResponse;
import ua.university.sms.model.entity.Teacher;
import ua.university.sms.repository.TeacherRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherResponse> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TeacherResponse getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Викладача з ID: " + id + " не знайдено")); // Замени на кастомный эксепшн при наличии
        return mapToResponse(teacher);
    }

    @Transactional
    public TeacherResponse createTeacher(TeacherRequest request) {
        Teacher teacher = new Teacher();
        updateTeacherFields(teacher, request);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return mapToResponse(savedTeacher);
    }

    @Transactional
    public TeacherResponse updateTeacher(Long id, TeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Викладача з ID: " + id + " не знайдено"));
        updateTeacherFields(teacher, request);
        return mapToResponse(teacher);
    }

    @Transactional
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new ResourceNotFoundException("Викладача з ID: " + id + " не знайдено");
        }
        teacherRepository.deleteById(id);
    }

    private void updateTeacherFields(Teacher teacher, TeacherRequest request) {
        teacher.setFullName(request.fullName());
        teacher.setEmail(request.email());
        teacher.setDepartment(request.department());
    }

    private TeacherResponse mapToResponse(Teacher teacher) {
        return new TeacherResponse(
                teacher.getId(),
                teacher.getFullName(),
                teacher.getEmail(),
                teacher.getDepartment()
        );
    }
}