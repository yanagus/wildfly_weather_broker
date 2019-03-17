package ru.bellintegrator.dao.currentobservation;

import ru.bellintegrator.model.CurrentObservation;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@RequestScoped
public class CurrentObservationDaoImpl implements CurrentObservationDao {

    @PersistenceContext(unitName = "MySQLPU")
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(CurrentObservation currentObservation) {
        entityManager.persist(currentObservation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrentObservation findByParameters(Integer woeid) {
        CriteriaQuery<CurrentObservation> criteria = buildCriteriaByParametersList(woeid);
        TypedQuery<CurrentObservation> query = entityManager.createQuery(criteria);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     * Сформировать запрос на поиск обзора погоды не старее 6 часов с настоящего момента
     *
     * @param woeid идентификатор города (WOEID - Where On Earth IDentifier)
     * @return запрос
     */
    private CriteriaQuery<CurrentObservation> buildCriteriaByParametersList(Integer woeid) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CurrentObservation> criteria = builder.createQuery(CurrentObservation.class);
        Root<CurrentObservation> root = criteria.from(CurrentObservation.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("location"), woeid));

        long millis = System.currentTimeMillis();
        long lineDate = (millis - 6*60*60*1000)/1000;
        predicates.add(builder.greaterThan(root.get("pubDate"), lineDate));

        criteria.select(root).where(predicates.toArray(new Predicate[]{}));
        return criteria;
    }

}
