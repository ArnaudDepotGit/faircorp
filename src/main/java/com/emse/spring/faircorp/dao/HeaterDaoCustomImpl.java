package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class HeaterDaoCustomImpl implements HeaterDaoCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    public int deleteByRoom(Long id) {
        String jpql = "delete from Heater h where h.room.id = :id";
        return em.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Heater> findRoomOnHeaters(Long id) {
        String jpql = "select h from Heater h where h.room.id = :id and h.heaterStatus = :status";
        return em.createQuery(jpql, Heater.class)
                .setParameter("id", id)
                .setParameter("status", HeaterStatus.ON)
                .getResultList();
    }
}
