package de.ostfalia.prog.s22ws.model;


import javafx.scene.image.Image;

public interface IStandable{



    /**
     *  Setzt das Objekt auf dem this steht
     * @param standable auf dem das momentane Objekt steht
     */
    void setStandsOn(IStandable standable);
    IStandable getStandsOn();

    /**
     *  Setzt das Objekt, das auf this steht
     * @param standable das Objekt das auf this steht
     */
    void setIsStoodOnBy(IStandable standable);
    IStandable getIsStoodOnBy();

    /**
     *  Liefert die Stack Position des Objekts
     * @return int wo sich das Objekt im Stack befindet
     */
    int getStackPosition();

    /**
     * @return Das Objekt des Topelements des Stacks
     */
    IStandable getTopElement();


    Image getImage();
}