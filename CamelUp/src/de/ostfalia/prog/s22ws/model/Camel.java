package de.ostfalia.prog.s22ws.model;

import de.ostfalia.prog.s22ws.base.Farbe;
import de.ostfalia.prog.s22ws.base.Filemanager;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Camel implements IStandable {

    private final Farbe color;
    private IStandable standsOn;
    private IStandable isStoodOnBy;
    private int position;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private Image image;



    public Camel(Farbe color) throws FileNotFoundException {
        this.color = color;
        standsOn = null;
        isStoodOnBy = null;
        position = 0;
        image=new Image( new FileInputStream(Filemanager.path+""+color.toString()+".png"));
    }

    /**
     * @return die Postion des Kamels
     */
    public int getPosition() {
        return position;
    }

    /**
     * Setzt eine neue Postion des Kamels
     * @param pos
     * @param newStandsOn
     */
    public void setPosition(int pos, Tile newStandsOn) {
        this.position = pos;
        this.setStandsOn(newStandsOn);
    }

    @Override
    public IStandable getStandsOn() {
        return standsOn;
    }

    public void setStandsOn(IStandable standsOn) {
        if (standsOn.getIsStoodOnBy() == null) {
            this.standsOn = standsOn;
            standsOn.setIsStoodOnBy(this);
        }
        else {
            setStandsOn(standsOn.getIsStoodOnBy());
        }
    }

    /**
     * Setzt das Kamel zwischen Bodenfeld und erstes Kamel.
     * @param standsOn Ziel der Bewegung
     * @return null oder das amel welches auf dem Bodenfeld steht
     */
    public IStandable setStandsBetween(IStandable standsOn) {
        if(standsOn.getIsStoodOnBy() instanceof Camel){
            IStandable re= standsOn.getIsStoodOnBy();
            this.standsOn = standsOn;
            standsOn.setIsStoodOnBy(this);
            return re;
        }
        return null;
    }


    /**
     * @return das Element das auf dem Kamel steht
     */
    public IStandable getIsStoodOnBy() {
        return isStoodOnBy;
    }

    /**
     * @param isStoodOnBy das neue Element, das auf dem Kamel steht
     */
    public void setIsStoodOnBy(IStandable isStoodOnBy) {
        this.isStoodOnBy = isStoodOnBy;
    }

    /** Checks for Stack position recursively
     *  for example Floor returns 0 so all Camels to the calling one add 1
     *
     * @return Recursivly goes through Camelstack until hitting the floor, then adding one for each camel
     */
    public int getStackPosition() {
        return standsOn.getStackPosition() + 1;
    }

    /** Class that returns Top Element of the contained stack recursively
     *
     * @return the top element of the stack this camel is contained in. May be itself
     */

    @Override
    public IStandable getTopElement() {
        if (isStoodOnBy == null) {
            return this;
        } else {
            return isStoodOnBy.getTopElement();
        }
    }

    /**
     *
     * @return das Feld auf dem das Kamel steht
     */
    public Tile getBottomElement() {
        if(standsOn instanceof Tile) {
            return (Tile) standsOn;
        } else {
            return ((Camel) standsOn).getBottomElement();
        }
    }

    /**
     * Bewegt das Kamel um eine bestimmte Anzahl an Schritten
     * @param pos
     * @param tile
     */
    public void moveToFeld(int pos, Tile tile) {
        this.position = pos;
        move(0, tile);
    }

    /**
     * @return das obere Element
     */
    public Camel getTopCamel() {
        return (Camel) getTopElement();
    }

    /**
     * @return die Farbe des Kamels
     */
    public Farbe getColor() {
        return color;
    }

    /**
     * Bewegt das Kamel
     * @param augenzahl nach der das Kamel bewegt wird
     * @param newTile das neue Feld auf dem das Kamel stehen soll
     */
    public void move(int augenzahl, Tile newTile) {
        this.position = newTile.getPosition();
        this.standsOn.setIsStoodOnBy(null);
        this.setStandsOn(newTile);
        if (this.isStoodOnBy != null) {
            ((Camel) this.isStoodOnBy).movedByCamel(augenzahl);
        }
    }
    public void moveToFatamorgana(int augenzahl, Tile newTile){
        if(newTile.getIsStoodOnBy()!=null&&!this.equals(newTile.getIsStoodOnBy())){
            this.position = newTile.getPosition();
            this.standsOn.setIsStoodOnBy(null);
            IStandable re=this.setStandsBetween(newTile);
            if (this.isStoodOnBy != null) {
                ((Camel) this.isStoodOnBy).movedByCamel(augenzahl);
            }
            re.setStandsOn(newTile);
            System.out.println(re);
        }else {
            this.move(augenzahl, newTile);
        }

    }


    /**
     * Setzt die Position der Kamele, die sich bewegt haben, um
     * @param augenzahl nach der Sich das Kamel unter dem betrachtetem Kamel bewegt hat
     */
    public void movedByCamel(int augenzahl) {
        this.position = ((Camel)this.standsOn).getPosition();
        if (this.isStoodOnBy != null) {
            ((Camel) this.isStoodOnBy).movedByCamel(augenzahl);
        }
    }


    /**
     * @return string des Kamels und seiner Position
     */
    @Override
    public String toString() {
        String camelString = color.toString() +", ";
        Camel current = this;
        if (current.getIsStoodOnBy() != null) {
            camelString += getIsStoodOnBy().toString();
        }
        return camelString;
    }



}

