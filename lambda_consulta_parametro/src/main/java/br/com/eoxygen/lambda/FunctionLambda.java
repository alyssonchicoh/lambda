package br.com.eoxygen.lambda;

import br.com.eoxygen.dto.ParametroDTO;
import br.com.eoxygen.enuns.HTTPStatus;
import br.com.eoxygen.service.ParametroService;
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
        ParametroService service = new ParametroService();

        try{
            Map<String,String> parametros = input.getQueryStringParameters();

            String cnpjCliente = parametros.get("cnpj_cliente");
            String id = parametros.getOrDefault("id","");
            String idUsina = parametros.getOrDefault("id_usina","");


            List<ParametroDTO> dtoList = service.consultar(cnpjCliente,id,idUsina);

            return ApiGatwayResponseUtil.construirRetorno(gson,dtoList, HTTPStatus.SUCESSO);
        }catch (Exception e){
            e.printStackTrace();
            return ApiGatwayResponseUtil.construirRetorno(gson,e.getMessage(),HTTPStatus.ERRO_SERVIDOR);
        }finally {
            try{
                service.repository.fecharConexao();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
