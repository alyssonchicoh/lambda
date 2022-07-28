package br.com.eoxygen.service;

import br.com.eoxygen.dto.ClienteDTO;
import br.com.eoxygen.enuns.TipoCampo;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.InsertSQLUtil;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.validador.ValidadorEntrada;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

public class ClienteService {
    public final GenericRepository repository = new GenericRepository();;
    private String nomeSchemaCliente = "cliente_";

    public int criarCliente(ClienteDTO cliente) throws Exception {
        ValidadorEntrada.validarEntradas(repository,cliente);

        nomeSchemaCliente = nomeSchemaCliente.concat(cliente.getCnpj());
        String sql = "";
        sql = sql + this.criarRegistroTabelaCliente(cliente);
        sql = sql + this.criarSchema();
        sql = sql + this.criarTabelasSchema();
        return this.efetuarOperacaoBancoDados(sql);
    }

    private int efetuarOperacaoBancoDados(String sql) throws Exception{
        return repository.inseirDados(sql);
    }

    private String criarRegistroTabelaCliente(ClienteDTO cliente){
        List<InsertSQLUtil> campos = new ArrayList<>();

        campos.add(new InsertSQLUtil(TipoCampo.TEXTO,TABELA_CLIENTE_COLUNA_NOME,cliente.getNome()));
        campos.add(new InsertSQLUtil(TipoCampo.TEXTO,TABELA_CLIENTE_COLUNA_CNPJ,cliente.getCnpj()));
        campos.add(new InsertSQLUtil(TipoCampo.TEXTO,TABELA_CLIENTE_COLUNA_ENDERECO,cliente.getEndereco()));
        campos.add(new InsertSQLUtil(TipoCampo.TEXTO,TABELA_CLIENTE_COLUNA_EMAIL,cliente.getEmail()));
        campos.add(new InsertSQLUtil(TipoCampo.NUMERO,TABELA_CLIENTE_COLUNA_ID_CIDADE,cliente.getIdCidade()));

        return SQLUtil.insert(NOME_SCHEMA_BASE,CLIENTE,campos);
    }

    private String criarSchema(){
        return "CREATE SCHEMA "+nomeSchemaCliente + ";";
    }

    private String criarTabelasSchema() throws Exception{
        URL resource = getClass().getClassLoader().getResource("script_create_bd.txt");
        Scanner in = new Scanner(new FileReader(resource.getPath()));
        String sql = "";

        while (in.hasNextLine()) {
           sql = sql.concat(in.nextLine());
        }

        sql = sql.replace("#SCHEMA#",nomeSchemaCliente);

        return sql;
    }
}
