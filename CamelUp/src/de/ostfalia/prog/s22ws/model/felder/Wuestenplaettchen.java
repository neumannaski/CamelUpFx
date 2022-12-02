package de.ostfalia.prog.s22ws.model.felder;

import de.ostfalia.prog.s22ws.base.Charakter;
import de.ostfalia.prog.s22ws.base.Filemanager;
import de.ostfalia.prog.s22ws.model.IStandable;
import de.ostfalia.prog.s22ws.model.Tile;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Funktionalität und Wirkungsweise der Felder auf welchen ein Plättchen liegt.
 * Enthält den Charakter zu dem dieses Plättchen gehört
 */
public class Wuestenplaettchen implements IStandable {


    private Plaettchen plaettchen;
    private Charakter charakter;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private Image image;

    private IStandable isStoodOnBy = null;
    private IStandable standsOn = null;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Wuestenplaettchen(Charakter charakter){
        this.charakter=charakter;
    }

    public void setPlaettchen(Plaettchen plaettchen){
        this.plaettchen=plaettchen;
            try {
                image =new Image(new FileInputStream(Filemanager.path+""+plaettchen+".jpg"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }

    public void setCharakter(Charakter charakter) {
        this.charakter = charakter;
    }

    public Charakter getCharakter() {
        return charakter;
    }

    public Plaettchen getPlaettchen() {
        return plaettchen;
    }



    /**
     * Resetted dasPlättchen, sodass kein Wüstenplättchen mehr vorliegt
     */
    public void reset(){
        position=0;
        if(standsOn!=null) {
            standsOn.setIsStoodOnBy(null);
        }
        standsOn=null;
    }

    @Override
    public IStandable getIsStoodOnBy() {
        return isStoodOnBy;
    }

    @Override
    public int getStackPosition() {
        return 0;
    }

    @Override
    public IStandable getTopElement() {
        return null;
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
        if(this.standsOn!=null){
            this.standsOn.setIsStoodOnBy(null);
            System.out.println(this.standsOn);
        }
        Tile tile=(Tile)standsOn;
        position=tile.getPosition();
        standsOn.setIsStoodOnBy(this);
        this.standsOn=standsOn;

    }


    public String toGameString() {
        return this.charakter+":"+plaettchen.toString();
    }

    @Override
    public String toString() {
        return this.charakter+":"+plaettchen.toString();
    }
}
