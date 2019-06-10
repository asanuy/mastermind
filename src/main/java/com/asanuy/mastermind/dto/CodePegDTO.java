package com.asanuy.mastermind.dto;

import com.asanuy.mastermind.model.PegColor;

public class CodePegDTO {

    public CodePegDTO() {}

    private PegColor pegColor;

    public PegColor getPegColor() {
        return pegColor;
    }

    public void setPegColor(PegColor pegColor) {
        this.pegColor = pegColor;
    }
}
