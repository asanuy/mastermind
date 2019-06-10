package com.asanuy.mastermind.integration.service;

import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.model.Game;
import com.asanuy.mastermind.model.PegColor;
import com.asanuy.mastermind.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceIntegrationTest {

    @Autowired
    private GameService gameService;

    @Test
    @Transactional
    public void whenCreateGame_thenGetGame() {
        Game game = new Game();
        CodePeg codePeg = new CodePeg(PegColor.YELLOW, game);
        game.setCodePegs(Collections.singletonList(codePeg));

        gameService.createGame(game);

        assertEquals(game, gameService.getGame(game.getId()));
    }
}
