package br.com.eoxygen.exception;

public class UsinaNaoAlocadaAoClienteException extends Exception{

    public UsinaNaoAlocadaAoClienteException(){
        super("A Usina não está alocada ao cliente informado");
    }
}
