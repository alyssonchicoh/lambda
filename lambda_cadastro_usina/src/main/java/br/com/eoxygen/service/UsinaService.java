package br.com.eoxygen.service;

import br.com.eoxygen.repository.GenericRepository;
import br.com.eoxygen.util.InsertSQLUtil;
import br.com.eoxygen.util.SQLUtil;
import br.com.eoxygen.dto.UsinaDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import static br.com.eoxygen.enuns.TipoCampo.*;
import static br.com.eoxygen.util.NomeColunaUtil.*;
import static br.com.eoxygen.util.NomeTabelaUtil.*;

public class UsinaService {
    public GenericRepository repository = new GenericRepository();;

    public Long salvar(UsinaDTO usina) throws Exception{
        SimpleDateFormat formatadorData = new SimpleDateFormat("dd-MM-yyyy");

        List<InsertSQLUtil> dados = new ArrayList<InsertSQLUtil>();
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_NOME,usina.getNome()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_NUMERO_SERIE,usina.getNumeroSerie()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_FABRICANTE,usina.getFabricante()));
        dados.add(new InsertSQLUtil(TEXTO,TABELA_USINA_COLUNA_ID_CLP,usina.getIdCLP()));
        dados.add(new InsertSQLUtil(DATA,TABELA_USINA_COLUNA_DATA_CONSTRUCAO,usina.getDataConstrucao()));
        dados.add(new InsertSQLUtil(DATA,TABELA_USINA_COLUNA_DATA_COMPRA,usina.getDataCompra()));

       String sql = SQLUtil.insert(NOME_SCHEMA_BASE,USINA,dados);

       return this.repository.inserirDadosComIdRetorno(sql);
    }

    public static void main(String[] args) throws Exception {
        UsinaService service = new UsinaService();
        UsinaDTO dto = new UsinaDTO();
        dto.setDataCompra("27-07-2022");
        dto.setDataConstrucao("27-07-2022");
        dto.setNome("Alysson");
        dto.setNumeroSerie("5454");
        dto.setIdCLP("445");

        service.salvar(dto);
    }
}
