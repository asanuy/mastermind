package com.asanuy.mastermind.assembler;

import com.asanuy.mastermind.model.Game;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public interface GameResourceAssembler extends ResourceAssembler<Game, Resource<Game>> {
}
