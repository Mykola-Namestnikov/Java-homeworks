package ua.university.sms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Інформація про курс у відповіді API")
public record CourseResponse(
        @Schema(description = "ID курсу", example = "1")
        Long id,
        @Schema(description = "Назва курсу", example = "Вступ до Java")
        String title,
        @Schema(description = "Кількість кредитів", example = "5")
        Integer credits,
        @Schema(description = "ID викладача", example = "3")
        Long teacherId
) {}