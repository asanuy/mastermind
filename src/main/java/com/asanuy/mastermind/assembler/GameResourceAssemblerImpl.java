package com.asanuy.mastermind.assembler;

import com.asanuy.mastermind.controller.CodePegController;
import com.asanuy.mastermind.controller.GameController;
import com.asanuy.mastermind.model.Game;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class GameResourceAssemblerImpl implements GameResourceAssembler {

    @Override
    public Resource<Game> toResource(Game game) {
        return new Resource<>(game,
                linkTo(methodOn(GameController.class).get(game.getId())).withSelfRel(),
                linkTo(methodOn(CodePegController.class).getByGame(game.getId())).withRel("codePegs"));
    }
}
