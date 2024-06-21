package de.schnellertermin.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintCreatedResponse {
    private String id;
    private String name;
    private String categoryName;
    private int urgencyScore;
}
