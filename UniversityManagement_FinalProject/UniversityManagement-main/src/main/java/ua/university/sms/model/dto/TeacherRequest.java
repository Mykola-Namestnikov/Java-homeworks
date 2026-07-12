package ua.university.sms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запит на створення чи оновлення викладача")
public record TeacherRequest(
        @Schema(description = "Ім'я та прізвище викладача", example = "Іван Петров")
        @NotBlank(message = "Ім'я та прізвище не може бути порожнім")
        @Size(min = 2, max = 100, message = "Ім'я та прізвище має бути від 2 до 50 символів")
        String fullName,

        @Schema(description = "Електронна пошта", example = "ivan.petrov@university.ua")
        @NotBlank(message = "Email не може бути порожнім")
        @Email(message = "Некорректний формат email")
        String email,

        @Schema(description = "Кафедра / Департамент", example = "Кафедра Комп'ютерних Наук")
        @NotBlank(message = "Департамент не може бути порожнім")
        String department
) {}