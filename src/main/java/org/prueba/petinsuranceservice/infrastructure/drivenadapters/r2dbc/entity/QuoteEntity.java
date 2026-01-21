package org.prueba.petinsuranceservice.infrastructure.drivenadapters.r2dbc.entity;

import lombok.*;
import org.prueba.petinsuranceservice.domain.model.Plan;
import org.prueba.petinsuranceservice.domain.model.Species;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("quotes")
public class QuoteEntity implements Persistable<UUID> {
    @Id
    private UUID id;
    private BigDecimal amount;
    private LocalDateTime expirationDate;
    private String petName;
    private String petSpecies;
    private String petBreed;
    private Integer petAge;
    private String petPlan;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }
}
