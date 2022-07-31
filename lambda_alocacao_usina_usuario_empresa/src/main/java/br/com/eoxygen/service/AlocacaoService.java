package br.com.eoxygen.service;

import br.com.eoxygen.dto.AlocacaoDTO;
import br.com.eoxygen.exception.OperacaoNaoPermitidaException;
import br.com.eoxygen.exception.UsinaUsuarioJaAlocadoException;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.InsertSQLUtil;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static br.com.eoxygen.enuns.TipoCampo.NUMERO;
import static br.com.eoxygen.util.NomeTabelaUtil.*;
import static br.com.eoxygen.util.NomeColunaUtil.*;

public class AlocacaoService {
    public final GenericRepository repository = new GenericRepository();
    private String schemaCliente = "cliente_";

    public void alocar(AlocacaoDTO alocacaoDTO) throws Exception{
        this.schemaCliente = this.schemaCliente.concat(alocacaoDTO.getCnpjCliente());

        if(alocacaoDTO.getOperacao() == null || alocacaoDTO.getOperacao() < 1 || alocacaoDTO.getOperacao() >2 ){
            throw new OperacaoNaoPermitidaException();
        }

        if(jaExisteAlocacaoUsuarioUsina(alocacaoDTO.getIdUsuario(),alocacaoDTO.getIdUsina())){
            throw new UsinaUsuarioJaAlocadoException();
        }

        if(alocacaoDTO.getOperacao().equals(1)){
            this.alocarUsuario(alocacaoDTO);
        }else if(alocacaoDTO.getOperacao().equals(2)){
            for(String idUsina: alocacaoDTO.getIdUsina()) {
                this.desalocarUsuarioUsina(alocacaoDTO.getIdUsuario(), idUsina);
            }
        }
    }
    private void alocarUsuario(AlocacaoDTO alocacaoDTO) throws Exception{
        String sqlGeral = "";
        for(String idUsina: alocacaoDTO.getIdUsina()){
            List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();
            dados.add(new InsertSQLUtil(NUMERO,TABELA_USUARIO_USINA_COLUNA_ID_USINA,idUsina));
            dados.add(new InsertSQLUtil(NUMERO,TABELA_USUARIO_USINA_COLUNA_ID_USUARIO,alocacaoDTO.getIdUsuario()));

            String sql = SQLUtil.insert(schemaCliente,USUARIO_USINA,dados);
            sqlGeral = sqlGeral.concat(sql);
        }
        this.repository.inseirDados(sqlGeral);
    }

    private void desalocarUsuarioUsina(String idUsuario,String idUsina) throws Exception{
        String condicao = " WHERE "+TABELA_USUARIO_USINA_COLUNA_ID_USUARIO + "=" +idUsuario +" AND "
                +TABELA_USUARIO_USINA_COLUNA_ID_USINA + "="+idUsina;

        String sql = "DELETE FROM "+schemaCliente + "."+USUARIO_USINA + condicao;

        this.repository.inseirDados(sql);
    }

    public boolean jaExisteAlocacaoUsuarioUsina(String idUsuario,List<String> idUsina) throws Exception{
        String condicao = "AND "+TABELA_USUARIO_USINA_COLUNA_ID_USUARIO + " = "+idUsuario;
        condicao = condicao + " AND "+TABELA_USUARIO_USINA_COLUNA_ID_USINA +" IN (#IDUSINA#)";
        String ids = "";
        for(int i = 0; i<idUsina.size(); i++){
            ids = ids.concat(idUsina.get(i));

            if(idUsina.size()-1 != i){
                ids = ids.concat(",");
            }
        }

        condicao = condicao.replace("#IDUSINA#",ids);

        String sql = SQLUtil.select(schemaCliente,USUARIO_USINA,new SelectSQLUtil(),condicao);
        ResultSet rs = this.repository.consultarDados(sql);

        if(rs.next()){
            rs.close();
            return true;
        }

        rs.close();
        return false;
    }
}
