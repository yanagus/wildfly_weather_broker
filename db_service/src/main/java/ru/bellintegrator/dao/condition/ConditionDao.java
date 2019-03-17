package ru.bellintegrator.dao.condition;

import ru.bellintegrator.model.Condition;

/**
 * DAO для работы с Condition
 */
public interface ConditionDao {

    /**
     * Сохранить информацию о текущем состоянии погоды в базу данных
     *
     * @param condition текущее состояние погоды
     */
    void save(Condition condition);
}
