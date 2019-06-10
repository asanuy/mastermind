package com.asanuy.mastermind.service;

import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.model.Game;
import com.asanuy.mastermind.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    @Transactional
    public void createGame(Game game) {
        // Soft delete previous game if there is any
        gameRepository.deletePreviousGame();

        if (game.getCodePegs() == null
              || game.getCodePegs().isEmpty()) {
            throw new IllegalStateException("Game contains no code pegs");
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
}
