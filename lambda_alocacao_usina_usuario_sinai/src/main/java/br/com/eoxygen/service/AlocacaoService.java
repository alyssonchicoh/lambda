package br.com.eoxygen.service;

import br.com.eoxygen.dto.AlocacaoDTO;
import br.com.eoxygen.dto.UsinaDTO;
import br.com.eoxygen.model.Usuario;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.InsertSQLUtil;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static br.com.eoxygen.enuns.TipoCampo.NUMERO;
import static br.com.eoxygen.enuns.TipoCampo.TEXTO;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

public class AlocacaoService {

    public final GenericRepository repository = new GenericRepository();;
    private String schemaCliente = "cliente_";
    private String sqlGeral = "";

    public void alocar(AlocacaoDTO alocacaoDTO) throws Exception {
        this.schemaCliente = this.schemaCliente.concat(alocacaoDTO.getCnpjClienteDestino());
        if (alocacaoDTO.getOperacao().equals(1)) {
            this.efetuarAlocacao(alocacaoDTO);
        }
        else if (alocacaoDTO.getOperacao().equals(2)) {
            this.efetuarDesalocacao(alocacaoDTO);
        }
    }

    private void efetuarAlocacao(AlocacaoDTO alocacaoDTO) throws Exception {
        final Usuario usuario = this.buscarUsuarioBaseSinai(alocacaoDTO.getIdUsuarioBaseSinai());
        if (usuario != null) {
            final Long idUsuario = this.inserirUsuarioBaseCliente(usuario);
            for(UsinaDTO usina: alocacaoDTO.getUsinas()){
                this.alocarUsuarioUsinaBaseCliente(idUsuario.toString(), usina.getIdUsinaBaseCliente());
                this.alocarUsuarioUsinaBaseSinai(alocacaoDTO.getIdUsuarioBaseSinai(), usina.getIdUsinaBaseSinai(), alocacaoDTO.getIdClienteBaseSinai());
            }
        }
    }

    private void efetuarDesalocacao(AlocacaoDTO alocacaoDTO) throws Exception {
        final Usuario usuario = this.buscarUsuarioBaseSinai(alocacaoDTO.getIdUsuarioBaseSinai());
        if (usuario != null) {
            final Long idUsuario = this.buscarUsuarioBaseCliente(usuario.getCpf());
            for(UsinaDTO usina: alocacaoDTO.getUsinas()) {
                this.desalocarUsuarioUsina(idUsuario.toString(), usina.getIdUsinaBaseCliente());
                this.desalocarUsuarioUsinaSinai(alocacaoDTO.getIdUsuarioBaseSinai(), usina.getIdUsinaBaseSinai(), alocacaoDTO.getIdClienteBaseSinai());

            }
        }
    }




    private Usuario buscarUsuarioBaseSinai(String id) throws Exception{
        String condicao = " AND "+ TABELA_USUARIO_COLUNA_ID +" = "+id;
        String sql = SQLUtil.select(NOME_SCHEMA_BASE,USUARIO,new SelectSQLUtil(),condicao);
        ResultSet rs = this.repository.consultarDados(sql);

        while(rs.next()){
            Usuario usuario = new Usuario();
            usuario.setCpf(rs.getString(TABELA_USUARIO_COLUNA_CPF));
            usuario.setNome(rs.getString(TABELA_USUARIO_COLUNA_NOME));
            return usuario;
        }

        return null;
    }

    private Long buscarUsuarioBaseCliente(String cpfCliente) throws Exception{
        String condicao = " AND "+ TABELA_USUARIO_COLUNA_CPF +" = '"+cpfCliente+"'";
        String sql = SQLUtil.select(schemaCliente,USUARIO,new SelectSQLUtil(),condicao);
        ResultSet rs = this.repository.consultarDados(sql);

        while(rs.next()){
            return rs.getLong(TABELA_USUARIO_COLUNA_ID);
        }

        return 0L;
    }

    private void inserirUsuarioBaseCliente(Usuario usuario) throws Exception{
        Long idCliente = this.buscarUsuarioBaseCliente(usuario.getCpf());

        if(idCliente.equals(0L)){
            List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();
            dados.add(new InsertSQLUtil(TEXTO,TABELA_USUARIO_COLUNA_NOME,usuario.getNome()));
            dados.add(new InsertSQLUtil(TEXTO,TABELA_USUARIO_COLUNA_CPF,usuario.getCpf()));
            dados.add(new InsertSQLUtil(TEXTO,TABELA_USUARIO_COLUNA_TIPO,"USUARIO_SINAI"));
            String sql = SQLUtil.insert(schemaCliente,USUARIO,dados);
            this.sqlGeral = sqlGeral.concat(sql);
        }

    }

    private void alocarUsuarioUsinaBaseCliente(String idUsuarioBaseCliente,String idUsinaBaseCliente) throws Exception{
        List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();
        dados.add(new InsertSQLUtil(NUMERO,TABELA_USUARIO_USINA_COLUNA_ID_USUARIO,idUsuarioBaseCliente.toString()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USUARIO_USINA_COLUNA_ID_USINA,idUsinaBaseCliente.toString()));
        String sql = SQLUtil.insert(schemaCliente,USUARIO_USINA,dados);
        this.sqlGeral = sqlGeral.concat(sql);
    }

    private void alocarUsuarioUsinaBaseSinai(String idUsuario,String idUsina,String idCliente) throws Exception{
        List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();

        dados.add(new InsertSQLUtil(NUMERO, TABELA_USUARIO_USINA_SINAI_ID_USUARIO,idUsuario));
        dados.add(new InsertSQLUtil(NUMERO, TABELA_USUARIO_USINA_SINAI_ID_USINA,idUsina));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_USUARIO_USINA_SINAI_ID_CLIENTE,idCliente));

        String sql = SQLUtil.insert(NOME_SCHEMA_BASE,USUARIO_USINA,dados);
        this.sqlGeral = sqlGeral.concat(sql);
    }

    private void desalocarUsuarioUsina(String idUsuario,String idUsina) throws Exception{
        String condicao = " WHERE "+TABELA_USUARIO_USINA_COLUNA_ID_USUARIO + "=" +idUsuario +" AND "
                +TABELA_USUARIO_USINA_COLUNA_ID_USINA + "="+idUsina;

        String sql = "DELETE FROM "+schemaCliente + "."+USUARIO_USINA + condicao;
        this.sqlGeral = sqlGeral.concat(sql);

    }

    private void desalocarUsuarioUsinaSinai(String idUsuario,String idUsina,String idCliente) throws Exception{
        String condicao = " WHERE "
                +TABELA_USUARIO_USINA_SINAI_ID_USINA + "=" +idUsina +" AND "
                +TABELA_USUARIO_USINA_SINAI_ID_CLIENTE + "="+idCliente + " AND "
                +TABELA_USUARIO_USINA_SINAI_ID_USUARIO+ "="+idUsuario + "";

        String sql = "DELETE FROM "+ NOME_SCHEMA_BASE + "."+USUARIO_USINA + condicao;
        this.sqlGeral = sqlGeral.concat(sql);
    }
}
