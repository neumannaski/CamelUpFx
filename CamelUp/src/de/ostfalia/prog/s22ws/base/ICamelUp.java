package de.ostfalia.prog.s22ws.base;

import de.ostfalia.prog.s22ws.model.felder.Plaettchen;
import de.ostfalia.prog.s22ws.model.exceptions.UngueltigerZugException;

import java.util.List;

/**
 * Das Interface ICamelUp stellt u.a. die Schnittstelle zwischen den
 * automatisierten JUnit-Testfälle und Ihre Implementierung dar. Es wird dadurch
 * festgelegt, welche Methoden in die entsprechende Klasse (mindestens)
 * implementiert werden müssen.
 * 
 * Die Methoden werden teilweise im laufe des Semesters implementiert. Es ist in
 * jeder Methode anhand des Tags "@implNote" erkennbar, ab welche Serie sie
 * spätestens relevant ist.
 */
public interface ICamelUp {

	/**
	 * Setzt die Kamele entsprechend der gewuerfelten Werte auf die Startposition
	 * innerhalb des Spielfeldes.
	 * 
	 * @implNote Implementierung wird ab Serie 1 verlangt
	 */
	public void startPosition();

	/**
	 * Setzt die Kamele direkt auf die angegebenen Positionen innerhalb der
	 * Rennstrecke. Die jeweiligen Kamele und deren Positionen werden je anhand
	 * eines Strings in dem Format „FARBE:Feldnummer“ repräsentiert. Die Reihenfolge
	 * der Angaben entspricht die aufsteigende Höhe des Kamels auf einem möglichen
	 * Kamelturm. z.B.: "GRUEN:1","GELB:1","ORANGE:2","BLAU:2","WEISS:3"
	 * 
	 * @param positionen Auflistung mit den Startposiotionen der Kamele.
	 * 
	 * @implNote Implementierung wird ab Serie 1 verlangt
	 */
	public void startPosition(String... positionen);

	/**
	 * Bewegt das Kamel mit der gewürfelten Farbe gemäß den in der Spielanleitung
	 * definierten Regeln.
	 * 
	 * @param charakter Charakter des Spielers.
	 * 
	 * @implNote Implementierung wird ab Serie 1 verlangt
	 */
	public String bewegeKamel(Charakter charakter);

	/**
	 * Liefert die Farbe des führenden Kamels.
	 * 
	 * @return Die Farbe des führenden Kamels.
	 * 
	 * @implNote Implementierung wird ab Serie 1 verlangt
	 */
	public Farbe fuehrendesKamel();

	/**
	 * Liefert die Farbe des auf der Rennstrecke zweitplatzierten Kamels.
	 * 
	 * @return Die Farbe des zweitplatzierten Kamels.
	 * 
	 * @implNote Implementierung wird ab Serie 3 verlangt
	 */
	public Farbe zweitplatziertesKamel();

	/**
	 * Liefert die Farbe des letzten Kamels auf der Rennstrecke.
	 * 
	 * @return die Farbe des letzten Kamels.
	 * 
	 * @implNote Implementierung wird ab Serie 1 verlangt
	 */
	public Farbe letztesKamel();

	/**
	 * Liefert die Spielfeldnummer des Kamels mit der angegebenen Farbe.
	 * 
	 * @param farbe Die Farbe des gesuchten Kamels.
	 * @return Spielfeldnummer (1..16) des Kamels mit der angegebenen Farbe, oder 0,
	 *         wenn sich das Kamel nicht auf dem Spielfeld befindet.
	 * 
	 * @implNote Implementierung wird ab Serie 1 verlangt
	 */
	public int feldNummer(Farbe farbe);

	/**
	 * Liefert die Feldnummer, wo das Wüstenplättchen des angegebenen Charakters
	 * sich befindet.
	 * 
	 * @param charakter Der Charakter dessen Wuestenplaettchen gesucht wird
	 * @return Spielfeldnummer (2..16) des Wuestenplaettchens mit dem angegebenen
	 *         Charakter, oder 0, wenn sich das Plaettchen nicht auf dem Spielfeld
	 *         befindet.
	 * 
	 * @implNote Implementierung wird ab Serie 2 verlangt
	 */
	public int feldNummer(Charakter charakter);

	/**
	 * Liefert die Stapelposition des Kamels mit der angegebenen Farbe.
	 * 
	 * @param farbe Die Farbe des Kamels.
	 * @return Die Höhe (1..5) des gesuchten Kamels auf dem Kamelturm, oder 0, wenn
	 *         sich das Kamel nicht auf dem Spielfeld befindet. Im Falle eines
	 *         Kamelturms, gilt 1 als die unterste Stelle.
	 * 
	 * @implNote Implementierung wird ab Serie 1 verlangt
	 */
	public int stapelPosition(Farbe farbe);

	// ---------------------------------------------------------------------------------------------
	/**
	 * Legt das Wuestenplaettchen des Charakters mit dem angegebenen Typ auf das
	 * Feld.
	 * 
	 * @param charakter Der Charakter des Spielers.
	 * @param feld      Die Feldnummer (2..16), wo das Plättchen gelegt werden soll.
	 * @param typ       Der Wert des Plaettchens (OASE oder FATAMORGANA).
	 * @throws UngueltigerZugException,  wenn das Legen des Plaettchens auf das Feld
	 *                                   nicht zulässig ist.
	 * @throws UngueltigerFeldException, wenn das Plaettchen auf ein Feld außerhalb
	 *                                   des Spielfelds bzw. der Rennstrecke gelegt
	 *                                   werden soll.
	 * 
	 * @implNote Implementierung wird ab Serie 2 verlangt
	 */
	 public void legeWuestenplaettchen(Charakter charakter, int feld, Plaettchen typ) throws UngueltigerZugException;

	// ---------------------------------------------------------------------------------------------

	/**
	 * Liefert das Guthaben des angegebenen Charakters.
	 * 
	 * @param charakter Der Charakter des Spielers.
	 * @return Das Guthaben des Charakters in Ägyptische Pfund, oder 0, wenn sich
	 *         der Charakter nicht am Spiel beteiligt.
	 * 
	 * @implNote Implementierung wird ab Serie 3 verlangt
	 */
	public int guthaben(Charakter charakter);

	/**
	 * Der angegebenen Charakter wettet auf den Etappensieg des Kamels mit der
	 * angegebenen Farbe.
	 * 
	 * @param charakter Der Charakter, der die Wette abgibt.
	 * @param farbe     Die Farbe des Kamels, worauf gewettet wird.
	 * 
	 * @implNote Implementierung wird ab Serie 3 verlangt
	 */
	public void etappenWette(Charakter charakter, Farbe farbe);

	/**
	 * Der Charakter wettet auf das tolle Kamel (den Gesamtsieger des Spiels) mit
	 * der angegebenen Farbe.
	 * 
	 * @param charakter Der Charakter, der die Wette abgibt.
	 * @param farbe     Die Farbe des Kamels.
	 * 
	 * @implNote Implementierung wird ab Serie 3 verlangt
	 */
	public void wetteTollesKamel(Charakter charakter, Farbe farbe);

	/**
	 * Der Charakter wettet auf das olle Kamel (den Gesamtletzten) mit der
	 * angegebenen Farbe.
	 * 
	 * @param charakter Der Charakter, der die Wette abgibt.
	 * @param farbe     Die Farbe des Kamels.
	 * 
	 * @implNote Implementierung wird ab Serie 3 verlangt
	 */
	public void wetteOllesKamel(Charakter charakter, Farbe farbe);

	/**
	 * Liefert den bzw. die Spielgewinner. Steht noch kein Gewinner fest, liefert
	 * die Methode eine leere Liste.
	 * 
	 * @return Liste der Charaktere der Spielgewinner.
	 * 
	 * @implNote Implementierung wird ab Serie 3 verlangt
	 */
	public List<Charakter> spielGewinner();

}
