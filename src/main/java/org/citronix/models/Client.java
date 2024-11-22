package org.citronix.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends BaseEntity {
    @NotBlank
    private String cin;
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "client")
    private Set<Sale> sales;
}
