package de.ostfalia.prog.s22ws.model;
import java.util.Random;
import de.ostfalia.prog.s22ws.base.Farbe;

/** Die Würfel Klasse stellt die Würfel für CamelUp bereit. Sie werden mit einer Farbe und festgelegten AUgenzahl generiert.
 *
 * @author Michael Neumann
 */
public class Wuerfel {


    private final Farbe farbe;
    private final int augenzahl;

    /**
     * Konstruktor für den Fall, dass wir nur die Farbe haben
     * @param farbe di Farbe des würfels
     */

    public Wuerfel (Farbe farbe){
        Random rand = new Random();
        this.farbe = farbe;
        this.augenzahl = rand.nextInt(3) + 1;
    }

    /**
     * Konstruktor für den Fall, dass die Vorgabe durch einen String erfolgt
     * @param string die Voragbe als String
     */

    public Wuerfel (String string){
        String[] parts = string.split(":");
        this.farbe=Farbe.valueOf(parts[0]);
        this.augenzahl=Integer.parseInt(parts[1]);
    }


    public Farbe getFarbe() {
        return farbe;
    }

    public int getAugenzahl() {
        return augenzahl;
    }

    /** To String gibt die generierte Augenzahl und Farbe aus
     *
     * @return FARBE + Augenzahl
     */
    @Override
    public String toString(){
        return farbe.toString() + " ";
    }

}
