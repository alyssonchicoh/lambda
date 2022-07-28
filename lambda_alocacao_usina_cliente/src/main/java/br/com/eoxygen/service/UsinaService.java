package br.com.eoxygen.service;
import br.com.eoxygen.dto.AlocacaoRetornoDTO;
import br.com.eoxygen.dto.AlocacaoUsinaDTO;
import br.com.eoxygen.model.UsinaSinai;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.InsertSQLUtil;
import br.com.eoxygen.util.NomeTabelaUtil;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static br.com.eoxygen.enuns.TipoCampo.*;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

public class UsinaService {

    private String sqlGeral = "";

    public final GenericRepository repository = new GenericRepository();
    private Long idCliente;

    public AlocacaoRetornoDTO alocar(AlocacaoUsinaDTO dto) throws Exception{
       String schemaClienteAntigo = "cliente_"+dto.getCnpjClienteAntigo();
       UsinaSinai usina = this.consultarUsinaBaseSinai(dto.getIdUsinaBaseSinai());

       if(!dto.getCnpjClienteAntigo().equals("")){
           this.desativarUsinaSchemaClienteAntigo(schemaClienteAntigo,usina.getId().toString());
       }

       this.atualizarClienteUsinaBaseSinai(dto.getIdUsinaBaseSinai(),dto.getCnpjClienteNovo());

       this.inserirAlocacaoUsinaCliente(usina.getId().toString());
       this.inserirUsinaSchemaClienteNovo(usina,usina.getId().toString(),dto,dto.getNomeResponsavel(),dto.getContatoResponsavel(),dto.getIdHospitalBaseCliente());

        AlocacaoRetornoDTO retorno = new AlocacaoRetornoDTO();
        retorno.setIdAlocacao(idAloacao);
        retorno.setIdUsinaBaseClienteNovo(idUsinaClienteNovo);
        retorno.setIdUsinaBaseSinai(usina.getId());

       return retorno;

    }

    private UsinaSinai consultarUsinaBaseSinai(String idUsina) throws Exception{
        String condicao = " AND "+TABELA_USINA_COLUNA_ID +" = "+ idUsina;
        String sql = SQLUtil.select(NOME_SCHEMA_BASE,USINA,new SelectSQLUtil(),condicao);
        ResultSet rs = this.repository.consultarDados(sql);
        UsinaSinai usina = null;
        if(rs.next()) {
            usina = new UsinaSinai();
            usina.setId(rs.getLong(TABELA_USINA_SINAI_COLUNA_ID));
            usina.setNome(rs.getString(TABELA_USINA_SINAI_COLUNA_NOME));
            usina.setFabricante(rs.getString(TABELA_USINA_COLUNA_FABRICANTE));
            usina.setNumeroSerie(rs.getString(TABELA_USINA_SINAI_COLUNA_NUMERO_SERIE));
            usina.setDataConstrucao(rs.getDate(TABELA_USINA_SINAI_COLUNA_DATA_CONSTRUCAO));
            usina.setDataCompra(rs.getDate(TABELA_USINA_SINAI_COLUNA_DATA_COMPRA));
            usina.setStatus(rs.getString(TABELA_USINA_SINAI_COLUNA_STATUS));
            usina.setIdCLP(rs.getString(TABELA_USINA_SINAI_COLUNA_ID_CLP));
            rs.close();
            return usina;
        }
        rs.close();
        return null;
    }

    private void desativarUsinaSchemaClienteAntigo(String schemaClienteAntigo,String idUsinaSchemaBase) throws Exception{
        String sql = "UPDATE "+schemaClienteAntigo + "." + USINA + " SET " +TABELA_USINA_COLUNA_ATIVADA +
                " = FALSE WHERE "+TABELA_USINA_COLUNA_ID_USINA_SCHEMA_SINAI + " = "+idUsinaSchemaBase +";";

        this.sqlGeral = sqlGeral + sql;
    }

    private Long inserirUsinaSchemaClienteNovo(UsinaSinai usina,String idUsinaSchemaBase, AlocacaoUsinaDTO dto,String nomeResponsavel,String contatoResponsavel,String idHospital) throws Exception{
        String idAlocacao = "";

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

        return  this.repository.inserirDadosComIdRetorno(sql);
    }


    private Long consultarIdClienteBaseSinai(String cnpjCliente) throws Exception{
        String condicao = " AND "+TABELA_CLIENTE_COLUNA_CNPJ +" = '"+ cnpjCliente + "'";
        String sql = SQLUtil.select(NOME_SCHEMA_BASE,CLIENTE,new SelectSQLUtil(),condicao);
        ResultSet rs = this.repository.consultarDados(sql);

        if(rs.next()) {
            idCliente = rs.getLong(TABELA_CLIENTE_COLUNA_ID);
        }

        rs.close();
        return idCliente;
    }

    private void atualizarClienteUsinaBaseSinai(String idUsina,String cnpjCliente) throws Exception{
        String idCliente = this.consultarIdClienteBaseSinai(cnpjCliente).toString();
        String sql = "UPDATE "+NOME_SCHEMA_BASE + "." + USINA + " SET " +TABELA_USINA_SINAI_COLUNA_ID_CLIENTE +
                " = " +idCliente + " WHERE "+TABELA_USINA_SINAI_COLUNA_ID + " = "+idUsina + ";";
        this.sqlGeral = sqlGeral + sql;
    }


    private void inserirAlocacaoUsinaCliente(String idUsina) throws Exception{
        List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();
        dados.add(new InsertSQLUtil(NUMERO,TABELA_ALOCACAO_USINA_CLIENTE_ID_USINA,idUsina));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_ALOCACAO_USINA_CLIENTE_ID_CLIENTE,idCliente.toString()));

        String sql = SQLUtil.insert(NOME_SCHEMA_BASE, ALOCACAO_USINA_CLIENTE,dados);

        this.sqlGeral = sqlGeral + sql;

        "SELECT ID FROM SINAI.ALOCACAO_USINA_CLIENTE "
    }
    private void consultarIdAlocacao(){
        StringBuilder sql = n;

    }


}
