package de.ostfalia.prog.s22ws.model.exceptions;

/**
 *  Wird geworfen, wenn Das Plättchen auf ein ungültiges Feld gelegt wird
 */
public class UngueltigesFeldException extends RuntimeException{

    String message;

    public UngueltigesFeldException(){
        this.message="UngueltigesFeldException";
    }
    public UngueltigesFeldException(String message){
        this.message=message;
    }

    public String toString(){
        return message;
    }
}
