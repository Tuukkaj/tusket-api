package fi.tuukka.tusketapi.api.time;

import fi.tuukka.tusketapi.api.time.dto.TimeDto;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "time")
public class Time {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  private Long userId;
  private Long categoryId;
  private String description;
  @NotNull
  private Instant startTime;
  private Instant endTime;
  @NotNull
  private TimeStatus status;
  @NotNull
  @Convert(converter = TimePauseListConverter.class)
  private List<TimePause> pauses;

  TimeDto toDto() {
    return TimeDto.builder()
        .id(id)
        .userId(userId)
        .categoryId(categoryId)
        .description(description)
        .startTime(startTime)
        .endTime(endTime)
        .pauses(pauses)
        .status(status)
        .build();
  }
}
