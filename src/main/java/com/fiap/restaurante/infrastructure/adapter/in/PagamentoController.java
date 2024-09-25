package com.fiap.restaurante.infrastructure.adapter.in;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.restaurante.application.port.out.usecase.PagamentoUseCasePortOut;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/pagamento")
public class PagamentoController{

    @Autowired
    private PagamentoUseCasePortOut pagamentoUseCasePortOut;

    @PostMapping
    public boolean pagar(@RequestBody String entity) {
        return this.pagamentoUseCasePortOut.pagar();
    }

    @PostMapping("/gerar-qrcode")
    public String gerarQrCode(@RequestParam Double valor, @RequestParam String descricao) {
        return pagamentoUseCasePortOut.gerarQRCodePagamento(valor, descricao);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receberNotificacao(@RequestBody Map<String, Object> payload) {
        return pagamentoUseCasePortOut.receberNotificacao(payload);
    }
    

}