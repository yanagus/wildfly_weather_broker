package ru.bellintegrator.dao.wind;

import ru.bellintegrator.model.Wind;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * {@inheritDoc}
 */
@RequestScoped
public class WindDaoImpl implements WindDao {

    @PersistenceContext(unitName = "MySQLPU")
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Wind wind) {
        entityManager.persist(wind);
    }
}
