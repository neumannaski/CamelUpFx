package de.ostfalia.prog.s22ws.model.felder;


/**
 * Enthält Wirkungstypen von setzbaren Wüstenplättchen
 */
public enum Plaettchen  {

    OASE ("Oase"), FATAMORGANA("Fatamorgana");

    String typ;

    Plaettchen(String typ){
       this.typ=typ;
    }

    public void setTyp(String typ){
        this.typ=typ;
    }

    public String getTyp(){
        return this.typ;
    }


}
