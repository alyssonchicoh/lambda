package br.com.eoxygen.exception;

public class HospitalNaoEncontradoException extends Exception{

    public HospitalNaoEncontradoException(){
        super("O ID Do Hospital não foi encontrado na base de dados do Cliente");
    }
}
