package de.ostfalia.prog.s22ws.model;

import de.ostfalia.prog.s22ws.base.Charakter;
import de.ostfalia.prog.s22ws.base.Farbe;

public class Wettkarte extends Pyramidenkarte {
    private Farbe farbe;
    private int value;

    public Charakter getCharakter() {
        return charakter;
    }

    public void setCharakter(Charakter charakter) {
        this.charakter = charakter;
    }

    private Charakter charakter;

    public Wettkarte(Farbe farbe, int value) {
        this.farbe = farbe;
        this.value = value;
    }
    public Wettkarte(Farbe farbe, Charakter charakter) {
        this.farbe = farbe;
        this.charakter=charakter;
    }

    public Farbe getFarbe() {
        return farbe;
    }

    public void setFarbe(Farbe farbe) {
        this.farbe = farbe;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
