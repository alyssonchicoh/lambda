package br.com.eoxygen.exception;

public class CPFJaExistenteException extends Exception{

    public CPFJaExistenteException(){
        super("O CPF informado já está cadastrado na base de dados");
    }
}
