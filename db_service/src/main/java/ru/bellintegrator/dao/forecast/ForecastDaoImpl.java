package ru.bellintegrator.dao.forecast;

import ru.bellintegrator.model.Forecast;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * {@inheritDoc}
 */
@RequestScoped
public class ForecastDaoImpl implements ForecastDao {

    @PersistenceContext(unitName = "MySQLPU")
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Forecast forecast) {
        entityManager.persist(forecast);
    }
}
