package de.ostfalia.prog.s22ws.model.exceptions;

/**
 *
 */
public class UngueltigerZugException extends Exception{


    String message;

    public  UngueltigerZugException(){
        message="UngueltigerZugException";
    }
    public  UngueltigerZugException(String message){
        this.message=message;
    }

    public String toString(){
        return message;
    }
}
