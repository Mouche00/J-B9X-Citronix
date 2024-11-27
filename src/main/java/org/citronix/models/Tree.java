package org.citronix.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trees")
public class Tree implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @PastOrPresent
    private LocalDate plantingDate;

    @Transient
    @With
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HarvestDetail> harvestDetails = new ArrayList<>();

    public int getAge() {
        if (plantingDate != null) {
            return Period.between(plantingDate, LocalDate.now()).getYears();
        }
        return 0;
    }
}
