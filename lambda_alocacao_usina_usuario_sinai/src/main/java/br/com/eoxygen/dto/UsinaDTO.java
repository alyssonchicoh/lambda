package br.com.eoxygen.dto;

public class UsinaDTO {

    private String idUsinaBaseSinai;
    private String idUsinaBaseCliente;

    public UsinaDTO(){

    }
    public UsinaDTO(String idUsinaBaseSinai,String idUsinaBaseCliente){
        this.idUsinaBaseSinai = idUsinaBaseSinai;
        this.idUsinaBaseCliente = idUsinaBaseCliente;
    }

    public String getIdUsinaBaseSinai() {
        return idUsinaBaseSinai;
    }

    public void setIdUsinaBaseSinai(String idUsinaBaseSinai) {
        this.idUsinaBaseSinai = idUsinaBaseSinai;
    }

    public String getIdUsinaBaseCliente() {
        return idUsinaBaseCliente;
    }

    public void setIdUsinaBaseCliente(String idUsinaBaseCliente) {
        this.idUsinaBaseCliente = idUsinaBaseCliente;
    }
}
