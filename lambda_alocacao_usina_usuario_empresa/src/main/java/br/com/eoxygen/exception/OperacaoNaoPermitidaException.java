package br.com.eoxygen.exception;

public class OperacaoNaoPermitidaException extends Exception{

    public OperacaoNaoPermitidaException(){
        super("Operacao Nao permitida. Escolha 1 para alocar e 2 para desalocar");
    }
}
