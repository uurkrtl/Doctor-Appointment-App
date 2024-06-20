package de.schnellertermin.backend.services.dtos.requests;

import de.schnellertermin.backend.services.messages.ComplaintMessage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintRequest {
    @NotNull(message = ComplaintMessage.COMPLAINT_NAME_REQUIRED)
    private String name;
    @NotNull(message = ComplaintMessage.CATEGORY_REQUIRED)
    private String categoryId;
    @Min(0)
    @Max(5)
    private int urgencyScore;
}
