package de.schnellertermin.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "complaints")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Complaint {
    private String id;
    private String name;
    @DBRef
    private Category category;
    private int urgencyScore;
}
