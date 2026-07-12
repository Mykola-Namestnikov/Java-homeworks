package ua.university.sms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Дані для створення або оновлення курсу")
public record CourseRequest(

        @Schema(description = "Назва курсу", example = "Вступ до Java")
        @NotBlank(message = "Назва курсу не може бути порожньою")
        String title,

        @Schema(description = "Кількість кредитів (ECTS)", example = "5")
        @NotNull(message = "Кількість кредитів є обов'язковою")
        @Min(value = 1, message = "Курс повинен мати хоча б 1 кредит")
        @Max(value = 12, message = "Максимальна кількість кредитів — 12")
        Integer credits,

        @Schema(description = "ID викладача, який веде курс", example = "3")
        @NotNull(message = "ID викладача є обов'язковим")
        Long teacherId
) {}