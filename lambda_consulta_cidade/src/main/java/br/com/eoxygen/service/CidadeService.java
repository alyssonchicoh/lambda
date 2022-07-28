package br.com.eoxygen.service;

import br.com.eoxygen.dto.CidadeDTO;
import br.com.eoxygen.dto.EstadoDTO;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

public class CidadeService {

    private String nomeSchema = "cliente_";;
    public final GenericRepository repository = new GenericRepository();;

    public List<CidadeDTO> consultar(String cnpjCliente, String id,String idEstado) throws Exception{
        this.definirSchema(cnpjCliente);
        String condicao = this.definirCondicaoConsultaCidade(id,idEstado);

        ResultSet rs = this.repository.consultarDados(this.construirSQL(condicao));

        return montarResultSet(rs);
    }

    private String construirSQL(String condicao){
        String sql = "SELECT * FROM #TABELA_A# TA INNER JOIN #TABELA_B# TB ON " +
                "(TA."+TABELA_CIDADE_COLUNA_ID_ESTADO + "=" +
                "TB."+TABELA_ESTADO_COLUNA_ID + ")" +
                " WHERE TA.DELETADO IS FALSE "+condicao;

        sql = sql.replace("#TABELA_A#",nomeSchema+ "."+CIDADE);
        sql = sql.replace("#TABELA_B#",nomeSchema+ "."+ESTADO);
        System.out.println(sql);
        return  sql;
    }

    private void definirSchema(String cnpjCliente){
        this.nomeSchema = cnpjCliente.equals("0")
                ? NOME_SCHEMA_BASE
                : nomeSchema + cnpjCliente;
    }

    private List<CidadeDTO> montarResultSet(ResultSet rs) throws Exception{
        List<CidadeDTO> objetos = new ArrayList<CidadeDTO>();

        while(rs.next()){
            CidadeDTO cidadeDTO = new CidadeDTO();
            EstadoDTO estadoDTO = new EstadoDTO();

            cidadeDTO.setId(rs.getLong(1));
            cidadeDTO.setNome(rs.getString(2));
            estadoDTO.setNome(rs.getString(6));
            estadoDTO.setUf(rs.getString(7));
            cidadeDTO.setEstado(estadoDTO);

            objetos.add(cidadeDTO);
        }

        return objetos;
    }

    private String definirCondicaoConsultaCidade(String id,String idEstado){
        String condicao = "";

        if(id != null && !id.equals("")){
            condicao = condicao + " AND TA."+TABELA_CIDADE_COLUNA_ID +" = " + id;
        }

        if(idEstado != null && !idEstado.equals("")){
            condicao = condicao + " AND TA."+TABELA_CIDADE_COLUNA_ID_ESTADO +" = "+idEstado;
        }

        return condicao;
    }

}
