package com.asanuy.mastermind.repository;

import com.asanuy.mastermind.model.Game;

public interface GameRepository {

    void save(Game game);
    Game findById(Long id);
    boolean deletePreviousGame();
}
