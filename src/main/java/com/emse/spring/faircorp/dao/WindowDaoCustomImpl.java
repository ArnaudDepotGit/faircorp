package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;
import com.emse.spring.faircorp.model.WindowStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class WindowDaoCustomImpl implements WindowDaoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Window> findRoomOpenWindows(Long id) {
        String jpql = "select w from Window w where w.room.id = :id and w.windowStatus= :status";
        return em.createQuery(jpql, Window.class)
                .setParameter("id", id)
                .setParameter("status", WindowStatus.OPEN)
                .getResultList();
    }

    @Override
    public int deleteByRoom(Long id) {
        String jpql = "delete from Window w where w.room.id = :id";
        return em.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Window> findRoomWindows(String name) {
        String jpql1 = "select r from Room r where r.name = :name";
        Long id_room = em.createQuery(jpql1, Room.class)
                .setParameter("name", name)
                .getSingleResult().getId();
        String jpql2 = "select w from Window w where w.room.id = :id";
        return em.createQuery(jpql2, Window.class)
                .setParameter("id", id_room)
                .getResultList();
    }
}
