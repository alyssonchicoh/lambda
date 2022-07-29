package br.com.eoxygen.validador;

import br.com.eoxygen.dto.AlocacaoUsinaDTO;
import br.com.eoxygen.exception.CampoObrigatorioExcepetion;
import br.com.eoxygen.exception.ClienteNaoEncontradoException;
import br.com.eoxygen.exception.HospitalNaoEncontradoException;
import br.com.eoxygen.exception.UsinaNaoAlocadaAoClienteException;
import br.com.eoxygen.model.UsinaSinai;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;

import java.sql.ResultSet;

import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

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

    public static Long exiteCliente(GenericRepository repository,String cnpj) throws Exception{
        String condicao = " AND "+TABELA_CLIENTE_COLUNA_CNPJ +" = '"+ cnpj +"'";
        SelectSQLUtil colunas = new SelectSQLUtil();
        colunas.adicionaValor(TABELA_CLIENTE_COLUNA_ID);
        String sql = SQLUtil.select(NOME_SCHEMA_BASE,CLIENTE,new SelectSQLUtil(),condicao);
        ResultSet rs = repository.consultarDados(sql);

        if(rs.next()){
            Long id = rs.getLong(1);
            rs.close();
            return id;
        }

        throw new ClienteNaoEncontradoException(cnpj);
    }

    public static void exiteHospital(GenericRepository repository,String cnpj,String idHospital) throws Exception{
        String condicao = " AND "+TABELA_HOSPITAL_COLUNA_ID +" = "+idHospital;
        SelectSQLUtil colunas = new SelectSQLUtil();
        colunas.adicionaValor(TABELA_HOSPITAL_COLUNA_ID);
        String sql = SQLUtil.select("cliente_"+cnpj,HOSPITAL,new SelectSQLUtil(),condicao);
        ResultSet rs = repository.consultarDados(sql);

        if(!rs.next()){
            rs.close();
            throw new HospitalNaoEncontradoException();
        }
    }

    public static void usinaAlocadaACliente(UsinaSinai usina, Long idCliente) throws Exception{
        if(!usina.getIdCliente().equals(idCliente)){
            throw new UsinaNaoAlocadaAoClienteException();
        }
    }
}
