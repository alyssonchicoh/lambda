package br.com.eoxygen.exception;

public class HospitalNaoEncontradoException extends Exception{

    public HospitalNaoEncontradoException(String cnpj){
        super("O ID Do Hospital não foi encontrado na base de dados do Cliente");
    }
}
