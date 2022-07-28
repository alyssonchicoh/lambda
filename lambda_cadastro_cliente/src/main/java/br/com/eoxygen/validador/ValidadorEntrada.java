package br.com.eoxygen.validador;

import br.com.eoxygen.dto.ClienteDTO;
import br.com.eoxygen.exceptions.CNPJClienteInvalidoException;
import br.com.eoxygen.exceptions.CNPJClienteJaExistente;
import br.com.eoxygen.exceptions.CampoObrigatorioException;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.util.SelectSQLUtil;
import java.sql.ResultSet;
import static br.com.eoxygen.util.NomeColunaUtil.TABELA_CLIENTE_COLUNA_CNPJ;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

public class ValidadorEntrada {

    public static void validarEntradas(GenericRepository repository, ClienteDTO clienteDTO) throws Exception{
        if(!clienteDTO.existeNome()){
            throw new CampoObrigatorioException("nome");
        }

        if(!clienteDTO.existeCnpj()) {
            throw new CampoObrigatorioException("CNPJ");
        }

        if(!clienteDTO.existeEndereco()) {
            throw new CampoObrigatorioException("Endereco");
        }

        if(!clienteDTO.existeIdCidade()) {
            throw new CampoObrigatorioException("idCidade");
        }

        if(!clienteDTO.existeEmail()) {
            throw new CampoObrigatorioException("Email");
        }

        if(!clienteDTO.getCnpj().matches("[0-9]+")){
            throw new CNPJClienteInvalidoException();
        }

        if(jaExisteClienteCadastradoComCNPJ(repository,clienteDTO.getCnpj())){
            throw new CNPJClienteJaExistente();
        }

    }

    private static boolean jaExisteClienteCadastradoComCNPJ(GenericRepository repository,String cnpj) throws Exception{
        String condicao = " AND "+ TABELA_CLIENTE_COLUNA_CNPJ + " = '"+cnpj+"' ";
        String sql = SQLUtil.select(NOME_SCHEMA_BASE,CLIENTE,new SelectSQLUtil(),condicao);
        ResultSet rs = repository.consultarDados(sql);
        return rs.next();
    }
}
