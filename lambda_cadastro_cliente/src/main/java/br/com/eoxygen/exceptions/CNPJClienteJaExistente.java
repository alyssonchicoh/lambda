package br.com.eoxygen.exceptions;

public class CNPJClienteJaExistente extends Exception {

    public CNPJClienteJaExistente(){
        super("Ja Existe um cliente cadastrado com esse CNPJ");
    }
}
