package org.citronix.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.citronix.utils.enums.Season;

import java.time.Year;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "harvests")
public class Harvest extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @NotNull
    private Season season;

    @PastOrPresent
    @NotNull
    private Year year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(mappedBy = "harvest")
    private Set<HarvestDetail> harvestDetails;

    @OneToMany(mappedBy = "harvest")
    private Set<Sale> sales;
}
