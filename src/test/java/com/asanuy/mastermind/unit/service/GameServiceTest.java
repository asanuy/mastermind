package com.asanuy.mastermind.unit.service;

import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.model.Game;
import com.asanuy.mastermind.model.PegColor;
import com.asanuy.mastermind.repository.GameRepository;
import com.asanuy.mastermind.service.GameServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @InjectMocks
    private GameServiceImpl gameService;
    @Mock
    private GameRepository gameRepository;

    @Test
    public void testCreateGame() {
        Game game = new Game();
        game.setCodePegs(Arrays.asList(
                new CodePeg(PegColor.YELLOW, game),
                new CodePeg(PegColor.RED, game),
                new CodePeg(PegColor.WHITE, game),
                new CodePeg(PegColor.GREEN, game)));
        gameService.createGame(game);

        verify(gameRepository, times(1)).deletePreviousGame();
        verify(gameRepository, times(1)).save(game);
        assertEquals(4, game.getCodePegs().size());

        int position = 1;
        for (CodePeg codePeg : game.getCodePegs()) {
            assertEquals(position++, codePeg.getPosition());
        }
    }

    @Test
    public void testGetGame() {
        Long gameId = 1L;
        gameService.getGame(gameId);

        verify(gameRepository, times(1)).findById(gameId);
    }
}
