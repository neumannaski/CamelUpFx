package de.ostfalia.prog.s22ws.model;

import de.ostfalia.prog.s22ws.base.Farbe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Pyramide {

    private static final Pyramide PYRAMIDE = new Pyramide(); // Die statische Instanz der Pyramide
    private  ArrayList <Wuerfel> wuerfelpyramide= new ArrayList<Wuerfel>(5);  // List in der die Würfel ligen
    private final Farbe[] farben = Farbe.values(); // Vorlage der Würfelfarben


    private Pyramide() {

    }
    /**
     * der Würfelpyramide werden nach der Vorgabe im String Format die Würfel der Reihe nach hinzugefügt
     * @param wuerfel
     */

    public void initialisiere(String... wuerfel){
        wuerfelpyramide.clear();
        for (String w:wuerfel) {
            w = w.trim();
            Wuerfel wuerfelins=new Wuerfel(w);
            wuerfelpyramide.add(wuerfelins);
        }
    }
    /**
     * Fügt die Würfel wieder der Pyramiode hinzu und würfelt sie in der Pyramide durch
     */
    public void neueEtappe() {
        wuerfelpyramide.clear();
        Random rand = new Random();
        ArrayList<Farbe> remainingColor = new ArrayList<Farbe>(List.of(Farbe.values()));

        for (int i = 0; i < farben.length; i++ ) {
           int wuerfel = rand.nextInt(remainingColor.size());
           wuerfelpyramide.add(new Wuerfel(remainingColor.get(wuerfel)));
           remainingColor.remove(wuerfel);
        }
    }

    /**
     * Gibt die statische Instanz Pyramide zurück
     * @return Pyramide
     */
    public static Pyramide getInstance() {
        return PYRAMIDE;
    }

    /**
     * @return die Anzahl der Würfel, die noch in der Pyramide sind
     */
    public int anzahlWuerfel(){
        return wuerfelpyramide.size();
    }

    /**
     * @return den Würfel in der Reihenfolge der Pyramide
     */

    public Wuerfel getNextWuerfel(){
        if(wuerfelpyramide.size()>0){
            Wuerfel wuerfel=wuerfelpyramide.get(0);
            wuerfelpyramide.remove(0);
            return wuerfel;
        }else {
            return null;
        }
    }
    /**
     + Fügt der Arraylist neue Würfel hinzu
     */
    private void fuegeWuerfelhinzu(){
        for (int i=0; i< farben.length;i++){
            this.wuerfelpyramide.add(new Wuerfel(farben[i]));
        }
    }

    @Override
    public String toString() {
        String wuerfles = "";
        for (Wuerfel w: wuerfelpyramide) {
            wuerfles += w.toString();
        }
        return wuerfles;
    }
}
