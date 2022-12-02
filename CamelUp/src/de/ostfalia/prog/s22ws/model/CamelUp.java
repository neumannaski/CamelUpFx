package de.ostfalia.prog.s22ws.model;

import de.ostfalia.prog.s22ws.base.Charakter;
import de.ostfalia.prog.s22ws.base.Farbe;
import de.ostfalia.prog.s22ws.base.ICamelUp;
import de.ostfalia.prog.s22ws.model.exceptions.UngueltigerZugException;
import de.ostfalia.prog.s22ws.model.felder.Plaettchen;
import de.ostfalia.prog.s22ws.model.felder.Wuestenplaettchen;

import java.io.FileNotFoundException;
import java.util.*;

public class CamelUp implements ICamelUp {

    private final Farbe[] farben = Farbe.values();
    private final Camel[] kamele = new Camel[farben.length];
    private Floor spielfeld = new Floor(18);

    public Floor getSpielfeld() {
        return spielfeld;
    }

    public void setSpielfeld(Floor spielfeld) {
        this.spielfeld = spielfeld;
    }

    public ArrayList<Charakter> getCharakterlist() {
        return charakterlist;
    }

    public void setCharakterlist(ArrayList<Charakter> charakterlist) {
        this.charakterlist = charakterlist;
    }

    public void setSpielerlist(ArrayList<Spieler> spielerlist) {
        this.spielerlist = spielerlist;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public Tile getFinishline() {
        return finishline;
    }

    public void setFinishline(Tile finishline) {
        this.finishline = finishline;
    }

    private Tile finishline = new Tile();
    private ArrayList<Charakter> charakterlist = new ArrayList<>(EnumSet.allOf(Charakter.class));
    private ArrayList<Spieler> spielerlist; // Hier werden die Spieler in einer Array-List gespeichert
    private boolean gameEnd;
    private HashMap<Farbe, Stack<Wettkarte>> wettkarten = new HashMap<Farbe, Stack<Wettkarte>>();
    private Queue<Wettkarte> tollesKamel=new ArrayDeque<>();
    private  Queue<Wettkarte> ollesKamel=new ArrayDeque<>();

    public Spieler getCurrentSpieler() {
        return currentSpieler;
    }

    public void setCurrentSpieler(Spieler currentSpieler) {
        this.currentSpieler = currentSpieler;
    }

    private Spieler currentSpieler;

    public Spieler getNextSpieler(){
        int index=spielerlist.lastIndexOf(currentSpieler);
        if(index+1>=spielerlist.size()){
            currentSpieler=spielerlist.get(0);
        }else {
            currentSpieler=spielerlist.get(++index);
        }
        return currentSpieler;
    }

    /**
     * Standard Konstruktor der die spielerlist verwendet
     */
    public CamelUp() {
        for (Farbe c: farben) {
            try{
                kamele[c.ordinal()] = new Camel(c);
            }catch (FileNotFoundException e){
                System.out.println(e);
            }
        }
        gameEnd = false;
        spielerlist = new ArrayList<Spieler>();
        Pyramide.getInstance().neueEtappe();
        etappenWettenGenerieren();
    }

    /**
     * Überladener Konstruktor, der die Charaktere der Liste hinzufügt und das Spiel startet
     *
     * @param charaktere die im Spiel vorkommen sollen
     */
    public CamelUp(Charakter... charaktere) {
        for (Farbe c: farben) {
            try{
                kamele[c.ordinal()] = new Camel(c);
            }catch (FileNotFoundException e){
                System.out.println(e);
            }

        }
        gameEnd = false;
        spielerlist = new ArrayList<Spieler>(charaktere.length);
        for (Charakter charakter: charaktere) {
            spielerlist.add(new Spieler( "", charakter));
        }
        etappenWettenGenerieren();
    }

    /**
     * Setzt die Kamele entsprechend der gewuerfelten Werte auf die Startposition
     * innerhalb des Spielfeldes.
     *
     * @implNote Implementierung wird ab Serie 1 verlangt
     */
    public void startPosition() {
        for (Farbe c: farben) {
            Wuerfel w = Pyramide.getInstance().getNextWuerfel();
            Camel k = getCamelByColor(w.getFarbe().name());
            k.setPosition(w.getAugenzahl(), spielfeld.goThroughFeld(spielfeld.getHead(), w.getAugenzahl()));
        }
        currentSpieler=spielerlist.get(0);
        Pyramide.getInstance().neueEtappe();

    }

    ;

    /**
     * Setzt die Kamele direkt auf die angegebenen Positionen innerhalb der
     * Rennstrecke. Die jeweiligen Kamele und deren Positionen werden je anhand
     * eines Strings in dem Format „FARBE:Feldnummer“ repräsentiert. Die Reihenfolge
     * der Angaben entspricht die aufsteigende Höhe des Kamels auf einem möglichen
     * Kamelturm. z.B.: "GRUEN:1","GELB:1","ORANGE:2","BLAU:2","WEISS:3"
     *
     * @param positionen Auflistung mit den Startposiotionen der Kamele.
     * @implNote Implementierung wird ab Serie 1 verlangt
     */
    public void startPosition(String... positionen) {
        for (String s : positionen) {
            Camel camel = getCamelByColor(s.split(":")[0].trim());
            int pos = Integer.parseInt(s.split(":")[1].trim());
            camel.setPosition(pos , spielfeld.getNumberedTile(pos));
        }
    }

    /**
     * Bewegt das Kamel mit der gewürfelten Farbe gemäß den in der Spielanleitung
     * definierten Regeln.
     *
     * @param charakter Charakter des Spielers.
     * @implNote Implementierung wird ab Serie 1 verlangt
     */
    public String bewegeKamel (Charakter charakter) {
        Wuerfel w = Pyramide.getInstance().getNextWuerfel();
        Camel k = getCamelByColor(w.getFarbe().name());
        pyramidenKarteGeben(getSpielerByCharacter(charakter));

        if (k.getPosition() + w.getAugenzahl() > 16 && !gameEnd ) {
            gameEnd = true;
            k.moveToFeld(1, finishline);
            werteKartenaus();

        }
        if(spielfeld.checkforOase()&& (k.getPosition() + w.getAugenzahl())==16){
            gameEnd = true;
            k.moveToFeld(1, finishline);
        }
        if (!gameEnd) {
            Tile nexttile=spielfeld.goThroughFeldwithCon(k.getBottomElement(), w.getAugenzahl());
            if(nexttile.getPosition() != k.getPosition() + w.getAugenzahl()) {
                if (nexttile.getNextTile() == spielfeld.getNumberedTile(k.getPosition() + w.getAugenzahl())) {
                    getSpielerByCharacter(((Wuestenplaettchen) nexttile.getNextTile().getIsStoodOnBy()).getCharakter()).addMoney(1);
                } else {
                    getSpielerByCharacter(((Wuestenplaettchen) nexttile.getBeforeTile().getIsStoodOnBy()).getCharakter()).addMoney(1);
                }
            }
            if(spielfeld.checkforFatamorgana(nexttile)){
                k.moveToFatamorgana(w.getAugenzahl(), nexttile);
            }else {
                k.move(w.getAugenzahl(), nexttile);
            }
        }
        if (Pyramide.getInstance().anzahlWuerfel() == 0) {
            neueEtappe();
            return " "+k.getColor().toString()+"  wurde um "+w.getAugenzahl()+ " Felder bewegt. \n Etappe beendet";
        }
        return " "+k.getColor().toString()+"  wurde um "+w.getAugenzahl()+ " Felder bewegt. \n \n";


    }

    /**
     * Liefert die Farbe des führenden Kamels.
     *
     * @return Die Farbe des führenden Kamels.
     * @implNote Implementierung wird ab Serie 1 verlangt
     */
    public Farbe fuehrendesKamel() {
        if (gameEnd) {
            return ((Camel) finishline.getTopElement()).getColor();
        }
        Tile current = spielfeld.getEnd();
        while (current.getIsStoodOnBy() == null || current.getIsStoodOnBy() instanceof Wuestenplaettchen) {
            current = current.getBeforeTile();
        }
        return ((Camel) current.getTopElement()).getColor();
    }

    ;

    /**
     * Liefert die Farbe des auf der Rennstrecke zweitplatzierten Kamels.
     *
     * @return Die Farbe des zweitplatzierten Kamels.
     * @implNote Implementierung wird ab Serie 3 verlangt
     */
    public Farbe zweitplatziertesKamel() {
        Tile current = spielfeld.getEnd();
        if (gameEnd) {
            if (finishline.getTopElement().getStandsOn() instanceof Camel) {
                return ((Camel) finishline.getTopElement().getStandsOn()).getColor();
            } else {
            while (current.getIsStoodOnBy() == null) {
                current = current.getBeforeTile();
            }
            return ((Camel) current.getTopElement()).getColor();
        }
        }

        current = spielfeld.getEnd();

        while (current.getIsStoodOnBy() == null) {
            current = current.getBeforeTile();
        }
        if (current.getTopElement().getStandsOn() instanceof Camel) {
            return ((Camel) current.getTopElement().getStandsOn()).getColor();
        } else {
            current = current.getBeforeTile();
            while (current.getIsStoodOnBy() == null) {
                current = current.getBeforeTile();
            }
        }
        return ((Camel) current.getTopElement()).getColor();
    }


    /**
     * Liefert die Farbe des letzten Kamels auf der Rennstrecke.
     *
     * @return die Farbe des letzten Kamels.
     * @implNote Implementierung wird ab Serie 1 verlangt
     */
    public Farbe letztesKamel() {
        Tile current = spielfeld.getHead();
        while (current.getIsStoodOnBy() == null || current.getIsStoodOnBy() instanceof Wuestenplaettchen) {
            current = current.getNextTile();
            if (current == null) {
                return ((Camel) finishline.getIsStoodOnBy()).getColor();
            }
        }

        return ((Camel) current.getIsStoodOnBy()).getColor();
    }


    /**
     * Liefert die Spielfeldnummer des Kamels mit der angegebenen Farbe.
     *
     * @param farbe Die Farbe des gesuchten Kamels.
     * @return Spielfeldnummer (1..16) des Kamels mit der angegebenen Farbe, oder 0,
     * wenn sich das Kamel nicht auf dem Spielfeld befindet.
     * @implNote Implementierung wird ab Serie 1 verlangt
     */
    public int feldNummer(Farbe farbe) {
        return getCamelByColor(farbe.name()).getPosition();

    }

    @Override
    public int feldNummer(Charakter charakter) {
        for (int i = 0; i < spielerlist.size(); i++) {
            if(spielerlist.get(i).getCharakter().equals(charakter)){
                return spielerlist.get(i).getCharakter().getPlaettchen().getPosition();
            }
        }
        return 0;
    }




    /**
     * Liefert die Stapelposition des Kamels mit der angegebenen Farbe.
     *
     * @param farbe Die Farbe des Kamels.
     * @return Die Höhe (1..5) des gesuchten Kamels auf dem Kamelturm, oder 0, wenn
     * sich das Kamel nicht auf dem Spielfeld befindet. Im Falle eines
     * Kamelturms, gilt 1 als die unterste Stelle.
     * @implNote Implementierung wird ab Serie 1 verlangt
     */
    public int stapelPosition(Farbe farbe) {
        return getCamelByColor(farbe.toString()).getStackPosition();
    }


    /** Wüstenplättchen legen und dem Plättchen einen Charakter zuweisen
     *
     * @param charakter Der Charakter des Spielers.
     * @param feld      Die Feldnummer (2..16), wo das Plättchen gelegt werden soll.
     * @param typ       Der Wert des Plaettchens (OASE oder FATAMORGANA).
     * @throws UngueltigerZugException
     */
    @Override
    public void legeWuestenplaettchen(Charakter charakter, int feld, Plaettchen typ) throws UngueltigerZugException {
            charakter.getSpielplatte().setPlaettchen(typ);
            if(spielfeld.checkFreeSpace(feld, typ, charakter)){
                spielfeld.legePlaettchen(feld, charakter.getPlaettchen());
            }else {
                throw new UngueltigerZugException();
            }
    }

    @Override
    public int guthaben(Charakter charakter) {
        try {
            return getSpielerByCharacter(charakter).getMoney();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void etappenWette(Charakter charakter, Farbe farbe) throws EmptyStackException {
        Spieler spieler = getSpielerByCharacter(charakter);
        Wettkarte karte = wettkarten.get(farbe).pop();
        spieler.addKarte(karte);
    }

    @Override
    public void wetteTollesKamel(Charakter charakter, Farbe farbe) {

        tollesKamel.add(new Wettkarte(farbe, charakter));

    }

    @Override
    public void wetteOllesKamel(Charakter charakter, Farbe farbe) {
        Wettkarte wette = new Wettkarte(farbe, charakter);
        if(!ollesKamel.contains(wette)) {
            ollesKamel.add(wette);
        }
    }

    @Override
    public List<Charakter> spielGewinner() {
        List<Charakter> charakters=new ArrayList<>();
        int mostmoney=0;
        if(gameEnd) {
            for (Spieler s:spielerlist) {
                if(charakters.isEmpty()||s.getMoney()>=mostmoney){
                    if(s.getMoney()>mostmoney){
                        charakters.clear();
                        mostmoney=s.getMoney();
                    }
                    charakters.add(s.getCharakter());
                }
            }
            return charakters;
        }
        return new ArrayList<>();
    }

    private void werteKartenaus(){
        Farbe camel1=fuehrendesKamel();
        Farbe camel2=letztesKamel();
        int[] money={8, 5, 3, 2, 1, 1, 1, 1, 1, 1};
        int index=0;
        while (!tollesKamel.isEmpty()){
            Wettkarte wettkarte=tollesKamel.poll();
            if(wettkarte.getFarbe().equals(camel1)){
                getSpielerByCharacter(wettkarte.getCharakter()).addMoney(money[index++]);
            }else {
                getSpielerByCharacter(wettkarte.getCharakter()).addMoney(-1);
            }
        }
        index=0;
        while (!ollesKamel.isEmpty()){
            Wettkarte wettkarte=ollesKamel.poll();
            if(wettkarte.getFarbe().equals(camel2)){
                getSpielerByCharacter(wettkarte.getCharakter()).addMoney(money[index++]);
            }else {
                getSpielerByCharacter(wettkarte.getCharakter()).addMoney(-1);
            }
        }
    }



    /**
     * Liefert das Kamel mit einer bestimmten Farbe
     * @param color die Farbe des Kamels, nach der wir suchen
     * @return das Camel mit der gesuchten Farbe
     */
    public Camel getCamelByColor(String color) {
        for (Camel camel : kamele) {

            if (camel.getColor().toString().equals(color.toUpperCase())) {
                return camel;
            }
        }
        return null;
    }

    /**
     * Liefert die Liste, in der die Spieler gespeichert sind
     *
     * @return die Liste der Spieler
     */
    public ArrayList<Spieler> getSpielerlist() {
        return spielerlist;
    }

    /**
     * Liefert true, wenn das Spiel beendet ist
     *
     * @return bool, ob das Spiel zu Ende ist
     */
    public boolean isGameEnd() {
        return gameEnd;
    }

    /**
     * Füge einen Spieler der Liste hinzu
     *
     * @param spieler der, der Liste hinzugefügt werden soll
     */
    public void spielerlistAdd(Spieler spieler) {
        spielerlist.add(spieler);
    }



    public void neueEtappe() {

        for (Spieler s: spielerlist) {
            s.getCharakter().getPlaettchen().reset();
        }
        // Wett- und Pyramidenkarten ausweten.
        for (int i = 0; i< spielerlist.size(); i++) {
            while (!spielerlist.get(i).isEmpty()) {
                Pyramidenkarte karte = spielerlist.get(i).getKarte();
                if (karte instanceof Wettkarte) {
                    spielerlist.get(i).addMoney(wettkarteAuswerten((Wettkarte) karte));
                } else {
                    spielerlist.get(i).addMoney(karte.getValue());
                }
            }
        }
        etappenWettenGenerieren();
        etappenWertung();
    }


    public void pyramidenKarteGeben(Spieler spieler) {
        spieler.addKarte(new Pyramidenkarte());
    }

    private int wettkarteAuswerten(Wettkarte karte) {
        if (karte.getFarbe() == fuehrendesKamel()){
            return karte.getValue();
        }
        if (karte.getFarbe() == zweitplatziertesKamel()) {
            return 1;
        }
        return -1;
    }

    public Spieler getSpielerByCharacter(Charakter c) throws NullPointerException{
        for (Spieler s: spielerlist) {
            if (s.getCharakter() == c) {
                return s;
            }
        }
        throw new NullPointerException();
    }

    public void etappenWettenGenerieren() {
        wettkarten.clear();
        for (Farbe farbe : farben) {
            Stack<Wettkarte> cardstack = new Stack<Wettkarte>();
            cardstack.add( new Wettkarte(farbe, 2));
            cardstack.add( new Wettkarte(farbe, 3));
            cardstack.add( new Wettkarte(farbe, 5));

            wettkarten.put(farbe, cardstack);
        }
    }

    /**
     * Gibt nach Ende einer Etappe den Stand der Kamele aus
     */
    public void etappenWertung() {

        System.out.println(this);
        System.out.printf("Etappe beendet! \n");
        System.out.printf("führendes Kamel: %s \n", fuehrendesKamel().toString());
        System.out.printf("letztes Kamel: %s \n", letztesKamel().toString());
        Pyramide.getInstance().neueEtappe();


    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();



        sb.append("Teilnehmende Spieler: ");

        for (Spieler s: spielerlist) {
            sb.append(s.toString());
        }
        sb.append("\n \n Pyramide enthält noch folgende Würfel: ");
        sb.append(Pyramide.getInstance().toString());

        sb.append("\n \n Spielfeld: \n");
        sb.append(spielfeld.toString());


        return sb.toString();
    }
    public List<List<IStandable>> toList(){
        System.out.println(spielfeld.toList().size());
        return spielfeld.toList();
    }

    public String spielAuswertung() {
        String ausgabe="Gewonnen hat:\n";
        List<Charakter> charakters=new ArrayList<>();
        int mostmoney=0;
        if(gameEnd) {
            for (Spieler s:spielerlist) {
                if(charakters.isEmpty()||s.getMoney()>=mostmoney){
                    if(s.getMoney()>mostmoney){
                        charakters.clear();
                        mostmoney=s.getMoney();
                    }
                    charakters.add(s.getCharakter());
                }
            }
            for (Charakter c:charakters) {
                ausgabe+=getSpielerByCharacter(c).getName()+" "+c+" ÄP: "+getSpielerByCharacter(c).getMoney()+"\n";
            }
            ausgabe+="\n\n Erstes Kamel: "+fuehrendesKamel().toString()+"\n\n " +
                    "Letztes Kamel: "+letztesKamel().toString();
        }
        return ausgabe;
    }
}
