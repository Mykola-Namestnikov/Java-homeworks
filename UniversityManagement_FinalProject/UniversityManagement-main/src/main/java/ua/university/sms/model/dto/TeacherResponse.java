package ua.university.sms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Дані викладача у відповіді API")
public record TeacherResponse(
        @Schema(description = "Уникальний ідентификатор", example = "1")
        Long id,
        @Schema(description = "Ім'я, прізвище", example = "Іван Петров")
        String fullName,
        @Schema(description = "Email", example = "ivan.petrov@university.ua")
        String email,
        @Schema(description = "Департамент", example = "Кафедра Комп'ютерних Наук")
        String department
) {}