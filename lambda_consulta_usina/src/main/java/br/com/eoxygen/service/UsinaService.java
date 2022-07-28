package br.com.eoxygen.service;

import br.com.eoxygen.dto.ClienteDTO;
import br.com.eoxygen.dto.HospitalDTO;
import br.com.eoxygen.dto.UsinaDTO;
import br.com.eoxygen.dto.UsinaSinaiDTO;
import br.com.eoxygen.model.UsinaSinai;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;

import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsinaService {

    private String nomeSchema = "cliente_";;
    public GenericRepository repository = new GenericRepository();;

    public List<UsinaDTO> consultar(String cnpjCliente, Long id) throws Exception{
        this.nomeSchema = nomeSchema + cnpjCliente;
        String condicao = this.definirCondicao(id);

        String sql = SQLUtil.select(nomeSchema,USINA,new SelectSQLUtil(),condicao);
        ResultSet rs = this.repository.consultarDados(sql);

        return montarResultSet(rs);
    }

    public List<UsinaSinaiDTO> consultarUsinasSinai(Long id) throws Exception{
        this.nomeSchema = NOME_SCHEMA_BASE;
        String condicao = this.definirCondicao(id);

        String sql = SQLUtil.select(nomeSchema,USINA,new SelectSQLUtil(),condicao);
        ResultSet rs = this.repository.consultarDados(sql);

        return montarResultSetUsinaSinai(rs);
    }

    private List<UsinaDTO> montarResultSet(ResultSet rs) throws Exception{
        List<UsinaDTO> objetos = new ArrayList<UsinaDTO>();

        while(rs.next()){
            UsinaDTO dto = new UsinaDTO();
            dto.setId(rs.getLong(TABELA_USINA_COLUNA_ID));
            dto.setNome(rs.getString(TABELA_USINA_COLUNA_NOME));
            dto.setNumeroSerie(rs.getString(TABELA_USINA_COLUNA_NUMERO_SERIE));
            dto.setFabricante(rs.getString(TABELA_USINA_COLUNA_FABRICANTE));
            dto.setDataCompra(rs.getDate(TABELA_USINA_COLUNA_DATA_COMPRA));
            dto.setHospital(this.consultarHospital(rs.getLong(TABELA_USINA_COLUNA_ID_HOSPITAL)));
            dto.setIdAlocacao(rs.getLong(TABELA_USINA_COLUNA_ID_ALOCACAO_SCHEMA_SINAI));
            dto.setIdUsinaBaseSinai(rs.getLong(TABELA_USINA_COLUNA_ID_USINA_SCHEMA_SINAI));

            objetos.add(dto);
        }
        rs.close();

        return objetos;
    }

    private List<UsinaSinaiDTO> montarResultSetUsinaSinai(ResultSet rs) throws Exception{
        List<UsinaSinaiDTO> objetos = new ArrayList<UsinaSinaiDTO>();

        while(rs.next()){
            UsinaSinaiDTO usina = new UsinaSinaiDTO();
            usina.setId(rs.getLong(TABELA_USINA_SINAI_COLUNA_ID));
            usina.setNome(rs.getString(TABELA_USINA_SINAI_COLUNA_NOME));
            usina.setFabricante(rs.getString(TABELA_USINA_COLUNA_FABRICANTE));
            usina.setNumeroSerie(rs.getString(TABELA_USINA_SINAI_COLUNA_NUMERO_SERIE));
            usina.setDataConstrucao(rs.getDate(TABELA_USINA_SINAI_COLUNA_DATA_CONSTRUCAO));
            usina.setDataCompra(rs.getDate(TABELA_USINA_SINAI_COLUNA_DATA_COMPRA));
            usina.setStatus(rs.getString(TABELA_USINA_SINAI_COLUNA_STATUS));
            usina.setIdCLP(rs.getString(TABELA_USINA_SINAI_COLUNA_ID_CLP));
            usina.setCliente(this.consultarCliente(rs.getLong(TABELA_USINA_SINAI_COLUNA_ID_CLIENTE)));

            objetos.add(usina);
        }

        rs.close();

        return objetos;
    }

    private HospitalDTO consultarHospital(Long id) throws Exception{
        String condicao = " AND "+TABELA_HOSPITAL_COLUNA_ID + " = "+id;

        SelectSQLUtil colunas = new SelectSQLUtil();
        colunas.adicionaValor(TABELA_HOSPITAL_COLUNA_NOME);

        String sql = SQLUtil.select(nomeSchema,HOSPITAL,colunas,condicao);
        ResultSet rs = this.repository.consultarDados(sql);

        if(rs.next()){
            HospitalDTO dto = new HospitalDTO();
            dto.setId(id);
            dto.setNome(rs.getString(TABELA_HOSPITAL_COLUNA_NOME));
            rs.close();
            return dto;
        }
        rs.close();

        return null;
    }

    private ClienteDTO consultarCliente(Long id) throws Exception{
        String condicao = " AND "+TABELA_CLIENTE_COLUNA_ID + " = "+id;

        SelectSQLUtil colunas = new SelectSQLUtil();
        colunas.adicionaValor(TABELA_CLIENTE_COLUNA_NOME);

        String sql = SQLUtil.select(NOME_SCHEMA_BASE,CLIENTE,colunas,condicao);
        ResultSet rs = this.repository.consultarDados(sql);

        if(rs.next()){
            ClienteDTO dto = new ClienteDTO();
            dto.setId(id);
            dto.setNome(rs.getString(TABELA_CLIENTE_COLUNA_NOME));
            rs.close();

            return dto;
        }

        rs.close();

        return null;
    }
    private String definirCondicao(Long id){
        return id != null ? " AND "+TABELA_USINA_COLUNA_ID +" = " + id : "";
    }

}
