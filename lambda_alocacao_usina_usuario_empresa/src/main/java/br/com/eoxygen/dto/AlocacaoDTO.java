package br.com.eoxygen.dto;

import java.util.List;

public class AlocacaoDTO extends  GenericDTO{

    private List<String> idUsina;
    private String idUsuario;
    //1 - ALOCAR, 2- DESALOCAR
    private Integer operacao;

    public List<String> getIdUsina() {
        return idUsina;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public Integer getOperacao() {
        return operacao;
    }

    public void setIdUsina(List<String> idUsina) {
        this.idUsina = idUsina;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setOperacao(Integer operacao) {
        this.operacao = operacao;
    }
}
