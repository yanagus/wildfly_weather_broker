package ru.bellintegrator.dao.atmosphere;

import ru.bellintegrator.model.Atmosphere;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * {@inheritDoc}
 */
@RequestScoped
public class AtmosphereDaoImpl implements AtmosphereDao {

    @PersistenceContext(unitName = "MySQLPU")
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Atmosphere atmosphere) {
        entityManager.persist(atmosphere);
    }
}
