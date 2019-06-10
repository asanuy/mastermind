package com.asanuy.mastermind.service;

import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.model.Game;
import com.asanuy.mastermind.model.PegColor;
import com.asanuy.mastermind.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private CodePegService codePegService;

    @Override
    @Transactional
    public void createGame(Game game) {
        // Soft delete previous game if there is any
        gameRepository.deletePreviousGame();

        if (game.getCodePegs() == null
              || game.getCodePegs().isEmpty()) {
            throw new IllegalArgumentException("Game contains no code pegs");
        }

        int position = 1;
        for (CodePeg codePeg : game.getCodePegs()) {
            codePeg.setPosition(position++);
        }

        gameRepository.save(game);
    }

    @Override
    public Game getGame(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public List<PegColor> evaluateGuess(List<PegColor> guessedPegColors) {

        List<CodePeg> gameCodePegs = codePegService.getByLastGame();

        if (gameCodePegs.isEmpty()) {
            throw new IllegalStateException("No game available! Create a game first");
        }
        if (gameCodePegs.size() != guessedPegColors.size()) {
            throw new IllegalArgumentException("Number of combinations provided does not match the number of combinations for the current game");
        }

        int[] gamePegColorsCounter = new int[PegColor.values().length];
        int[] guessedPegColorsCounter = new int[PegColor.values().length];

        List<PegColor> result = new ArrayList<>();

        // Counter the number of times each color appears using two int arrays
        // At the end of the loop, the minimum value of the counter for each
        // color will be the maximum number of whites possible.
        // As in the same look blacks were calculated, the solution of whites
        // is going to be white = white(total) - black
        for (int i=0; i<gameCodePegs.size(); i++) {
            PegColor gamePegColor = gameCodePegs.get(i).getPegColor();
            PegColor guessedPegColor = guessedPegColors.get(i);

            gamePegColorsCounter[gamePegColor.getId()-1]++;
            guessedPegColorsCounter[guessedPegColor.getId()-1]++;

            if (gamePegColor.equals(guessedPegColor)) {
                result.add(PegColor.BLACK);
            }
        }

        // Minimum value out of two is maximum number of whites possible
        int matches = 0;
        for (int i=0; i<PegColor.values().length; i++) {
            matches += Math.min(gamePegColorsCounter[i], guessedPegColorsCounter[i]);
        }
        // white = white(total) - black
        matches -= result.size();

        for (int i=0; i<matches; i++) {
            result.add(PegColor.WHITE);
        }

        return result;
    }
}
