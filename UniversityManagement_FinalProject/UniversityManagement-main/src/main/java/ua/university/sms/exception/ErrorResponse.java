package ua.university.sms.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Уніфікована структура відповіді у разі помилки")
public record ErrorResponse(
        @Schema(description = "HTTP статус-код", example = "404")
        int status,

        @Schema(description = "Тип помилки", example = "Not Found")
        String error,

        @Schema(description = "Повідомлення про помилку", example = "Ресурс не знайдено")
        String message,

        @Schema(description = "Час виникнення помилки")
        LocalDateTime timestamp,

        @Schema(description = "Додаткові деталі (наприклад, помилки валідації полів)")
        Map<String, String> details
) {
    // Зручний конструктор для простих помилок (без деталей валідації)
    public ErrorResponse(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now(), null);
    }
}