package fi.tuukka.tusketapi.api.time.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTimeRequest {
    @NotNull
    private Long userId;
    private Long categoryId;
    private String description;
}
