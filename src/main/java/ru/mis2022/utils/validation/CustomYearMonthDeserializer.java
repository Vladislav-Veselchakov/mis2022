package ru.mis2022.utils.validation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.mis2022.models.exception.ApiValidationException;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class CustomYearMonthDeserializer extends JsonDeserializer<YearMonth>{

        @Override
        public YearMonth deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext)
                throws IOException, JsonProcessingException {
            try {
                String date = jsonParser.getText();
                return YearMonth.parse(date, DateTimeFormatter.ofPattern( "MM/yyyy" ));
            } catch (JsonProcessingException e) {
                throw new ApiValidationException(400, "Введены некорректные данные!");
            }
        }

}
