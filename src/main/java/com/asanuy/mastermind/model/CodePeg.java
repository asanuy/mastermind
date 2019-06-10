package com.asanuy.mastermind.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class CodePeg extends BaseEntity {

    @Column
    @NotNull
    private int position;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PegColor pegColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @NotNull
    @JsonBackReference
    private Game game;

    public CodePeg() {}

    public CodePeg(PegColor pegColor, Game game) {
        this.pegColor = pegColor;
        this.game = game;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PegColor getPegColor() {
        return pegColor;
    }

    public void setPegColor(PegColor pegColor) {
        this.pegColor = pegColor;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
