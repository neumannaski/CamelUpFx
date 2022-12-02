package de.ostfalia.prog.s22ws.base;


import de.ostfalia.prog.s22ws.model.felder.Wuestenplaettchen;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Die Aufzählungsklasse Charakter definiert die möglichen Charaktere der
 * Spielfiguren innerhalb des Spiels Camel Up.
 * @author Vorgegeben
 */
public enum Charakter {
	LADY, PRINZESSIN, MAEDCHEN, DAME, SCHEICH, HAENDLER, GENTLEMAN, ABENTEURER;

	Wuestenplaettchen spielplatte;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	Image image;

	Charakter(){
		spielplatte=new Wuestenplaettchen(this);
		try{
			image=new Image( new FileInputStream(Filemanager.path+""+this+".jpg"));
		}catch (FileNotFoundException e){
			System.out.println(e);
		}

	}

	public Wuestenplaettchen getSpielplatte() {
		return spielplatte;
	}

	public void setSpielplatte(Wuestenplaettchen spielplatte) {
		this.spielplatte = spielplatte;
	}

	public Wuestenplaettchen getPlaettchen() {
		return spielplatte;
	}
}
