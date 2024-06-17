package de.schnellertermin.backend.services.dtos.requests;

import de.schnellertermin.backend.services.messages.CategoryMessage;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    @NotNull(message = CategoryMessage.CATEGORY_NAME_REQUIRED)
    private String name;
}
