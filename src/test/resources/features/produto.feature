Feature: Produto Service

  Scenario: Cadastrar produto com sucesso
    Given que o produto "Hamburguer" deseja ser cadastrado
    When o produto se cadastra
    Then o produto deve ser cadastrado com sucesso

  Scenario: Buscar produto quando encontrado
    Given que o produto "Hamburguer" está cadastrado
    When busco o produto pelo nome
    Then o produto deve ser retornado

  Scenario: Validar produto com sucesso
    Given que o produto "Hamburguer" é válido
    When o cliente tenta validar o produto
    Then a validação do produto deve ser realizada com sucesso
