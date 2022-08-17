package ru.mis2022.utils.validation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.mis2022.models.exception.ApiValidationException;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@AllArgsConstructor
@NoArgsConstructor
public class CustomYearMonthSerializer extends JsonSerializer<YearMonth> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy");

    @Override
    public void serialize(YearMonth value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        try {
            String s = value.format(formatter);
            gen.writeString(s);
        } catch (DateTimeParseException e) {
            throw new ApiValidationException(400, "Введены некорректные данные!");
        }
    }
}

