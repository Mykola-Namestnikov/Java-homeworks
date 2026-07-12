package ua.university.sms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация о студенте в ответе API")
public record StudentResponse(
        @Schema(description = "Унікальный ідентифікатор студента", example = "1")
        Long id,

        @Schema(description = "Повне ім'я", example = "Олег Іванов")
        String fullName,

        @Schema(description = "Email", example = "oleg.ivanov@university.ua")
        String email,

        @Schema(description = "Курс навчання", example = "2")
        Integer studyYear,

        @Schema(description = "Статус", example = "Active")
        String status,

        @Schema(description = "Середній бал (GPA)", example = "3.85")
        Double gpa
) {}