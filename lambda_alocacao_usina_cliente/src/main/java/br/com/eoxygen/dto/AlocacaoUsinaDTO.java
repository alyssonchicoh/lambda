package br.com.eoxygen.dto;

public class AlocacaoUsinaDTO {

    private String cnpjClienteAntigo = "";
    private String cnpjClienteNovo;
    private String idUsinaBaseSinai;
    private String novoNomeTabelaLeituraDynamon;
    private String novoNomeTopicoIoT;
    private String novoNomeTopicoFirebase;
    private String idHospitalBaseCliente;
    private String nomeResponsavel;
    private String contatoResponsavel;


    public String getCnpjClienteAntigo() {
        return cnpjClienteAntigo;
    }

    public void setCnpjClienteAntigo(String cnpjClienteAntigo) {
        this.cnpjClienteAntigo = cnpjClienteAntigo;
    }

    public String getCnpjClienteNovo() {
        return cnpjClienteNovo;
    }

    public void setCnpjClienteNovo(String cnpjClienteNovo) {
        this.cnpjClienteNovo = cnpjClienteNovo;
    }

    public String getIdUsinaBaseSinai() {
        return idUsinaBaseSinai;
    }

    public void setIdUsinaBaseSinai(String idUsinaBaseSinai) {
        this.idUsinaBaseSinai = idUsinaBaseSinai;
    }

    public String getNovoNomeTabelaLeituraDynamon() {
        return novoNomeTabelaLeituraDynamon;
    }

    public void setNovoNomeTabelaLeituraDynamon(String novoNomeTabelaLeituraDynamon) {
        this.novoNomeTabelaLeituraDynamon = novoNomeTabelaLeituraDynamon;
    }

    public String getNovoNomeTopicoIoT() {
        return novoNomeTopicoIoT;
    }

    public void setNovoNomeTopicoIoT(String novoNomeTopicoIoT) {
        this.novoNomeTopicoIoT = novoNomeTopicoIoT;
    }

    public String getNovoNomeTopicoFirebase() {
        return novoNomeTopicoFirebase;
    }

    public void setNovoNomeTopicoFirebase(String novoNomeTopicoFirebase) {
        this.novoNomeTopicoFirebase = novoNomeTopicoFirebase;
    }

    public String getIdHospitalBaseCliente() {
        return idHospitalBaseCliente;
    }

    public void setIdHospitalBaseCliente(String idHospitalBaseCliente) {
        this.idHospitalBaseCliente = idHospitalBaseCliente;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getContatoResponsavel() {
        return contatoResponsavel;
    }

    public void setContatoResponsavel(String contatoResponsavel) {
        this.contatoResponsavel = contatoResponsavel;
    }
}
