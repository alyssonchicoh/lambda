package br.com.eoxygen.service;

import br.com.eoxygen.dto.ParametroDTO;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

public class ParametroService {
    private String nomeSchema = "cliente_";
    public final GenericRepository repository = new GenericRepository();

    public List<ParametroDTO> consultar(String cnpjCliente, String id, String idUsina) throws Exception{
        this.nomeSchema = nomeSchema + cnpjCliente;

        String sql = SQLUtil.select(nomeSchema,PARAMETRO,new SelectSQLUtil(),this.definirCondicao(id,idUsina));
        ResultSet rs = this.repository.consultarDados(sql);

        return montarResultSet(rs);
    }

    private List<ParametroDTO> montarResultSet(ResultSet rs) throws Exception{
        List<ParametroDTO> objetos = new ArrayList<ParametroDTO>();

        while(rs.next()){
            ParametroDTO dto = new ParametroDTO();
            dto.setId(rs.getLong(TABELA_INFO_PARAMETRO_COLUNA_ID));
            dto.setNome(rs.getString(TABELA_INFO_PARAMETRO_COLUNA_NOME));
            dto.setUnidadeMedida(rs.getString(TABELA_INFO_PARAMETRO_COLUNA_UNIDADE_MEDIDA));
            dto.setLimiarSuperior(rs.getDouble(TABELA_INFO_PARAMETRO_COLUNA_LIMIAR_SUPERIOR));
            dto.setLimiarInferior(rs.getDouble(TABELA_INFO_PARAMETRO_COLUNA_LIMIAR_INFERIOR));
            dto.setPorcentagemAproximacaoLimiarSuperior(rs.getDouble(TABELA_INFO_PARAMETRO_COLUNA_PORCENTAGEM_APROXIMACAO_LIMIAR_SUPERIOR));
            dto.setPorcentagemAproximacaoLimiarInferior(rs.getDouble(TABELA_INFO_PARAMETRO_COLUNA_PORCENTAGEM_APROXIMACAO_LIMIAR_INFERIOR));
            dto.setIdUsina(rs.getLong(TABELA_INFO_PARAMETRO_COLUNA_ID_USINA));
            dto.setLabel(rs.getString(TABELA_INFO_PARAMETRO_COLUNA_LABEL));
            objetos.add(dto);
        }

        rs.close();
        return objetos;
    }

    private String definirCondicao(String id,String idUsina){
        String condicao = "";

        condicao = condicao.concat(id.equals("") ? "" : " AND "+TABELA_INFO_PARAMETRO_COLUNA_ID +" = " + id);
        condicao = condicao.concat(idUsina.equals("") ? "" : " AND "+TABELA_INFO_PARAMETRO_COLUNA_ID_USINA +" = " + idUsina);

        return condicao;
    }
}
