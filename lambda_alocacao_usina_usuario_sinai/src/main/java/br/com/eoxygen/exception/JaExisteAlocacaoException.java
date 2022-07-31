package br.com.eoxygen.exception;

public class JaExisteAlocacaoException extends Exception{

    public JaExisteAlocacaoException(){
        super("Já existe alocacao para o usuario e usina");
    }
}
