# Desafio-03

# Índice

- [Descrição](#descricao)
- [micro serviços](#micro-servicos)
    * [servidor eureka](#eureka-server)
    * [api gateway](#gateway)
- [Como Começar](#como-começar)
- [Autores](#autores)

# <a name="descricao"></a>Sistema de Votação Empresarial

O Sistema de Votação Empresarial é uma API de backend dedicada à gestão dos processos de decisão dentro de uma empresa.
Sua arquitetura é baseada REST, proporcionando acesso aos recursos por meio de endpoints. A API assegura a persistência
no dados no banco de dados.

# <a name="micro-servicos"></a>Micro Serviços

## <a name="gateway"></a>Api  gateway

* Descrição: O API Gateway unifica as solicitações de vários microsserviços, oferecendo um único ponto de entrada para
  os clientes acessarem os recursos do sistema. Ele gerencia roteamento, autenticação, autorização e outras
  funcionalidades relacionadas à comunicação entre sistemas, simplificando o acesso aos serviços distribuídos.
  por meio da url padrao `http://localhost:8080`.

## <a name="eureka-server"></a>Servidor eureka

Descrição: O Servidor Eureka é uma aplicação centralizada que permite que os microsserviços se registrem e descubram uns
aos outros dinamicamente em um ambiente de computação em nuvem. Ele simplifica a comunicação entre os microsserviços,
oferecendo um mecanismo de descoberta de serviços confiável e escalável. Seu console pode ser acessado
por `http://localhost:8761`

## Serviço para a gestão de Funcionários

* Descrição: Gerencia o cadastro e a administração de Funcionários.
* Entidade:
    * Funcionario
        * nome
        * CPF
        * endereço
        * telefone
        * email
* funcionalidades:
    * Cadastra um Funcionário
    * Excluir um Funcionário
    * buscar um funcionário específico e todos
    * editar um funcionário

* Endereço individual
  `http://localhost:8081`

### endpoints

| Método    | URL                       | Descrição                                                                                  |
|-----------|---------------------------|--------------------------------------------------------------------------------------------|
| `POST`    | /api/v1/funcionarios      | [Cadastra um novo Funcionário no sistema.](#cadastrar)                                     |
| `PUT`     | /api/v1/funcionarios      | [Edita um Funcionário.](#editar)                                                           |
| `GET`     | /api/v1/funcionarios      | [Recupera todos os Funcionários cadastrados no sistema.](#buscartodos)                     |
| `GET`     | /api/v1/funcionarios/{id} | [Recupera um Funcionário pelo seu ID.](#buscarporid)                                       |
| `DELETE`  | /api/v1/funcionarios/{id} | [Deleta um Funcionário pelo seu ID.](#excluir)                                             |
| navegador | /swagger-ui/index.html#/  | Documentação do swagger acessado pelo com acesso pelo endereço individual do micro serviço |

## Serviço para a gestão de Propostas

* Descrição: Gerencia o cadastro e a administração de propostas.
* Entidades:
    * Proposta
        * nome
        * descrição
        * id do funcionário
        * duração da votação
        * inicio da votação
        * lista de votos
    * Votos
        * id do funcionário
        * proposta
        * decisão
* funcionalidades:
    * Cadastra, excluir, edita e busca por uma proposta
    * permite um funcionário votar e criar a sua proposta
    * contabiliza os votos
* Endereço individual
  `http://localhost:8082`

### endpoints

| Método    | URL                                          | Descrição                                                                                               |
|-----------|----------------------------------------------|---------------------------------------------------------------------------------------------------------|
| `POST`    | /api/v1/propostas                            | [Cadastra um nova proposta no sistema.](#cadastrar2)                                                    |
| `PUT`     | /api/v1/propostas                            | [Edita um proposta.](#editar2)                                                                          |
| `GET`     | /api/v1/propostas                            | [Recupera todos os propostas cadastrados no sistema.](#buscartodos2)                                    |
| `GET`     | /api/v1/propostas/{id}                       | [Recupera uma proposta pelo seu ID.](#buscarporid2)                                                     |
| `PUT`     | /api/v1/propostas/votar                      | [Permite o Funcionário votar em uma proposta](#votar)                                                   |
| `GET`     | /api/v1/propostas/calcular/1?funcionarioId=1 | [Permite o gerenciador da votação calcular o resultado da votação respeitando o seu termino](#calcular) |
| `DELETE`  | /api/v1/propostas/{id}                       | [Deleta uma proposta pelo seu ID.](#excluir2)                                                           |
| navegador | /swagger-ui/index.html#/                     | Documentação do swagger acessado pelo com acesso pelo endereço individual do micro serviço              |

## Serviço para a gestão de Funcionários

* Descrição: Gerencia o cadastro e a administração de Funcionários.
* Entidade:
    * resultado
        * id da proposta
        * resultado
* funcionalidades:
    * Persiste os dados das votações anteriores
    * permite consultar uma votação por id e todas paginadas

* Endereço individual
  `http://localhost:8083`

### endpoints

| Método    | URL                      | Descrição                                                                                  |
|-----------|--------------------------|--------------------------------------------------------------------------------------------|
| `GET`     | /api/v1/resultados       | [Recupera todos os resultados armazenados no sistema.](#buscartodos3)                      |
| `GET`     | /api/v1/resultados/{id}  | [Recupera um resultado pelo seu ID.](#buscarporid3)                                        |
| navegador | /swagger-ui/index.html#/ | Documentação do swagger acessado pelo com acesso pelo endereço individual do micro serviço |

## Documentação dos endpoints

Esta seção define os métodos e o formato necessários para enviar uma solicitação, com base no link do endpoints, caso
necessário.

### MS-Funcionario

- **Método**: `cadastrar`
  <a name="cadastrar"></a>
- **body**:

 ``` 
{
    "nome": "Maria de Lourdes",
    "cpf": "12588017010",
    "endereco": "Rua Antonio Carlos",
    "telefone": "1199821994",
    "email": "maria@email.com"
}
 ```

### Buscar todos os Funcionários

<a name="buscarTodos"></a>

- **Método**: `buscarTodos`<a name="buscartodos"></a>
- **body**: vazio

### Buscar Funcionário por ID

<a name="buscarporid"></a>

- **Método**: `buscarPorId`
- **body**: vazio

### excluir

<a name="excluir"></a>

- **Método**: `excluir`
- **body**: vazio

### Editar Funcionário

- **Método**: `editar`
  <a name="editar"></a>
- **Descrição**: Este método editar o Funcionário.
- **body**: vazio

```
{
    "nome": "Maria de Lourdes",
    "cpf": "40892323094",
    "endereco": "Rua Antonio Carlos",
    "telefone": "43995663217",
    "email": "mariadelourdes@email.com"
}
 ```

### MS-proposta

### Cadastrar Proposta

- **Método**: `cadastrar`
  <a name="cadastrar2"></a>
- **body**:

```
{
    "titulo": "Melhoria no Processo de Vendas",
    "descricao": "Proposta para otimizar o processo de vendas com novas ferramentas",
    "funcionarioId": 12
}
```

### Buscar todas as Propostas

<a name="buscartodos2"></a>

- **Método:** `buscarTodos`
- **body**: vazio

### Buscar Proposta por ID

<a name="buscarporid2"></a>

- **método:** `buscarPorId`
- **body**: vazio

#### Editar Proposta

<a name="editar2"></a>

- **Método:**  `editar`
- **body**:

```
{
    "id": 2,
    "nome": "Proposta 1",
    "descricao": "Essa é uma proposta numero 1",
    "criador": {
        "id": 1,
        "nome": "Maria de Lourdes",
        "cpf": "06976003940",
        "endereco": "Rua Antonio Carlos",
        "telefone": "1199821994",
        "email": "mariazinha@email.com"
    },
    "duracaoEmMinutos": 5,
    "inicioVotacao": "2024-06-11T13:49:53.435+00:00"
}
```

#### Deletar Proposta

<a name="excluir2"></a>

- **Método:** `delete`

#### Votar em Proposta

<a name="votar"></a>

- **Método:**  `votar`
- **body**:

```
{
    "funcionarioId": 1,
    "propostaId": 1,
    "decisao": "REJEITAR"
}
```

#### Calcular Resultado da Proposta

<a name="calcular"></a>

- **Método:**  `calcularResultado`
- **body**: vazio

### MS-resultados

### Cadastrar Resultado

- **Método**: `cadastrar`
  <a name="cadastrar3"></a>
- **body**:

```
{
    "propostaid": "1",
    "resultado": "APROVAR",
}
```

### Buscar todas as Resultados

<a name="buscartodos3"></a>

- **Método:** `buscarTodos`
- **body**: vazio

### Buscar resultado por ID

<a name="buscarporid3"></a>

- **método:** `buscarPorId`
- **body**: vazio

# <a name = "como-começar"></a> Como começar

## Requisitos

Para executar esta aplicação, são necessários os seguintes pré-requisitos:

- JDK 17
- Maven

## Execução

Para executar a aplicação, siga os seguintes passos:

1. **Clone o Repositório**: Abra um terminal e clone o repositório do projeto para sua máquina local usando o
   comando `git clone <URL_do_repositório>`.

2. **Navegue até a Pasta do Projeto**: Abra um terminal na raiz do projeto clonado.

3.  **Inicie o Servidor Eureka e Microserviços**: Isso envolve navegar até as respectivas pastas dos
     microserviços e execute
    `mvn spring-boot:run`
para iniciar cada aplicação individualmente.


## <a name="autores"></a>Autores

- [Nicolas Marques](https://github.com/NMSilos)
- [Giovanni Eugenio](https://github.com/giovanieugenio)
- [Pedro Tomaz](https://github.com/tomazdalcortivo)
- [Diogo Meneses](https://github.com/diogo-meneses-franca)

