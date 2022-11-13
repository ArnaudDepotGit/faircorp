package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BuildingDaoCustomImpl implements BuildingDaoCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Room> findBuildingRooms(Long id) {
        String jpql1 = "select r from Room r where r.building.id = :id";
        return em.createQuery(jpql1, Room.class)
                .setParameter("id", id)
                .getResultList();
    }
}
