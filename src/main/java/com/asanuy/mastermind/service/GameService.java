package com.asanuy.mastermind.service;

import com.asanuy.mastermind.model.Game;

public interface GameService {

    void createGame(Game game);
    Game getGame(Long id);
}
