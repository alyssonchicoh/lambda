package br.com.eoxygen.lambda;
import br.com.eoxygen.dto.EstadoDTO;
import br.com.eoxygen.enuns.HTTPStatus;
import br.com.eoxygen.service.EstadoService;
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
        EstadoService service = new EstadoService();
        Gson gson = new Gson();

        try{
            Map<String,String> parametros = input.getQueryStringParameters();
            String cnpjCliente = parametros.get("cnpj_cliente");
            String id = parametros.getOrDefault("id","");

            List<EstadoDTO> dtoLit = service.consultar(cnpjCliente,id);

            return ApiGatwayResponseUtil.construirRetorno(gson,dtoLit, HTTPStatus.SUCESSO);
        }catch (Exception e){

            e.printStackTrace();
            return ApiGatwayResponseUtil.construirRetorno(gson,e.getMessage(),HTTPStatus.ERRO_SERVIDOR);
        }finally {
            try {
                service.repository.fecharConexao();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
