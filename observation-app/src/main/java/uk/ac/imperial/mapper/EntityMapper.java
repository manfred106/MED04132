package uk.ac.imperial.mapper;

public interface EntityMapper<E, D> {

    D fromEntity(E entity);

    E toEntity(D domain);

}
