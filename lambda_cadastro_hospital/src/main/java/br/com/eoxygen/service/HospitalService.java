package br.com.eoxygen.service;

import br.com.eoxygen.dto.HospitalDTO;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.InsertSQLUtil;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;
import static br.com.eoxygen.enuns.TipoCampo.*;
import br.com.eoxygen.util.SQLUtil;
import java.util.ArrayList;
import java.util.List;
public class HospitalService {
    public final GenericRepository repository = new GenericRepository();
    private String nomeSchema = "cliente_";
    public Long salvar(HospitalDTO hospital) throws Exception{
        nomeSchema = nomeSchema.concat(hospital.getCnpjCliente());

        List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();
        dados.add(new InsertSQLUtil(TEXTO,TABELA_HOSPITAL_COLUNA_NOME,hospital.getNome()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_HOSPITAL_COLUNA_ENDERECO,hospital.getEndereco()));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_HOSPITAL_COLUNA_ID_CIDADE,hospital.getIdCidade().toString()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_HOSPITAL_COLUNA_NOME_RESPONSAVEL,hospital.getNomeResponsavel()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_HOSPITAL_COLUNA_TELEFONE_RESPONSAVEL,hospital.getTelefoneResponsavel()));

        String sql = SQLUtil.insert(nomeSchema,HOSPITAL,dados);

        return this.repository.inserirDadosComIdRetorno(sql);
    }
}
