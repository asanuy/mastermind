package com.asanuy.mastermind.service;

import com.asanuy.mastermind.model.CodePeg;

import java.util.List;

public interface CodePegService {

    List<CodePeg> getByGame(Long id);
    CodePeg getCodePeg(Long id);
}
