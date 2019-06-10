package com.asanuy.mastermind.integration.service;

import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.model.Game;
import com.asanuy.mastermind.model.PegColor;
import com.asanuy.mastermind.service.CodePegService;
import com.asanuy.mastermind.service.GameService;
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
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodePegServiceIntegrationTest {

    @Autowired
    private CodePegService codePegService;
    @Autowired
    private GameService gameService;

    private List<CodePeg> codePegs;
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
        codePegs = Arrays.asList(
                new CodePeg(PegColor.YELLOW, game),
                new CodePeg(PegColor.RED, game));
        game.setCodePegs(codePegs);
        gameService.createGame(game);
    }

    @Test
    @Transactional
    public void whenGetByGame_thenReturnCodePegs() {
        assertEquals(codePegs, codePegService.getByGame(game.getId()));
    }

    @Test
    @Transactional
    public void whenGetByCodePeg_thenReturnCodePeg() {
        CodePeg codePeg = codePegs.get(0);
        assertEquals(codePeg, codePegService.getCodePeg(codePeg.getId()));
    }

    @Test
    @Transactional
    public void whenGetByLastGame_thenReturnLastCodePegs() {
        List<CodePeg> codePegs = codePegService.getByLastGame();

        assertFalse(codePegs.get(0).getGame().isDeleted());
        assertEquals(2, codePegs.size());
        assertEquals(1, codePegs.get(0).getPosition());
        assertEquals(2, codePegs.get(1).getPosition());
    }
}
