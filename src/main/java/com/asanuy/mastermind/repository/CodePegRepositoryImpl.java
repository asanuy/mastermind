package com.asanuy.mastermind.repository;

import com.asanuy.mastermind.model.CodePeg;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CodePegRepositoryImpl implements CodePegRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CodePeg> findByGameId(Long id) {
        return entityManager.createQuery("SELECT cp FROM CodePeg cp WHERE cp.game.id = :id")
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public CodePeg findById(Long id) {
        return entityManager.find(CodePeg.class, id);
    }
}
