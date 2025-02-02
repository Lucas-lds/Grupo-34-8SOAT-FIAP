package com.fiap.restaurante.infrastructure.adapter.in;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fiap.restaurante.application.port.out.usecase.PagamentoUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.QrCodeRequest;

@RestController
@RequestMapping("/pagamento")
@Tag(name = "Pagamento", description = "Operações de pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoUseCasePortOut pagamentoUseCasePortOut;

    @Operation(summary = "Consultar status do pagamento", description = "Consulta o status do pagamento de um pedido.")
    @GetMapping("/status/{idPedido}")
    public String consultarStatusPagamento(@PathVariable Long idPedido) {
        return this.pagamentoUseCasePortOut.consultarStatusPagamento(idPedido);
    }

    @Operation(summary = "Gerar QR Code para pagamento", description = "Gera um QR Code para um pagamento com base no valor e descrição.")
    @PostMapping("/gerar-qrcode")
    public String gerarQrCode(@RequestBody QrCodeRequest qrCodeRequest) {
        return pagamentoUseCasePortOut.gerarQRCodePagamento(qrCodeRequest.valor(), qrCodeRequest.descricao());
    }

    @Operation(summary = "Receber notificação de pagamento", description = "Recebe uma notificação de pagamento com o payload.")
    @PostMapping("/webhook")
    public ResponseEntity<String> receberNotificacao(@RequestBody Map<String, Object> payload) {
        return pagamentoUseCasePortOut.receberNotificacao(payload);
    }

}
