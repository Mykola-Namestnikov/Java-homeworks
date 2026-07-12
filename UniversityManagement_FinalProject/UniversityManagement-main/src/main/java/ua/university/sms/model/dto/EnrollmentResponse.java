package ua.university.sms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Деталі зарахування студента на курс")
public record EnrollmentResponse(
        Long id,
        Long studentId,
        String studentName,
        Long courseId,
        String courseTitle,
        Double grade,
        Boolean paid
) {}