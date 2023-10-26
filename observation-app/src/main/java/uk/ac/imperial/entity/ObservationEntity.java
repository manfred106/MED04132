package uk.ac.imperial.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "observation")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ObservationEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "patient")
    private Integer patient;

    @Column(name = "value")
    private double value;

    @ManyToOne
    @JoinColumn(name = "measurement_id")
    private MeasurementEntity measurementEntity;

}