package uk.ac.imperial.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Observation implements Cloneable {

    private Integer id;

    private String date;

    @NotNull
    private Integer patient;

    @NotNull
    @Min(value = 0)
    private Double value;

    @NotEmpty
    private String measureType;

    @NotEmpty
    private String measureUnit;

    @Override
    public Observation clone() {
        try {
            return (Observation) super.clone();
        } catch (CloneNotSupportedException e) {
            // This should never happen since Observation implements Cloneable
            throw new AssertionError("Observation cloning failed", e);
        }
    }

}