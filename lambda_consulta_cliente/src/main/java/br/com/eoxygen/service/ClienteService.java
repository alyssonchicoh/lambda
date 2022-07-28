package br.com.eoxygen.service;

import br.com.eoxygen.dto.ClienteDTO;
import br.com.eoxygen.repository.GenericRepository;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

public class ClienteService {
    public final GenericRepository repository = new GenericRepository();

    public List<ClienteDTO> consultar(String cnpj) throws Exception{
        String sql = this.montarJoin(cnpj);
        ResultSet rs = this.repository.consultarDados(sql);

        return montarResultSet(rs);
    }

    private String montarJoin(String cnpj){
        String condicao = cnpj.equals("0") ? "" : " AND "+TABELA_CLIENTE_COLUNA_CNPJ + "='"+cnpj+"'";

        String sql = "";
        sql = sql.concat("SELECT * FROM sinai."+ CLIENTE + " CLI INNER JOIN sinai."+CIDADE +" CID");
        sql = sql.concat(" ON CLI."+TABELA_CLIENTE_COLUNA_ID_CIDADE + " = CID."+TABELA_CIDADE_COLUNA_ID);
        sql = sql.concat(" INNER JOIN sinai."+ESTADO + " EST ON CID."+TABELA_CIDADE_COLUNA_ID_ESTADO + "= EST."+TABELA_ESTADO_COLUNA_ID);
        sql = sql.concat(" WHERE CLI.DELETADO IS FALSE "+condicao);

        return sql;
    }

    private List<ClienteDTO> montarResultSet(ResultSet rs) throws Exception{
        List<ClienteDTO> lista = new ArrayList<ClienteDTO>();

        while(rs.next()){
            ClienteDTO cliente = new ClienteDTO();
            cliente.setId(rs.getLong(1));
            cliente.setNome(rs.getString(2));
            cliente.setCnpj(rs.getString(3));
            cliente.setEndereco(rs.getString(4));
            cliente.setCidade(rs.getString( 8));
            cliente.setEstado(rs.getString(11));

            lista.add(cliente);
        }

        return lista;
    }

}
