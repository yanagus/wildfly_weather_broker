package ru.bellintegrator.dao;

import ru.bellintegrator.model.Astronomy;
import ru.bellintegrator.model.Atmosphere;
import ru.bellintegrator.model.Condition;
import ru.bellintegrator.model.CurrentObservation;
import ru.bellintegrator.model.Forecast;
import ru.bellintegrator.model.Location;
import ru.bellintegrator.model.Wind;

import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@inheritDoc}
 */
@RequestScoped
public class WeatherDaoImpl implements WeatherDao {

    @PersistenceContext(unitName = "MySQLPU")
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveLocation(Location location) {
        entityManager.persist(location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveForecast(Forecast forecast) {
        entityManager.persist(forecast);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAstronomy(Astronomy astronomy) {
        entityManager.persist(astronomy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAtmosphere(Atmosphere atmosphere) {
        entityManager.persist(atmosphere);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveCondition(Condition condition) {
        entityManager.persist(condition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveWind(Wind wind) {
        entityManager.persist(wind);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveCurrentObservation(CurrentObservation currentObservation) {
        entityManager.persist(currentObservation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrentObservation findCurrentObservationByWoeidAndDate(Integer woeid) {
        CriteriaQuery<CurrentObservation> criteria = buildCriteriaByWoeidAndDate(woeid);
        TypedQuery<CurrentObservation> query = entityManager.createQuery(criteria);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            List<CurrentObservation> currentObservations = query.getResultList();
            int count = currentObservations.size();
            return currentObservations.get(count-1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location findLocationByWoeid(Integer woeid) {
        return entityManager.find(Location.class, woeid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location findLocationByCity(String city) {
        CriteriaQuery<Location> criteria = buildCriteriaByParameter(city);
        TypedQuery<Location> query = entityManager.createQuery(criteria);
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
    private CriteriaQuery<CurrentObservation> buildCriteriaByWoeidAndDate(Integer woeid) {
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
