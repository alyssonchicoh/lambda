package br.com.eoxygen.exception;

public class CNPJObrigatorioException extends Exception{

    public CNPJObrigatorioException(){
        super("O CNPJ do cliente é obrigatório");
    }
}
