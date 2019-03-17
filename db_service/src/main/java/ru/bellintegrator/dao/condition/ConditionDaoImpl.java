package ru.bellintegrator.dao.condition;

import ru.bellintegrator.model.Condition;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * {@inheritDoc}
 */
@RequestScoped
public class ConditionDaoImpl implements ConditionDao {

    @PersistenceContext(unitName = "MySQLPU")
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Condition condition) {
        entityManager.persist(condition);
    }
}
