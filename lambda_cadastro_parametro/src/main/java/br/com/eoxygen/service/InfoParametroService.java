package br.com.eoxygen.service;

import br.com.eoxygen.dto.InfoParametroDTO;
import br.com.eoxygen.enuns.TipoParametroEnum;
import br.com.eoxygen.enuns.UnidadeMedidaEnum;
import br.com.eoxygen.exception.CNPJObrigatorioException;
import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.InsertSQLUtil;
import br.com.eoxygen.util.SQLUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import static br.com.eoxygen.enuns.TipoCampo.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;
import static br.com.eoxygen.util.NomeColunaUtil.*;

public class InfoParametroService {
    public final GenericRepository repository = new GenericRepository();
    private String nomeSchema = "cliente_";
    public InfoParametroDTO salvar(InfoParametroDTO infoParametroDTO) throws Exception{
        if(infoParametroDTO.getCnpjCliente() == null || infoParametroDTO.getCnpjCliente().equals("")){
            throw new CNPJObrigatorioException();
        }

        nomeSchema = nomeSchema.concat(infoParametroDTO.getCnpjCliente());

        List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();
        dados.add(new InsertSQLUtil(TEXTO,TABELA_INFO_PARAMETRO_COLUNA_NOME ,infoParametroDTO.getNome().getParametro()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_INFO_PARAMETRO_COLUNA_UNIDADE_MEDIDA,infoParametroDTO.getUnidadeMedida().getUnidadeMedida()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_INFO_PARAMETRO_COLUNA_LABEL,infoParametroDTO.getLabel()));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_INFO_PARAMETRO_COLUNA_LIMIAR_SUPERIOR,infoParametroDTO.getLimiarSuperior().toString()));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_INFO_PARAMETRO_COLUNA_LIMIAR_INFERIOR,infoParametroDTO.getLimiarInferior().toString()));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_INFO_PARAMETRO_COLUNA_PORCENTAGEM_APROXIMACAO_LIMIAR_SUPERIOR,infoParametroDTO.getPorcentagemAproximacaoLimiarSuperior().toString()));
        dados.add(new InsertSQLUtil(NUMERO,TABELA_INFO_PARAMETRO_COLUNA_PORCENTAGEM_APROXIMACAO_LIMIAR_INFERIOR,infoParametroDTO.getPorcentagemAproximacaoLimiarInferior().toString()));

        String sql = SQLUtil.insert(nomeSchema,PARAMETRO,dados);

        Long id = this.repository.inserirDadosComIdRetorno(sql);
        infoParametroDTO.setId(id);

        return infoParametroDTO;
    }

}
