package com.asanuy.mastermind.repository;

import com.asanuy.mastermind.model.Game;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GameRepositoryImpl implements GameRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Game game) {
        entityManager.persist(game);
    }

    @Override
    public Game findById(Long id) {
        return entityManager.find(Game.class, id);
    }

    @Override
    public boolean deletePreviousGame() {
        return 0 < entityManager.createQuery("UPDATE Game g SET g.deleted = true WHERE g.deleted = false").executeUpdate();
    }
}
