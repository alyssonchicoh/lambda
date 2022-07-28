package br.com.eoxygen.exceptions;

public class CNPJClienteInvalidoException extends Exception{

    public CNPJClienteInvalidoException(){
        super("O CNPJ do cliente está inválido");
    }
}
