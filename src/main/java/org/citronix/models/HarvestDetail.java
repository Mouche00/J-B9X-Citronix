package org.citronix.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "harvest_details")
public class HarvestDetail implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @PositiveOrZero
    private double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harvest_id")
    private Harvest harvest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_id")
    private Tree tree;
}
