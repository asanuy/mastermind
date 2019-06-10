package com.asanuy.mastermind.service;

import com.asanuy.mastermind.model.Game;
import com.asanuy.mastermind.model.PegColor;

import java.util.List;

public interface GameService {

    void createGame(Game game);
    Game getGame(Long id);
    List<PegColor> evaluateGuess(List<PegColor> pegColors);
}
