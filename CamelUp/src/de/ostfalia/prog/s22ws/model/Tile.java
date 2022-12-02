package de.ostfalia.prog.s22ws.model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Tile implements IStandable {

    private IStandable isStoodOnBy = null;
    private IStandable standsOn = null;

    private Tile nextTile;
    private Tile beforeTile;

    private int position;

    public Tile() {
        this.nextTile = null;
        this.beforeTile = null;
        position=1;
    }

    /**
     * Setzt Vor- und Nachfolger des Feldes
     * @param nextTile
     * @param beforeTile
     */
    public Tile(Tile nextTile, Tile beforeTile) {
        this.nextTile = nextTile;
        this.beforeTile = beforeTile;

    }

    public void setPosition(int position){
        this.position=position;
    }

    public int getPosition(){
        return this.position;
    }

    @Override
    public IStandable getIsStoodOnBy() {
        return isStoodOnBy;
    }

    @Override
    public void setIsStoodOnBy(IStandable isStoodOnBy) {
        this.isStoodOnBy = isStoodOnBy;
    }

    @Override
    public IStandable getStandsOn() {
        return standsOn;
    }

    @Override
    public void setStandsOn(IStandable standsOn) {
        this.standsOn = null;
    }


    public Tile getNextTile() {
        return nextTile;
    }

    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }

    public Tile getBeforeTile() {
        return beforeTile;
    }

    public void setBeforeTile(Tile beforeTile) {
        this.beforeTile = beforeTile;
    }

    @Override
    public int getStackPosition() {
        return 0;
    }

    @Override
    public IStandable getTopElement() {
        if (isStoodOnBy == null) {
            return this;
        } else {
            return isStoodOnBy.getTopElement();
        }
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String toString() {
        String tilestring = " { "+this.position+" ";
        if (isStoodOnBy != null) {
            tilestring += isStoodOnBy.toString();
        }
        tilestring += "  } ";
        return tilestring;
    }

    public List<IStandable> toList(){
        List<IStandable> list=new ArrayList<>();
        IStandable standable=this.isStoodOnBy;
        while (standable!=null){
            list.add(standable);
            standable=standable.getIsStoodOnBy();
        }
        System.out.println(list.size());
        return list;
    }
}
