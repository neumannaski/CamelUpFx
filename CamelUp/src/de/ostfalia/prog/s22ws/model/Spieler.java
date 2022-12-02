package de.ostfalia.prog.s22ws.model;

import de.ostfalia.prog.s22ws.base.Charakter;

import java.util.Stack;

public class Spieler{

    private final String name;
    private final Charakter charakter;

    private int money;
    Stack<Pyramidenkarte> karten;

    /**
     * Konstruktor für eine normale Spieler Erstellung
     * @param name Name des Spielers
     * @param charakter Ausgewählter Charakter dieses Spielers
     */

    public Spieler(String name, Charakter charakter){
        this.charakter=charakter;
        this.charakter.getPlaettchen().reset();
        this.name=name;
        karten = new Stack<Pyramidenkarte>();
        this.money = 3;
    }

    /**
     * @return den Charakter des Spielers
     */
    public Charakter getCharakter(){
        return this.charakter;
    }

    /**
     * @return den Namen des Spielers
     */
    public String getName(){
        return this.name;
    }

    public void addKarte(Pyramidenkarte karte) {
        karten.add(karte);
    }

    public Pyramidenkarte getKarte() {
        return karten.pop();
    }

    public boolean isEmpty() {
        return karten.isEmpty();
    }


    public int getMoney() {
        return money;
    }

    public void addMoney(int mon) {
        money += mon;
        if (money < 0) {
            money = 0;
        }
    }

    @Override
    public String toString() {
        return "name:'" +charakter + "' " +  name + " mit dem Guthaben:" + money +" ÄP    ";
    }


}
