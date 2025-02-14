Feature: Pedido Service

  Scenario: Cadastrar pedido com sucesso
    Given que o pedido com ID "1" deseja ser cadastrado
    When o pedido se cadastra
    Then o pedido deve ser cadastrado com sucesso

  Scenario: Buscar pedido quando encontrado
    Given que o pedido com ID "1" está cadastrado
    When busco o pedido pelo ID
    Then o pedido deve ser retornado

  Scenario: Validar pedido com sucesso
    Given que o pedido com ID "1" é válido
    When o cliente tenta validar o pedido
    Then a validação do pedido deve ser realizada com sucesso
