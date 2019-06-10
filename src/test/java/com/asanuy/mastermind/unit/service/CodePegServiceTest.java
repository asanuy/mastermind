package com.asanuy.mastermind.unit.service;

import com.asanuy.mastermind.repository.CodePegRepository;
import com.asanuy.mastermind.service.CodePegServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CodePegServiceTest {

    @InjectMocks
    private CodePegServiceImpl codePegService;
    @Mock
    private CodePegRepository codePegRepository;

    @Test
    public void testGetByGame() {
        Long gameId = 1L;
        codePegService.getByGame(gameId);

        verify(codePegRepository, times(1)).findByGameId(gameId);
    }

    @Test
    public void testGetCodePeg() {
        Long codePegId = 1L;
        codePegService.getCodePeg(codePegId);

        verify(codePegRepository, times(1)).findById(codePegId);
    }
}
