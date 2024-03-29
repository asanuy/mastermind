package com.asanuy.mastermind.controller;

import com.asanuy.mastermind.assembler.GameResourceAssembler;
import com.asanuy.mastermind.dto.CodePegDTO;
import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.model.Game;
import com.asanuy.mastermind.model.PegColor;
import com.asanuy.mastermind.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    private GameResourceAssembler assembler;
    @Autowired
    private GameService gameService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Game game = gameService.getGame(id);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(assembler.toResource(gameService.getGame(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<CodePegDTO> codePegDTOs) {
        if (codePegDTOs.isEmpty() || codePegDTOs.size() != 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A game must be a combination of 4 code pegs");
        }

        Game game = new Game();
        try {
            List<CodePeg> codePegs =
                    codePegDTOs.stream()
                    .map(codePegDTO -> new CodePeg(codePegDTO.getPegColor(), game))
                    .collect(Collectors.toList());
            game.setCodePegs(codePegs);

            gameService.createGame(game);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UUID.randomUUID());
        }

        return ResponseEntity.created(linkTo(methodOn(GameController.class).get(game.getId())).toUri())
                .body(assembler.toResource(game));
    }

    @GetMapping("/guess")
    public ResponseEntity<?> getGuess(@RequestParam(value = "pegColors") List<String> pegColors) {
        if (pegColors.isEmpty() || pegColors.size() != 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A guess must be a combination of 4 peg colors");
        }

        List<PegColor> guessedPegColors;
        try {
            guessedPegColors = pegColors.stream()
                    .map(s -> PegColor.valueOf(s))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Color not available. Valid colors are ["
                + Arrays.stream(PegColor.values()).map(PegColor::toString).collect(Collectors.joining(", ")) + "]");
        }

        List<PegColor> result;
        try {
            result = gameService.evaluateGuess(guessedPegColors);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (IllegalStateException ex ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UUID.randomUUID());
        }

        return ResponseEntity.ok(result);
    }
}
