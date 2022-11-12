package fi.tuukka.tusketapi.api.time;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimePause {

  private Instant pause;
  private Instant restart;
}
