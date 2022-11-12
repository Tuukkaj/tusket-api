package fi.tuukka.tusketapi.api.time.dto;

import fi.tuukka.tusketapi.api.time.TimeStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchTimeRequest {

  @NotNull
  private TimeStatus status;
}
