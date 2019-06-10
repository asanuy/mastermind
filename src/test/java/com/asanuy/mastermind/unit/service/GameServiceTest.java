package com.asanuy.mastermind.unit.service;

import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.model.Game;
import com.asanuy.mastermind.model.PegColor;
import com.asanuy.mastermind.repository.GameRepository;
import com.asanuy.mastermind.service.CodePegService;
import com.asanuy.mastermind.service.GameServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @InjectMocks
    private GameServiceImpl gameService;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private CodePegService codePegService;

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

    @Test
    public void testEvaluateGuessCorrect() {
        Game game = new Game();
        CodePeg codePeg1 = new CodePeg(PegColor.RED, game);
        codePeg1.setPosition(1);
        CodePeg codePeg2 = new CodePeg(PegColor.RED, game);
        codePeg2.setPosition(2);
        CodePeg codePeg3 = new CodePeg(PegColor.BLACK, game);
        codePeg3.setPosition(3);
        CodePeg codePeg4 = new CodePeg(PegColor.BLUE, game);
        codePeg4.setPosition(4);

        List<CodePeg> codePegs = Arrays.asList(codePeg1, codePeg2, codePeg3, codePeg4);

        when(codePegService.getByLastGame()).thenReturn(codePegs);

        // Expect 4 Black
        List<PegColor> guessedPegColors =
                Arrays.asList(PegColor.RED, PegColor.RED, PegColor.BLACK, PegColor.BLUE);

        List<PegColor> result = gameService.evaluateGuess(guessedPegColors);

        verify(codePegService, times(1)).getByLastGame();
        assertEquals(4, result.size());
        assertEquals(PegColor.BLACK, result.get(0));
        assertEquals(PegColor.BLACK, result.get(1));
        assertEquals(PegColor.BLACK, result.get(2));
        assertEquals(PegColor.BLACK, result.get(3));
    }

    @Test
    public void testEvaluateGuessPartiallyCorrect() {
        Game game = new Game();
        CodePeg codePeg1 = new CodePeg(PegColor.RED, game);
        codePeg1.setPosition(1);
        CodePeg codePeg2 = new CodePeg(PegColor.RED, game);
        codePeg2.setPosition(2);
        CodePeg codePeg3 = new CodePeg(PegColor.BLACK, game);
        codePeg3.setPosition(3);
        CodePeg codePeg4 = new CodePeg(PegColor.BLUE, game);
        codePeg4.setPosition(4);

        List<CodePeg> codePegs = Arrays.asList(codePeg1, codePeg2, codePeg3, codePeg4);

        when(codePegService.getByLastGame()).thenReturn(codePegs);

        // Expect 1 Black and 3 White
        List<PegColor> guessedPegColors =
                Arrays.asList(PegColor.BLACK, PegColor.RED, PegColor.BLUE, PegColor.RED);

        List<PegColor> result = gameService.evaluateGuess(guessedPegColors);

        assertEquals(4, result.size());
        assertEquals(PegColor.BLACK, result.get(0));
        assertEquals(PegColor.WHITE, result.get(1));
        assertEquals(PegColor.WHITE, result.get(2));
        assertEquals(PegColor.WHITE, result.get(3));

        // Expect 2 Black and 1 White
        guessedPegColors =
                Arrays.asList(PegColor.RED, PegColor.BLACK, PegColor.BLUE, PegColor.BLUE);

        result = gameService.evaluateGuess(guessedPegColors);

        assertEquals(3, result.size());
        assertEquals(PegColor.BLACK, result.get(0));
        assertEquals(PegColor.BLACK, result.get(1));
        assertEquals(PegColor.WHITE, result.get(2));
    }

    @Test(expected = IllegalStateException.class)
    public void whenEvaluateGuess_ThenThrowIllegalStateException() {
        when(codePegService.getByLastGame()).thenReturn(Collections.EMPTY_LIST);

        gameService.evaluateGuess(Collections.EMPTY_LIST);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenEvaluateGuess_ThenThrowIllegalArgumentException() {
        when(codePegService.getByLastGame()).thenReturn(Arrays.asList(new CodePeg(PegColor.RED, new Game())));

        gameService.evaluateGuess(Collections.EMPTY_LIST);
    }
}
