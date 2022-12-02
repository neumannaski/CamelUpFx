package de.ostfalia.prog.s22ws.model;

import de.ostfalia.prog.s22ws.base.Charakter;
import de.ostfalia.prog.s22ws.model.exceptions.UngueltigesFeldException;
import de.ostfalia.prog.s22ws.model.felder.Plaettchen;
import de.ostfalia.prog.s22ws.model.felder.Wuestenplaettchen;

import java.util.ArrayList;
import java.util.List;


public class Floor {

    private Tile head;
    private Tile end;

    /**
     * Erstellt eine doppelt verkettete List nach der länge
     * @param length der Liste
     */
    public Floor(int length) {

        Tile[] tiles = new Tile[length];

        tiles[0] = new Tile();
        head = tiles[0];
        head.setBeforeTile(null);
        tiles[0].setPosition(0);

        for (int i = 1; i <= tiles.length - 1; i++) {
            tiles[i] = new Tile();
            tiles[i].setPosition(i);
            tiles[i - 1].setNextTile(tiles[i]);
            tiles[i].setBeforeTile(tiles[i - 1]);
        }
        end = tiles[length - 1];
        end.setNextTile(null);
    }

    /**
     * Liefert den Kopf der Liste
     * @return kopf der Liste
     */
    public Tile getHead() {
        return head;
    }

    /**
     * Liefert die Stelle, wenn man von dem Ausgangspunkt eine bestimmte Anzahl an Schritten geht
     * @param moves die Anzahl an Schritten die gegangen werden sollen
     * @param tile Ausgangspunkt
     * @return Tile nach einer bestimmten Anzahl Schritten
     */
    public Tile goThroughFeld(Tile tile, int moves) {
        Tile currentTile = tile;
        for (int i = 0; i < moves; i++) {
            currentTile = currentTile.getNextTile();
        }
        return currentTile;
    }

    public  Tile goThroughFeldwithCon (Tile tile, int moves){
        Tile currentTile =goThroughFeld(tile, moves);
        if (currentTile.getIsStoodOnBy() !=null){
            if (currentTile.getIsStoodOnBy() instanceof Wuestenplaettchen){
                Wuestenplaettchen typ=(Wuestenplaettchen) currentTile.getIsStoodOnBy();
                switch (typ.getPlaettchen()){
                    case  FATAMORGANA:  return currentTile.getBeforeTile();
                    case OASE: return currentTile.getNextTile();
                    default: return currentTile;
                }
            }
        }
        return currentTile;
    }

    /**
     * Setzt den Kopf der Liste
     * @param head neuer Kopf der Liste
     */
    public void setHead(Tile head) {
        this.head = head;
    }

    /**
     * Liefert das Ende der Liste
     * @return Ende der Liste
     */
    public Tile getEnd() {
        return end;
    }

    /**
     * Setzt ein neues Ende der Liste
     * @param end das neue Ende der Liste
     */
    public void setEnd(Tile end) {
        this.end = end;
    }

    /**
     * Liefert das Feld mit der gesuchten Nummer
     * @param tileNumber nach der wir suchen
     * @return das gesuchte Feld
     */
    public Tile getNumberedTile (int tileNumber)  {
        return goThroughFeld(head, tileNumber );
    }


    /**
     * @return Übersicht über die Liste
     */
    @Override
    public String toString() {
        String floorstring = "Kamelrennen:\n";
        Tile current = head;
        while (current.getNextTile() != null) {
            floorstring += current.toString() +"\n";
            current = current.getNextTile();
        }
        return floorstring;
    }

    public List<List<IStandable>> toList(){
        List<List<IStandable>> list=new ArrayList<>();
        Tile current = head;
        while (current.getNextTile() != null) {
            list.add(current.toList());
            current = current.getNextTile();
        }
        return list;
    }

    public List<Tile> tilestolist(){
        List<Tile> list=new ArrayList<>();
        Tile current = head;
        while (current.getNextTile() != null) {
            list.add(current);
            current = current.getNextTile();
        }
        return list;

    }

    public boolean checkFreeSpace (int platz, Plaettchen plaettchen, Charakter charakter) {
        if(platz<1||platz>16 ){
            throw new UngueltigesFeldException();
        }else {
            Tile tiletoplace =getNumberedTile(platz);
            if(tiletoplace.getIsStoodOnBy()!=null){
                return false;
            }
            if(platz==1&&plaettchen.equals(Plaettchen.FATAMORGANA)){
                return false;
            }
            if( tiletoplace.getBeforeTile().getIsStoodOnBy()!=null){
                Tile before=tiletoplace.getBeforeTile();
                if ((before.getIsStoodOnBy() instanceof Wuestenplaettchen)) {
                    Charakter c = (Charakter) ((Wuestenplaettchen) before.getIsStoodOnBy()).getCharakter();
                    if (c != charakter) {
                        return false;
                    }
                }
            }
            if( tiletoplace.getNextTile().getIsStoodOnBy()!=null) {
                Tile after = tiletoplace.getNextTile();
                if ((after.getIsStoodOnBy() instanceof Wuestenplaettchen)) {
                    Charakter c = (Charakter) ((Wuestenplaettchen) after.getIsStoodOnBy()).getCharakter();
                    if (c != charakter) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
    public void legePlaettchen (int platz, Wuestenplaettchen platte) {
            Tile tiletoplace =getNumberedTile(platz);
            platte.setStandsOn(tiletoplace);

    }


    public boolean checkforFatamorgana(Tile tile){
        Tile tilebefore=tile.getNextTile();
        if(tilebefore.getIsStoodOnBy()!=null&& tilebefore.getIsStoodOnBy()instanceof Wuestenplaettchen){
            if(((Wuestenplaettchen) tilebefore.getIsStoodOnBy()).getPlaettchen().equals(Plaettchen.FATAMORGANA)){
                return true;
            }
        }
        return false;
    }

    public boolean checkforOase(){
        Tile tile=getNumberedTile(16);
        if(tile.getIsStoodOnBy()!=null){
           if(tile.getIsStoodOnBy() instanceof Wuestenplaettchen){
               Wuestenplaettchen wuestenplaettchen=(Wuestenplaettchen) tile.getIsStoodOnBy();
               if (wuestenplaettchen.getPlaettchen().equals(Plaettchen.OASE)){
                   return true;
               }
           }

        }
        return false;
    }


}
