package com.fiap.restaurante.infrastructure.adapter.out;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fiap.restaurante.application.port.out.PagamentoAdapterPortOut;
import org.springframework.http.*;

@Component
public class PagamentoAdapterOut implements PagamentoAdapterPortOut{

    private final String accessToken;
    private final String ngrokURL;
    private final String apiQRs;

    public PagamentoAdapterOut(String accessToken, String ngrokURL, String apiQRs) {
        this.accessToken = accessToken;
        this.ngrokURL = ngrokURL;
        this.apiQRs = apiQRs;
    }

    @Override
    public boolean realizarPagamento() {
        return true;
    }

    @Override
    public String gerarQRCodePagamento(Double valor, String descricao) {
        RestTemplate restTemplate = new RestTemplate();

        // Configura o cabeçalho da requisição
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        String dataExpiracao = LocalDateTime.now()
            .plusMinutes(10)
            .atOffset(ZoneOffset.of("-03:00"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

        // Configura o corpo da requisição
        JSONObject body = new JSONObject();
        body.put("external_reference", "123456");
        body.put("notification_url",  ngrokURL + "/api/v1/pagamento/webhook");
        body.put("expiration_date", dataExpiracao);
        body.put("title", descricao);
        body.put("description", descricao);
        body.put("total_amount", valor);
        body.put("items", new JSONObject[]{
            new JSONObject()
                .put("title", descricao)
                .put("quantity", 1)
                .put("unit_price", valor)
                .put("unit_measure", "unit")
                .put("total_amount", valor)
        });

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        // Enviar a requisição POST
        ResponseEntity<String> response = restTemplate.postForEntity(
            apiQRs,
            request,
            String.class
        );

        System.err.println(response.getStatusCode());

        if (response.getStatusCode() == HttpStatus.CREATED) {
            // Retornar o URL do QR Code
            JSONObject jsonResponse = new JSONObject(response.getBody());
            return jsonResponse.getString("qr_data");
        } else {
            throw new RuntimeException("Erro ao gerar QR Code: " + response.getBody());
        }
    }

    @Override
    public ResponseEntity<String> receberNotificacao(Map<String, Object> payload) {
        if (payload == null) {
            return new ResponseEntity<>("Payload vazio", HttpStatus.BAD_REQUEST);
        }

        // Processar o payload
        Long paymentId = (Long) payload.get("id");
        String action = (String) payload.get("action");
        String dateCreated = (String) payload.get("date_created");
        String type = (String) payload.get("type");
        String userId = (String) payload.get("user_id");

        // Exibir o payload recebido
        if (action != null && action.contains("payment.created")) {
            // Exibir informações para verificação
            System.out.println("ID do pagamento: " + paymentId);
            System.out.println("Status do pagamento: " + action);
            System.out.println("Data de Criação: " + dateCreated);
            System.out.println("Tipo: " + type);
            System.out.println("ID do usuario: " + userId);
        }

        return new ResponseEntity<>("Notificação recebida com sucesso", HttpStatus.OK);
    }
    
}
