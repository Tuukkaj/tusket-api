package fi.tuukka.tusketapi.api.time;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimePauseListConverter implements AttributeConverter<List<TimePause>, String> {

  private final ObjectMapper objectMapper;


  @Override
  public String convertToDatabaseColumn(List<TimePause> attribute) {
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      log.error("Failed to stringify {}", attribute, e);
      return null;
    }
  }

  @Override
  public List<TimePause> convertToEntityAttribute(String dbData) {
    try {
      return objectMapper.readValue(dbData, new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      log.error("Failed to parse {}", dbData, e);
      return null;
    }
  }
}
