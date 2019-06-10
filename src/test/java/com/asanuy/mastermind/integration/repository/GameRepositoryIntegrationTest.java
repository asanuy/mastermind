package com.asanuy.mastermind.integration.repository;

import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.model.Game;
import com.asanuy.mastermind.model.PegColor;
import com.asanuy.mastermind.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameRepositoryIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private GameRepository gameRepository;

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
        CodePeg codePeg = new CodePeg(PegColor.YELLOW, game);
        codePeg.setPosition(1);
        game.setCodePegs(Collections.singletonList(codePeg));
        gameRepository.save(game);
    }

    @Test
    @Transactional
    public void whenFindById_thenReturnGame() {
        assertEquals(game, gameRepository.findById(game.getId()));
    }

    @Test
    @Transactional
    public void whencreateGame_thenGameDeletedIsFalse() {
        Game game = gameRepository.findById(this.game.getId());
        assertFalse(game.isDeleted());
    }

    @Test
    @Transactional
    public void whenDeletePreviousGame_thenReturnTrue() {
        Game game = gameRepository.findById(this.game.getId());
        assertTrue(gameRepository.deletePreviousGame());
        entityManager.refresh(game);
        assertTrue(game.isDeleted());
    }
}
