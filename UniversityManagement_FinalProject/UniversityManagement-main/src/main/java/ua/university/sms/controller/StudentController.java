package ua.university.sms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.university.sms.model.dto.StudentRequest;
import ua.university.sms.model.dto.StudentResponse;
import ua.university.sms.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Students", description = "API для керування студентами и фильтрації")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Operation(summary = "Створити нового студента")
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse response = studentService.createStudent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Отримати список студентів (з опціональною фільтрацією по статусу чи року)")
    public ResponseEntity<List<StudentResponse>> getStudents(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer year) {

        if (status != null) {
            return ResponseEntity.ok(studentService.getStudentsByStatus(status));
        }
        if (year != null) {
            return ResponseEntity.ok(studentService.getStudentsByYear(year));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати студента по ID")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані студента")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити студента")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Пошук студента по частині імені чи email")
    public ResponseEntity<List<StudentResponse>> searchStudents(@RequestParam String query) {
        return ResponseEntity.ok(studentService.searchStudents(query));
    }

    @GetMapping("/top")
    @Operation(summary = "Отримати топ студентів по найвищому GPA")
    public ResponseEntity<List<StudentResponse>> getTopStudents() {
        return ResponseEntity.ok(studentService.getTopStudents());
    }
}