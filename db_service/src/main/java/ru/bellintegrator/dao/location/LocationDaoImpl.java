package ru.bellintegrator.dao.location;

import ru.bellintegrator.model.Location;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

/**
 * {@inheritDoc}
 */
@RequestScoped
public class LocationDaoImpl implements LocationDao {

    @PersistenceContext(unitName = "MySQLPU")
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Location location) {
        entityManager.persist(location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location findByWoeid(Integer woeid) {
        return entityManager.find(Location.class, woeid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location findByCity(String city) {
        CriteriaQuery<Location> criteria = buildCriteriaByParameter(city);
        TypedQuery<Location> query = entityManager.createQuery(criteria);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     * Сформировать запрос на получение местоположения из базы
     *
     * @param city название города
     * @return запрос
     */
    private CriteriaQuery<Location> buildCriteriaByParameter(String city) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Location> criteria = builder.createQuery(Location.class);
        Root<Location> locationRoot = criteria.from(Location.class);
        locationRoot.fetch("forecasts", JoinType.LEFT);
        criteria.where(builder.equal(builder.lower(locationRoot.get("city")), city.toLowerCase()));

        return criteria;
    }
}
