package br.com.eoxygen.lambda;

import br.com.eoxygen.dto.AlocacaoRetornoDTO;
import br.com.eoxygen.dto.AlocacaoUsinaDTO;
import br.com.eoxygen.enuns.HTTPStatus;
import br.com.eoxygen.service.UsinaService;
import br.com.eoxygen.util.ApiGatwayResponseUtil;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

public class FunctionLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        UsinaService service = new UsinaService();
        Gson gson = new Gson();

        try{
            AlocacaoUsinaDTO dto = gson.fromJson(input.getBody(),AlocacaoUsinaDTO.class);
            AlocacaoRetornoDTO alocacaoRetornoDTO  = service.alocar(dto);
            return ApiGatwayResponseUtil.construirRetorno(gson,alocacaoRetornoDTO,HTTPStatus.SUCESSO);
        }catch (Exception e){
            e.printStackTrace();
            return ApiGatwayResponseUtil.construirRetorno(gson,e.getMessage(),HTTPStatus.ERRO_SERVIDOR);
        }finally {
            try {
                service.repository.fecharConexao();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
