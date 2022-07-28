package br.com.eoxygen.validador;

import br.com.eoxygen.dto.AlocacaoUsinaDTO;
import br.com.eoxygen.exception.CampoObrigatorioExcepetion;

public class Validador {

    public static void camposObrigatoriosPreenchidos(AlocacaoUsinaDTO alocacaoUsinaDTO) throws Exception{
        if(alocacaoUsinaDTO.getCnpjClienteNovo() == null || alocacaoUsinaDTO.getCnpjClienteNovo().equals("")){
            throw new CampoObrigatorioExcepetion("CnpjClienteNovo");
        }

        if(alocacaoUsinaDTO.getIdUsinaBaseSinai() == null || alocacaoUsinaDTO.getIdUsinaBaseSinai().equals("")){
            throw new CampoObrigatorioExcepetion("IdUsinaBaseSinai");
        }

        if(alocacaoUsinaDTO.getNovoNomeTabelaLeituraDynamon() == null || alocacaoUsinaDTO.getNovoNomeTabelaLeituraDynamon().equals("")){
            throw new CampoObrigatorioExcepetion("novoNomeTabelaLeituraDynamon");
        }

        if(alocacaoUsinaDTO.getNovoNomeTopicoIoT() == null || alocacaoUsinaDTO.getNovoNomeTopicoIoT().equals("")){
            throw new CampoObrigatorioExcepetion("novoNomeTopicoIoT");
        }

        if(alocacaoUsinaDTO.getNovoNomeTopicoFirebase() == null || alocacaoUsinaDTO.getNovoNomeTopicoFirebase().equals("")){
            throw new CampoObrigatorioExcepetion("novoNomeTopicoFirebase");
        }

        if(alocacaoUsinaDTO.getIdHospitalBaseCliente() == null || alocacaoUsinaDTO.getIdHospitalBaseCliente().equals("")){
            throw new CampoObrigatorioExcepetion("idHospitalBaseCliente");
        }

        if(alocacaoUsinaDTO.getNomeResponsavel() == null || alocacaoUsinaDTO.getNomeResponsavel().equals("")){
            throw new CampoObrigatorioExcepetion("nomeResponsavel");
        }

        if(alocacaoUsinaDTO.getContatoResponsavel() == null || alocacaoUsinaDTO.getContatoResponsavel().equals("")){
            throw new CampoObrigatorioExcepetion("contatoResponsavel");
        }
    }
}
