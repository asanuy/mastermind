package com.asanuy.mastermind.model;

public enum PegColor {

    RED(1),
    BLUE(2),
    GREEN(3),
    YELLOW(4),
    WHITE(5),
    BLACK(6);

    private int id;

    PegColor(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
