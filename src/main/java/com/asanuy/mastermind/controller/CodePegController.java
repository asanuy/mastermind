package com.asanuy.mastermind.controller;

import com.asanuy.mastermind.assembler.CodePegResourceAssembler;
import com.asanuy.mastermind.model.CodePeg;
import com.asanuy.mastermind.service.CodePegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/games/codepegs")
public class CodePegController {

    @Autowired
    private CodePegResourceAssembler assembler;
    @Autowired
    private CodePegService codePegService;

    @GetMapping()
    public ResponseEntity<?> getByGame(@RequestParam(value = "gameId") Long gameId) {
        List<CodePeg> codePegs = codePegService.getByGame(gameId);

        if (codePegs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Resource<CodePeg>> codePegResources;
        try {
            codePegResources =
                    codePegs.stream()
                            .map(codePeg -> assembler.toResource(codePeg))
                            .collect(Collectors.toList());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UUID.randomUUID());
        }

        return  ResponseEntity.ok(new Resources<>(codePegResources,
                linkTo(methodOn(CodePegController.class).getByGame(gameId)).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        CodePeg codePeg = codePegService.getCodePeg(id);

        if (codePeg == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(assembler.toResource(codePeg));
    }
}
