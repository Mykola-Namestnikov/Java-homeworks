package ua.university.sms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.university.sms.model.dto.EnrollmentRequest;
import ua.university.sms.model.dto.EnrollmentResponse;
import ua.university.sms.model.dto.StudentResponse;
import ua.university.sms.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "Enrollments", description = "Управління процесом навчання, оцінками та оплатою")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @Operation(summary = "Зарахувати студента на курс")
    public ResponseEntity<EnrollmentResponse> enrollStudent(@Valid @RequestBody EnrollmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enrollStudent(request));
    }

    @PutMapping("/{id}/grade")
    @Operation(summary = "Виставити/оновити оцінку за курс")
    public ResponseEntity<EnrollmentResponse> setGrade(
            @PathVariable Long id,
            @RequestParam Double grade) {
        return ResponseEntity.ok(enrollmentService.setGrade(id, grade));
    }

    @PutMapping("/{id}/payment")
    @Operation(summary = "Оновити статус оплати курсу")
    public ResponseEntity<EnrollmentResponse> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam Boolean paid) {
        return ResponseEntity.ok(enrollmentService.updatePaymentStatus(id, paid));
    }

    @GetMapping("/unpaid-students")
    @Operation(summary = "Список студентів з хоча б одним неоплаченим курсом")
    public ResponseEntity<List<StudentResponse>> getStudentsWithUnpaidCourses() {
        return ResponseEntity.ok(enrollmentService.getStudentsWithUnpaidCourses());
    }

    @GetMapping("/course-average/{courseId}")
    @Operation(summary = "Розрахунок середнього бала по конкретному курсу")
    public ResponseEntity<Double> getAverageGradeByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getAverageGradeByCourse(courseId));
    }
}