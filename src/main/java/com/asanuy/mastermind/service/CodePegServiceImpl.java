package com.asanuy.mastermind.service;

import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.repository.CodePegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodePegServiceImpl implements CodePegService {

    @Autowired
    private CodePegRepository codePegRepository;

    @Override
    public List<CodePeg> getByGame(Long id) {
        return codePegRepository.findByGameId(id);
    }

    @Override
    public CodePeg getCodePeg(Long id) {
        return codePegRepository.findById(id);
    }

    @Override
    public List<CodePeg> getByLastGame() {
        return codePegRepository.findByLastGame();
    }
}
