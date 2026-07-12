package ua.university.sms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Schema(description = "Дані для створення чи оновлення студента")
public record StudentRequest(

        @Schema(description = "Повне ім'я студента", example = "Олег Іванов")
        @NotBlank(message = "Name cannot be empty")
        String fullName,

        @Schema(description = "Електронна пошта студента", example = "oleg.ivanov@university.ua")
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "Курс навчання", example = "2")
        @NotNull(message = "Study year is required")
        @Min(value = 1, message = "Study year must be at least 1")
        Integer studyYear,

        @Schema(description = "Статус студента (Active, Graduated, Probation)", example = "Active")
        @NotBlank(message = "Status is required")
        String status
) {}