package br.com.eoxygen.exception;

public class UsinaUsuarioJaAlocadoException extends Exception{

    public UsinaUsuarioJaAlocadoException(){
        super("A usina Ja Esta alocada ao usuario");
    }
}
