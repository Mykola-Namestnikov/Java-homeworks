package ua.university.sms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запит на зарахування студента на курс")
public record EnrollmentRequest(
        @Schema(description = "ID студента", example = "1")
        @NotNull(message = "ID студента обов'язковий")
        Long studentId,

        @Schema(description = "ID курсу", example = "2")
        @NotNull(message = "ID курсу обов'язковий")
        Long courseId
) {}