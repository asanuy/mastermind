package com.asanuy.mastermind.assembler;

import com.asanuy.mastermind.controller.CodePegController;
import com.asanuy.mastermind.model.CodePeg;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CodePegResourceAssemblerImpl implements CodePegResourceAssembler {

    @Override
    public Resource<CodePeg> toResource(CodePeg codePeg) {
        return new Resource<>(codePeg,
                linkTo(methodOn(CodePegController.class).get(codePeg.getId())).withSelfRel(),
                linkTo(methodOn(CodePegController.class).getByGame(codePeg.getGame().getId())).withRel("codePegs"));
    }
}
