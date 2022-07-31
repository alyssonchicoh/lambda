package br.com.eoxygen.lambda;

import br.com.eoxygen.dto.AlocacaoDTO;
import br.com.eoxygen.enuns.HTTPStatus;
import br.com.eoxygen.service.AlocacaoService;
import br.com.eoxygen.util.ApiGatwayResponseUtil;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

public class FunctionLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        Gson gson = new Gson();
        AlocacaoService service = new AlocacaoService();
        try{
            AlocacaoDTO dto = gson.fromJson(input.getBody(),AlocacaoDTO.class);
            service.alocar(dto);

            return ApiGatwayResponseUtil.construirRetornoSemBody(HTTPStatus.SUCESSO);
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
