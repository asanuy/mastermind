package com.asanuy.mastermind.integration.repository;

import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.model.Game;
import com.asanuy.mastermind.model.PegColor;
import com.asanuy.mastermind.repository.CodePegRepository;
import com.asanuy.mastermind.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodePegRepositoryIntegrationTest {

    @Autowired
    private CodePegRepository codePegRepository;
    @Autowired
    private GameRepository gameRepository;

    private List<CodePeg> codePegs;
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
        CodePeg codePeg1 = new CodePeg(PegColor.YELLOW, game);
        codePeg1.setPosition(1);
        CodePeg codePeg2 = new CodePeg(PegColor.RED, game);
        codePeg2.setPosition(2);
        codePegs = Arrays.asList(
                codePeg1,
                codePeg2);
        game.setCodePegs(codePegs);
        gameRepository.save(game);
    }

    @Test
    @Transactional
    public void whenFindById_thenReturnCodePeg() {
        CodePeg codePeg = codePegs.get(0);
        assertEquals(codePeg, codePegRepository.findById(codePeg.getId()));
    }

    @Test
    @Transactional
    public void whenFindByGameId_thenReturnCodePegs() {
        assertEquals(codePegs, codePegRepository.findByGameId(game.getId()));
    }
}
