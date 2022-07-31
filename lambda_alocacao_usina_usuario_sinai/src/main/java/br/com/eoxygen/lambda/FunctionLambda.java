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

/**
 * @author alyssonchicoh
 * @since 02/04/2022
 */

public class FunctionLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private AlocacaoService service;
    private Gson gson;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try{
            this.gson = new Gson();
            service = new AlocacaoService();
            AlocacaoDTO dto = gson.fromJson(input.getBody(),AlocacaoDTO.class);

            service.alocar(dto);

            return ApiGatwayResponseUtil.construirRetornoSemBody(HTTPStatus.SUCESSO);
        }catch (Exception e){
            e.printStackTrace();
            return ApiGatwayResponseUtil.construirRetorno(gson,e.getMessage(),HTTPStatus.ERRO_SERVIDOR);
        }
    }
}
