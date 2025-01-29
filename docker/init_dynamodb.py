import boto3

# Configurar o recurso DynamoDB
dynamodb = boto3.resource('dynamodb', region_name='us-east-1')

# Criar tabela de pedidos
try:
    pedidos_table = dynamodb.create_table(
        TableName='Pedidos',
        KeySchema=[
            {
                'AttributeName': 'PedidoID',
                'KeyType': 'HASH'  # Partition key
            }
        ],
        AttributeDefinitions=[
            {
                'AttributeName': 'PedidoID',
                'AttributeType': 'S'
            }
        ],
        BillingMode='PAY_PER_REQUEST'
    )
    pedidos_table.wait_until_exists()
    print("Tabela 'Pedidos' criada com sucesso.")
except Exception as e:
    print(f"Erro ao criar a tabela 'Pedidos': {e}")

# Inserir dados iniciais na tabela de pedidos
try:
    pedidos_table.put_item(
        Item={
            'PedidoID': '12345',
            'ClienteID': '67890',
            'DataPedido': '2023-10-01T12:34:56Z',
            'Status': 'Pendente'
        }
    )
    print("Dados iniciais inseridos na tabela 'Pedidos'.")
except Exception as e:
    print(f"Erro ao inserir dados na tabela 'Pedidos': {e}")

# Criar tabela de pedido_produtos
try:
    pedido_produtos_table = dynamodb.create_table(
        TableName='PedidoProdutos',
        KeySchema=[
            {
                'AttributeName': 'PedidoProdutoID',
                'KeyType': 'HASH'  # Partition key
            }
        ],
        AttributeDefinitions=[
            {
                'AttributeName': 'PedidoProdutoID',
                'AttributeType': 'S'
            }
        ],
        BillingMode='PAY_PER_REQUEST'
    )
    pedido_produtos_table.wait_until_exists()
    print("Tabela 'PedidoProdutos' criada com sucesso.")
except Exception as e:
    print(f"Erro ao criar a tabela 'PedidoProdutos': {e}")

# Inserir dados iniciais na tabela de pedido_produtos
try:
    pedido_produtos_table.put_item(
        Item={
            'PedidoProdutoID': '12345',
            'PedidoID': '67890',
            'ProdutoID': '54321',
            'Quantidade': 2
        }
    )
    print("Dados iniciais inseridos na tabela 'PedidoProdutos'.")
except Exception as e:
    print(f"Erro ao inserir dados na tabela 'PedidoProdutos': {e}")


"""
Este script faz o seguinte:

1. Inicializa o cliente DynamoDB.
2. Cria a tabela 'Pedidos' com a chave primária 'PedidoID'.
3. Insere um item inicial na tabela 'Pedidos'.
4. Cria a tabela 'PedidoProdutos' com a chave primária 'PedidoProdutoID'.
5. Insere um item inicial na tabela 'PedidoProdutos'.

Passos detalhados:

1. Inicializa o cliente DynamoDB:
   - Utiliza a biblioteca boto3 para interagir com o serviço DynamoDB da AWS.

2. Cria a tabela 'Pedidos':
   - Define a tabela 'Pedidos' com a chave primária 'PedidoID' do tipo string (S).
   - Utiliza o modo de cobrança 'PAY_PER_REQUEST' para escalabilidade automática.

3. Insere um item inicial na tabela 'Pedidos':
   - Adiciona um item com 'PedidoID', 'ClienteID', 'DataPedido' e 'Status'.

4. Cria a tabela 'PedidoProdutos':
   - Define a tabela 'PedidoProdutos' com a chave primária 'PedidoProdutoID' do tipo string (S).
   - Utiliza o modo de cobrança 'PAY_PER_REQUEST' para escalabilidade automática.

5. Insere um item inicial na tabela 'PedidoProdutos':
   - Adiciona um item com 'PedidoProdutoID', 'PedidoID', 'ProdutoID' e 'Quantidade'.

Exemplo de uso:
    python init_dynamodb.py
"""    