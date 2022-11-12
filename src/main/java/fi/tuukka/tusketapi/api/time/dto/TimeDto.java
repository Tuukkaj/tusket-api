package fi.tuukka.tusketapi.api.time.dto;

import fi.tuukka.tusketapi.api.time.Time;
import fi.tuukka.tusketapi.api.time.TimePause;
import fi.tuukka.tusketapi.api.time.TimeStatus;
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
public class TimeDto {

  private Long id;
  private Long userId;
  private Long categoryId;
  private TimeStatus status;
  private String description;
  private Instant startTime;
  private Instant endTime;
  private List<TimePause> pauses;

  public Time toDomain() {
    return Time.builder()
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
