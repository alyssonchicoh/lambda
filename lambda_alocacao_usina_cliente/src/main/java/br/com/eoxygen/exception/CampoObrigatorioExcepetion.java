package br.com.eoxygen.exception;

public class CampoObrigatorioExcepetion extends Exception{

    public CampoObrigatorioExcepetion(String campo){
        super("O Campo " + campo +" é obrigatório");
    }
}
