package br.com.eoxygen.dto;

import java.util.List;

public class AlocacaoDTO {

    private String idUsuarioBaseSinai;
    private String cnpjClienteDestino;
    private String idClienteBaseSinai;
    List<UsinaDTO> usinas;
    //1 - ALOCAR, 2- DESALOCAR
    private Integer operacao;

    public String getIdUsuarioBaseSinai() {
        return idUsuarioBaseSinai;
    }

    public void setIdUsuarioBaseSinai(String idUsuarioBaseSinai) {
        this.idUsuarioBaseSinai = idUsuarioBaseSinai;
    }

    public String getCnpjClienteDestino() {
        return cnpjClienteDestino;
    }

    public void setCnpjClienteDestino(String cnpjClienteDestino) {
        this.cnpjClienteDestino = cnpjClienteDestino;
    }


    public Integer getOperacao() {
        return operacao;
    }

    public void setOperacao(Integer operacao) {
        this.operacao = operacao;
    }

    public String getIdClienteBaseSinai() {
        return idClienteBaseSinai;
    }

    public void setIdClienteBaseSinai(String idClienteBaseSinai) {
        this.idClienteBaseSinai = idClienteBaseSinai;
    }

    public List<UsinaDTO> getUsinas() {
        return usinas;
    }

    public void setUsinas(List<UsinaDTO> usinas) {
        this.usinas = usinas;
    }
}
