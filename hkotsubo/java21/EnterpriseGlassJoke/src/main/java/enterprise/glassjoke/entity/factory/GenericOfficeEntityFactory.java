package enterprise.glassjoke.entity.factory;

import enterprise.glassjoke.entity.OfficeEntity;

/**
 * Interface for all factories that create office entities.
 *
 * @author hkotsubo
 *
 * @param <T> The type of OfficeEntity this factory creates
 */
public interface GenericOfficeEntityFactory<T extends OfficeEntity> {

    /**
     * Get an instance of this factory.
     *
     * @return The new instance
     */
    public GenericOfficeEntityFactory<T> newFactory();

    /**
     * Create an entity with a random name
     *
     * @return The new entity
     */
    public T createEntity();

    /**
     * Create an instance with the specified name
     *
     * @param name The entity's name
     *
     * @return The new entity
     */
    public T createEntity(String name);
}
