package br.com.eoxygen.lambda;
import br.com.eoxygen.dto.CidadeDTO;
import br.com.eoxygen.enuns.HTTPStatus;
import br.com.eoxygen.service.CidadeService;
import br.com.eoxygen.util.ApiGatwayResponseUtil;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import java.util.List;
import java.util.Map;

public class FunctionLambda  implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        Gson gson = new Gson();
        CidadeService service = new CidadeService();

        try{
            Map<String,String> parametros = input.getQueryStringParameters();

            String cnpjCliente = parametros.get("cnpj_cliente");
            String id = parametros.getOrDefault("id", null);
            String idEstado = parametros.getOrDefault("id_estado", null);

            List<CidadeDTO> dtoList = service.consultar(cnpjCliente,id,idEstado);

            return ApiGatwayResponseUtil.construirRetorno(gson,dtoList, HTTPStatus.SUCESSO);
        }catch (Exception e){
            e.printStackTrace();
            return ApiGatwayResponseUtil.construirRetorno(gson,e.getMessage(),HTTPStatus.ERRO_SERVIDOR);
        }finally {
            try{
                service.repository.fecharConexao();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
