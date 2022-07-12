package br.com.eoxygen.service;

import br.com.eoxygen.dto.HospitalDTO;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

public class HospitalService {
    public final GenericRepository repository = new GenericRepository();;

    private String nomeSchema = "cliente_";;
    public List<HospitalDTO> consultar(String cnpjCliente, String id) throws Exception{
        this.definirSchema(cnpjCliente);

        String sql = SQLUtil.select(nomeSchema,HOSPITAL,new SelectSQLUtil(),this.definirCondicao(id));
        ResultSet rs = this.repository.consultarDados(sql);

        return montarResultSet(rs);
    }

    private List<HospitalDTO> montarResultSet(ResultSet rs) throws Exception{
        List<HospitalDTO> listaHospital = new ArrayList<HospitalDTO>();

        while(rs.next()){
            HospitalDTO hospital = new HospitalDTO();

            hospital.setId(rs.getLong(TABELA_HOSPITAL_COLUNA_ID));
            hospital.setNome(rs.getString(TABELA_HOSPITAL_COLUNA_NOME));
            hospital.setEndereco(rs.getString(TABELA_HOSPITAL_COLUNA_ENDERECO));
            hospital.setIdCidade(rs.getLong(TABELA_HOSPITAL_COLUNA_ID_CIDADE));
            hospital.setNomeResponsavel(rs.getString(TABELA_HOSPITAL_COLUNA_NOME_RESPONSAVEL));
            hospital.setTelefoneResponsavel(rs.getString(TABELA_HOSPITAL_COLUNA_TELEFONE_RESPONSAVEL));

            listaHospital.add(hospital);
        }

        rs.close();

        return listaHospital;
    }

    private String definirCondicao(String id){
        return id.equals("") ? "": " AND "+TABELA_HOSPITAL_COLUNA_ID +" = " + id;
    }

    private void definirSchema(String cnpjCliente){
        this.nomeSchema = nomeSchema + cnpjCliente;

    }

}
