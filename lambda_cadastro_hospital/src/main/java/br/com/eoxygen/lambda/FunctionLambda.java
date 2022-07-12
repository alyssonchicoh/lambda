package br.com.eoxygen.lambda;

import br.com.eoxygen.dto.HospitalDTO;
import br.com.eoxygen.enuns.HTTPStatus;
import br.com.eoxygen.service.HospitalService;
import br.com.eoxygen.util.ApiGatwayResponseUtil;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

public class FunctionLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        HospitalService service = new HospitalService();
        Gson gson = new Gson();
        try{
            HospitalDTO dto = gson.fromJson(input.getBody(),HospitalDTO.class);

            Long id = service.salvar(dto);
            dto.setId(id);

            return ApiGatwayResponseUtil.construirRetorno(gson,dto, HTTPStatus.SUCESSO);
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
