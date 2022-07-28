package br.com.eoxygen.exceptions;

public class CampoObrigatorioException extends Exception{

    public CampoObrigatorioException(String nomeCampo){
        super("O Campo"+ nomeCampo +" é obrigatório");
    }
}
