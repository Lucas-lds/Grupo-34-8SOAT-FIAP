Feature: Cliente Service

  Scenario: Cadastrar cliente com sucesso
    Given que o cliente "João" com CPF "123.456.789-00" deseja se cadastrar
    When o cliente se cadastra
    Then o cliente deve ser cadastrado com sucesso

  Scenario: Buscar cliente quando encontrado
    Given que o cliente com CPF "123.456.789-00" está cadastrado
    When busco o cliente pelo CPF
    Then o cliente deve ser retornado

  Scenario: Validar CPF com sucesso
    Given que o CPF "123.456.789-00" é válido
    When o cliente tenta se autenticar
    Then a validação do CPF deve ser realizada com sucesso
