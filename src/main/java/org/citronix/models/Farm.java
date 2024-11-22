package org.citronix.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "farms")
public class Farm extends BaseEntity {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @DecimalMin(value = "0.2")
    private double surfaceArea;

    @PastOrPresent
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }

    @OneToMany(mappedBy = "farm")
    private Set<Field> fields;
}
