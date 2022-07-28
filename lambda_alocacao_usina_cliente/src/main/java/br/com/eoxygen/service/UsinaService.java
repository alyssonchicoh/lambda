package br.com.eoxygen.service;


import static br.com.eoxygen.enuns.TipoCampo.*;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

import br.com.eoxygen.dto.AlocacaoRetornoDTO;
import br.com.eoxygen.dto.AlocacaoUsinaDTO;
import br.com.eoxygen.model.Usina;
import br.com.eoxygen.model.UsinaSinai;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.InsertSQLUtil;
import br.com.eoxygen.util.NomeTabelaUtil;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;
import org.checkerframework.checker.units.qual.A;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsinaService {
    private String nomeSchema;
    private GenericRepository repository;
    private String nomeSchemaSinai = "sinai";
    private Long idCliente;


    public UsinaService(){
        this.repository = new GenericRepository();
        this.nomeSchema = "cliente_";
    }

    public static void main(String[] args) throws Exception{
        UsinaService usinaService = new UsinaService();
        AlocacaoUsinaDTO dto = new AlocacaoUsinaDTO();
        dto.setCnpjClienteNovo("88888888888");
      //  dto.setCnpjClienteAntigo("2");
        dto.setIdUsinaBaseSinai("1");
        dto.setNovoNomeTopicoFirebase("firebase_usina_01");
        dto.setNovoNomeTopicoIoT("topico_usina_01");
        dto.setNovoNomeTabelaLeituraDynamon("leituras_usina_01");
        usinaService.alocar(dto);


    }

    public AlocacaoRetornoDTO alocar(AlocacaoUsinaDTO dto) throws Exception{
       String schemaClienteAntigo = "cliente_"+dto.getCnpjClienteAntigo();
       String schemaClienteNovo = "cliente_"+dto.getCnpjClienteNovo();

       UsinaSinai usina = this.consultarUsinaBaseSinai(dto.getIdUsinaBaseSinai());

       if(!dto.getCnpjClienteAntigo().equals("")){
           this.desativarUsinaSchemaClienteAntigo(schemaClienteAntigo,usina.getId().toString());
       }

       this.atualizarClienteUsinaBaseSinai(dto.getIdUsinaBaseSinai(),dto.getCnpjClienteNovo());

       Long idAloacao = inserirAlocacaoUsinaCliente(usina.getId().toString());
       Long idUsinaClienteNovo = this.inserirUsinaSchemaClienteNovo(usina,usina.getId().toString(),dto,idAloacao,dto.getNomeResponsavel(),dto.getContatoResponsavel(),dto.getIdHospitalBaseCliente());

        AlocacaoRetornoDTO retorno = new AlocacaoRetornoDTO();
        retorno.setIdAlocacao(idAloacao);
        retorno.setIdUsinaBaseClienteNovo(idUsinaClienteNovo);
        retorno.setIdUsinaBaseSinai(usina.getId());

       return retorno;

    }

    private UsinaSinai consultarUsinaBaseSinai(String idUsina) throws Exception{
        String condicao = " AND "+TABELA_USINA_COLUNA_ID +" = "+ idUsina;
        String sql = SQLUtil.select(nomeSchemaSinai,USINA,new SelectSQLUtil(),condicao);
        ResultSet rs = this.repository.consultarDados(sql);
        UsinaSinai usina = null;
        while(rs.next()) {
            usina = new UsinaSinai();
            usina.setId(rs.getLong(TABELA_USINA_SINAI_COLUNA_ID));
            usina.setNome(rs.getString(TABELA_USINA_SINAI_COLUNA_NOME));
            usina.setFabricante(rs.getString(TABELA_USINA_COLUNA_FABRICANTE));
            usina.setNumeroSerie(rs.getString(TABELA_USINA_SINAI_COLUNA_NUMERO_SERIE));
            usina.setDataConstrucao(rs.getDate(TABELA_USINA_SINAI_COLUNA_DATA_CONSTRUCAO));
            usina.setDataCompra(rs.getDate(TABELA_USINA_SINAI_COLUNA_DATA_COMPRA));
            usina.setStatus(rs.getString(TABELA_USINA_SINAI_COLUNA_STATUS));
            usina.setIdCLP(rs.getString(TABELA_USINA_SINAI_COLUNA_ID_CLP));
        }
        return usina;
    }

    private void desativarUsinaSchemaClienteAntigo(String schemaClienteAntigo,String idUsinaSchemaBase) throws Exception{
        String sql = "UPDATE "+schemaClienteAntigo + "." + USINA + " SET " +TABELA_USINA_COLUNA_ATIVADA +
                " = FALSE WHERE "+TABELA_USINA_COLUNA_ID_USINA_SCHEMA_SINAI + " = "+idUsinaSchemaBase;
        System.out.println(sql);
        this.repository.inseirDados(sql);
    }

    private Long inserirUsinaSchemaClienteNovo(UsinaSinai usina,String idUsinaSchemaBase, AlocacaoUsinaDTO dto,Long idAlocacao,String nomeResponsavel,String contatoResponsavel,String idHospital) throws Exception{
        String schemaClienteNovo = "cliente_"+dto.getCnpjClienteNovo();
        List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_NOME,usina.getNome()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_NUMERO_SERIE,usina.getNumeroSerie()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_FABRICANTE,usina.getFabricante()));

        // dados.add(new InsertSQLUtil(DATA,TABELA_USINA_COLUNA_DATA_CONSTRUCAO,usina.getDataConstrucao()));
      //  dados.add(new InsertSQLUtil(DATA,TABELA_USINA_COLUNA_DATA_COMPRA,usina.getNome()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_STATUS,usina.getStatus()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_NOME_TABELA_DYNAMON,dto.getNovoNomeTabelaLeituraDynamon() + "_"+idAlocacao));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_ID_CLP,usina.getIdCLP()));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_USINA_COLUNA_ID_USINA_SCHEMA_SINAI,idUsinaSchemaBase));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_USINA_COLUNA_ATIVADA,"true"));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_NOME_TOPICO_IOT,dto.getNovoNomeTopicoIoT() + "_"+idAlocacao));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_NOME_TOPICO_FIREBASE,dto.getNovoNomeTopicoFirebase() + "_"+idAlocacao));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_USINA_COLUNA_ID_ALOCACAO_SCHEMA_SINAI,idAlocacao.toString()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_NOME_RESPONSAVEL,nomeResponsavel));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_CONTATO_RESPONSAVEL,contatoResponsavel));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_USINA_COLUNA_ID_HOSPITAL,idHospital));



        String sql = SQLUtil.insert(schemaClienteNovo,NomeTabelaUtil.USINA,dados);
        System.out.println(sql);
        return  this.repository.inserirDadosComIdRetorno(sql);
    }


    private Long consultarIdClienteBaseSinai(String cnpjCliente) throws Exception{
        String condicao = " AND "+TABELA_CLIENTE_COLUNA_CNPJ +" = '"+ cnpjCliente + "'";
        String sql = SQLUtil.select(nomeSchemaSinai,CLIENTE,new SelectSQLUtil(),condicao);
        ResultSet rs = this.repository.consultarDados(sql);

        while(rs.next()) {
            idCliente = rs.getLong(TABELA_CLIENTE_COLUNA_ID);
        }
        return idCliente;
    }

    private void atualizarClienteUsinaBaseSinai(String idUsina,String cnpjCliente) throws Exception{
        String idCliente = this.consultarIdClienteBaseSinai(cnpjCliente).toString();
        String sql = "UPDATE "+nomeSchemaSinai + "." + USINA + " SET " +TABELA_USINA_SINAI_COLUNA_ID_CLIENTE +
                " = " +idCliente + " WHERE "+TABELA_USINA_SINAI_COLUNA_ID + " = "+idUsina;
        System.out.println(sql);
        this.repository.inseirDados(sql);
    }


    private Long inserirAlocacaoUsinaCliente(String idUsina) throws Exception{
        String schemaClienteNovo = "sinai";
        List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();
        //dados.add(new InsertSQLUtil(DATA,TABELA_ALOCACAO_USINA_CLIENTE_DATA,usina.getNome()));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_ALOCACAO_USINA_CLIENTE_ID_USINA,idUsina));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_ALOCACAO_USINA_CLIENTE_ID_CLIENTE,idCliente.toString()));

        String sql = SQLUtil.insert(schemaClienteNovo, ALOCACAO_USINA_CLIENTE,dados);
        System.out.println(sql);
        return this.repository.inserirDadosComIdRetorno(sql);
    }


}
