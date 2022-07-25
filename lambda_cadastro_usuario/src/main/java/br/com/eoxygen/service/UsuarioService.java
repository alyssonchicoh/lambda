package br.com.eoxygen.service;

import br.com.eoxygen.dto.UsuarioDTO;
import br.com.eoxygen.exception.CPFJaExistenteException;
import br.com.eoxygen.exception.CampoObrigatorioException;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.InsertSQLUtil;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;
import static br.com.eoxygen.enuns.TipoCampo.*;

public class UsuarioService {
    public final GenericRepository repository = new GenericRepository();

    private String nomeSchema = "cliente_";

    public Long salvar(UsuarioDTO usuario) throws Exception{
        validarCamposObrigatorios(usuario);
        this.definirSchema(usuario.getCnpjCliente());
        this.cpfJaExistente(usuario.getCpf());

        List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USUARIO_COLUNA_NOME,usuario.getNome()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USUARIO_COLUNA_CPF,usuario.getCpf()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USUARIO_COLUNA_EMAIL,usuario.getEmail()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USUARIO_COLUNA_TIPO,usuario.getTipo()));

        String sql = SQLUtil.insert(nomeSchema,USUARIO,dados);
        return this.repository.inserirDadosComIdRetorno(sql);
    }


    private void cpfJaExistente(String cpf) throws Exception,CPFJaExistenteException {
        String condicao = " AND "+TABELA_USUARIO_COLUNA_CPF + "= '"+cpf + "'";
        String sql = SQLUtil.select(nomeSchema,USUARIO,new SelectSQLUtil(),condicao);
        ResultSet rs = this.repository.consultarDados(sql);

        if(rs.next()){
            rs.close();
            throw new CPFJaExistenteException();
        }

        rs.close();
    }

    private void validarCamposObrigatorios(UsuarioDTO usuarioDTO) throws CampoObrigatorioException {
        if(isNullOrEmpty(usuarioDTO.getCpf())){
            throw new CampoObrigatorioException("CPF");
        }

        if(isNullOrEmpty(usuarioDTO.getNome())){
            throw new CampoObrigatorioException("Nome");
        }

        if(isNullOrEmpty(usuarioDTO.getEmail())){
            throw new CampoObrigatorioException("Email");
        }

        if(isNullOrEmpty(usuarioDTO.getTipo())){
            throw new CampoObrigatorioException("Tipo");
        }

        if(isNullOrEmpty(usuarioDTO.getCnpjCliente())){
            throw new CampoObrigatorioException("CNPJ Cliente");
        }
    }

    private void definirSchema(String cnpjCliente){
        nomeSchema = cnpjCliente.equals("0") ? NOME_SCHEMA_BASE : nomeSchema.concat(cnpjCliente);
    }

    private boolean isNullOrEmpty(String campo){
        return campo == null || campo.equals("");
    }
}
