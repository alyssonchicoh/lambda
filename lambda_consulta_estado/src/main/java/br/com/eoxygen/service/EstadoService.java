package br.com.eoxygen.service;

import br.com.eoxygen.dto.EstadoDTO;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

public class EstadoService {
    public final GenericRepository repository = new GenericRepository();;
    private String nomeSchema = "cliente_";;
    public List<EstadoDTO> consultar(String cnpjCliente, String id) throws Exception{
        this.definirSchema(cnpjCliente);

        String sql = SQLUtil.select(nomeSchema,ESTADO,new SelectSQLUtil(),this.definirCondicao(id));
        ResultSet rs = this.repository.consultarDados(sql);

        return montarResultSet(rs);
    }

    private List<EstadoDTO> montarResultSet(ResultSet rs) throws Exception{
        List<EstadoDTO> objetos = new ArrayList<EstadoDTO>();

        while(rs.next()){
            EstadoDTO dto = new EstadoDTO();

            dto.setId(rs.getLong(TABELA_ESTADO_COLUNA_ID));
            dto.setNome(rs.getString(TABELA_ESTADO_COLUNA_NOME));
            dto.setUf(rs.getString(TABELA_ESTADO_COLUNA_UF));

            objetos.add(dto);
        }

        return objetos;
    }

    private String definirCondicao(String id){
        return id != "" ? " AND "+TABELA_ESTADO_COLUNA_ID +" = " + id : "";
    }

    private void definirSchema(String cnpjCliente){
        this.nomeSchema = cnpjCliente.equals("0") ? NOME_SCHEMA_BASE :  nomeSchema + cnpjCliente;
    }

}
