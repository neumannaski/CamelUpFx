package de.ostfalia.prog.s22ws.model;

import de.ostfalia.prog.s22ws.base.Charakter;
import de.ostfalia.prog.s22ws.base.Farbe;
import de.ostfalia.prog.s22ws.model.exceptions.UngueltigerZugException;
import de.ostfalia.prog.s22ws.model.exceptions.UngueltigesFeldException;
import de.ostfalia.prog.s22ws.model.felder.Plaettchen;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.EnumSet;
import java.util.Scanner;

public class InputManager {


    /**
     * Hier startet das Spiel
     * Es werden die einzelnen Spieler dem Spiel hinzugefügt und es wird geschaut, ob die Charaktere nicht doppelt vorkommen
     * Mit "ready" kann das Spiel gestartet werden
     * @param args Jo args halt
     */
    public static void main(String[] args) {
        CamelUp spiel = new CamelUp();
        ArrayList<Charakter> charakterlist = new ArrayList<>(EnumSet.allOf(Charakter.class));
        boolean spieleranzahl = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Füge einen neuen Spieler hinzu (Spieler, Charakter) Gib ready ein, um das Spiel zu starten.");
        while (spieleranzahl) {
            System.out.print( "Verfügbare Charaktere: ");
            for (Charakter value : charakterlist) {
                System.out.print(value.toString() + " ");
            }
            System.out.print("\n");
            String eingabe = scanner.next();
            String name;
            if (eingabe.equals("ready")) {
                if (spiel.getSpielerlist().size() > 1) {
                    spieleranzahl = false;
                    System.out.println("Das Spiel wurde gestartet");
                    break;

                } else {
                    System.out.println("Es wurden noch nicht ausreichend Spieler hinzugefügt");
                }
            } else {
                name = eingabe;
                try {
                    Charakter charakter = Charakter.valueOf(scanner.next().toUpperCase());
                    if (charakterlist.contains(charakter)) {
                        charakterlist.remove(charakter);
                        spiel.spielerlistAdd(new Spieler(name, charakter));
                        System.out.println("Spieler wurde erstellt");
                    } else {
                        System.out.println("Charakter existiert bereits");
                    }
                } catch (Exception e) {
                    System.out.println("Error Charakter nicht vorhanden");
                }
            }
        }
        System.out.println("Spiel gestartet, Startposition Kamele ermittelt");
        spiel.startPosition();
        System.out.println();
        System.out.println(spiel);
        String action = scanner.nextLine();
        while (!spiel.isGameEnd()) {
            for(int i = 0; i < spiel.getSpielerlist().size(); i++) {
                Spieler s = spiel.getSpielerlist().get(i);
                System.out.printf("%s ist dran, Auswahl zwischen %s,%s", s.getName(),
                                    "(1) Würfel ziehen", "(2) Spielplan anzeigen (3) Plättchen (ver)-legen (4) Etappenwette abschließen"+
                                " (5) Auf tolles oder olles Kamel wetten \n");

                action = scanner.nextLine();
                switch (action.trim()) {
                    case "1", "Würfel ziehen" -> spiel.bewegeKamel(s.getCharakter());
                    case "2", "Spielplan anzeigen" -> {System.out.println(spiel);i--;}
                    case "3", "Platz und Plättchenart angeben (1) Wüste (2) Oase"->i+=legePlaettchen(s, spiel);
                    case "4", "Etappenwette abschließen" -> i += etappenWette(s, spiel);
                    case "5", "Auf tolles oder olles Kamel wetten" -> i += spielWette(s, spiel);

                    default -> {
                        System.out.println("Befehl nicht erkannt, Wiederholung"); i--;
                    }
                }

            }
        }
        System.out.printf("Das Spiel ist beendet, gewonnen hat das Kamel der Farbe %s", spiel.fuehrendesKamel().toString());
    }

    public static int spielWette(Spieler s, CamelUp spiel) {

        System.out.println("olles oder tolles?");
        Scanner scanner = new Scanner(System.in);
        String res = null;
        boolean scanning = true;
        while (scanning) {
            res = scanner.next();
            switch (res) {
                case "olles", "tolles" -> scanning = false;
                case "abbrechen" -> {
                    return -1;
                }
                default -> System.out.println("Nicht erkannt, versuch es nochmal");
            }

        }


        Farbe farbe = farbeInput();

        if (res.equals("olles")) {
            spiel.wetteOllesKamel(s.getCharakter(), farbe);

        } else {
            spiel.wetteTollesKamel(s.getCharakter(), farbe);
        }

        return 0;
    }

    public static Farbe farbeInput() {
        System.out.println("Wähle eine Farbe");
        Scanner scanner = new Scanner(System.in);
        Farbe farbe = null;
        String tryFarbe = null;
        while (farbe == null) {
            tryFarbe = scanner.next().toUpperCase();

            try {
                farbe = Farbe.valueOf(tryFarbe);
            } catch(IllegalArgumentException e) {
                System.out.println("ungültige Farbe, Versuchs nochmal");
                farbe = null;
            }
        }
        return farbe;
    }
    public static int etappenWette(Spieler s, CamelUp spiel) {
        Farbe farbe = farbeInput();
        try {
            spiel.etappenWette(s.getCharakter(), farbe);
        } catch (EmptyStackException e) {
            System.out.println("Wettstapel leer");
            return -1;
        }
        return 0;
    }

    /** Hier wird beim Befehl in der Spiellogik ein Wüstenplättchen gelegt
     *
     * @param s Spieler der für diese Tat in der Verantwortung steht
     * @param spiel gespieltes SPiel
     * @return if it worked
     */
    public static int legePlaettchen(Spieler s, CamelUp spiel){
        System.out.println("Platz und Plättchenart angeben (1) Wüste (2) Oase");
        Scanner scanner = new Scanner(System.in);
        int platz=scanner.nextInt();
        System.out.println(s.toString()+" "+s.getCharakter().toString());
        String action =scanner.nextLine();
        switch (action.trim()) {
            case "1":
                s.getCharakter().getPlaettchen().setPlaettchen( Plaettchen.FATAMORGANA);
                break;
            case "2":
                s.getCharakter().getPlaettchen().setPlaettchen( Plaettchen.OASE);
                break;

            default: {
                    System.out.println("Befehl nicht erkannt, Wiederholung");
                }
        }
            try {
                spiel.legeWuestenplaettchen(s.getCharakter(), platz, s.getCharakter().getPlaettchen().getPlaettchen());
                System.out.println("Plättchen wurde gelegt");
                System.out.println(spiel);
                return 0;
            }catch (UngueltigerZugException | UngueltigesFeldException e){
                System.out.println(e);
                return -1;
            }


    }
}

