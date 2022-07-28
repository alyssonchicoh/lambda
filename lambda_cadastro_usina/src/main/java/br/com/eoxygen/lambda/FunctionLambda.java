package br.com.eoxygen.lambda;

import br.com.eoxygen.dto.UsinaDTO;
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
        UsinaService usinaService = new UsinaService();
        Gson gson = new Gson();
        try{
            UsinaDTO usinaDTO = gson.fromJson(input.getBody(),UsinaDTO.class);
            Long idUsina = usinaService.salvar(usinaDTO);
            usinaDTO.setId(idUsina);

            return ApiGatwayResponseUtil.construirRetorno(gson,usinaDTO,HTTPStatus.SUCESSO);
        }catch (Exception e){
            e.printStackTrace();
            return ApiGatwayResponseUtil.construirRetorno(gson,e.getMessage(),HTTPStatus.ERRO_SERVIDOR);
        }finally {
            try{
                usinaService.repository.fecharConexao();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
