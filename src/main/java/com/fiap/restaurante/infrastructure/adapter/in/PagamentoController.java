package com.fiap.restaurante.infrastructure.adapter.in;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.restaurante.application.port.out.usecase.PagamentoUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.QrCodeRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/pagamento")
public class PagamentoController{

    @Autowired
    private PagamentoUseCasePortOut pagamentoUseCasePortOut;

    @GetMapping("/status/{idPedido}")
    public String consultarStatusPagamento(@PathVariable Long idPedido) {
        return this.pagamentoUseCasePortOut.consultarStatusPagamento(idPedido);
    }

    @PostMapping("/gerar-qrcode")
    public String gerarQrCode(@RequestBody QrCodeRequest qrCodeRequest) {
        return pagamentoUseCasePortOut.gerarQRCodePagamento(qrCodeRequest.valor(), qrCodeRequest.descricao());
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receberNotificacao(@RequestBody Map<String, Object> payload) {
        return pagamentoUseCasePortOut.receberNotificacao(payload);
    }
    

}