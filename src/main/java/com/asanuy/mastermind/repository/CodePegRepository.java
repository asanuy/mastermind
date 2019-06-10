package com.asanuy.mastermind.repository;

import com.asanuy.mastermind.model.CodePeg;

import java.util.List;

public interface CodePegRepository {

    List<CodePeg> findByGameId(Long id);
    CodePeg findById(Long id);
    List<CodePeg> findByLastGame();
}
