package br.com.eoxygen.dto;

public class AlocacaoRetornoDTO {

    private Long idAlocacao;
    private Long idUsinaBaseClienteNovo;
    private Long idUsinaBaseSinai;

    public Long getIdUsinaBaseClienteNovo() {
        return idUsinaBaseClienteNovo;
    }

    public void setIdUsinaBaseClienteNovo(Long idUsinaBaseClienteNovo) {
        this.idUsinaBaseClienteNovo = idUsinaBaseClienteNovo;
    }

    public Long getIdAlocacao() {
        return idAlocacao;
    }

    public void setIdAlocacao(Long idAlocacao) {
        this.idAlocacao = idAlocacao;
    }

    public Long getIdUsinaBaseSinai() {
        return idUsinaBaseSinai;
    }

    public void setIdUsinaBaseSinai(Long idUsinaBaseSinai) {
        this.idUsinaBaseSinai = idUsinaBaseSinai;
    }
}
