package br.com.eoxygen.exception;

public class ClienteNaoEncontradoException extends Exception{

    public ClienteNaoEncontradoException(String cnpj){
        super("O cliente com CNPJ "+cnpj + " não existe na base de dados");
    }
}
