# Documento de Requisitos do Sistema - VettoriPay

**Versão:** 1.0

## Histórico de Alterações

| Data       | Versão | Descrição                              | Autor            |
|------------|--------|----------------------------------------|------------------|
| 18/09/2023 | 1.0    | Definição dos requisitos a serem implementados | Lucas A. Cavassoni |

## Descrição Geral do Sistema

O projeto em questão visa desenvolver um sistema de pagamento denominado "VettoriPay" que atende a dois tipos de usuários: os usuários comuns e os lojistas. Ambos os tipos de usuários terão carteiras virtuais com saldos em dinheiro e a capacidade de realizar transferências financeiras entre si.

## Requisitos Funcionais

### [RF001] - Cadastro de Usuário

- O sistema deve permitir o cadastro de usuário fornecendo Nome Completo, CPF, E-mail, Telefone, Tipo (Comum ou Lojista), Senha.
- O CPF (usuário comum) ou CNPJ (lojistas) deve ser um campo obrigatório no cadastro.
- CPF/CNPJ, E-mail e Telefone devem ser únicos por usuário.

### [RF002] - Gestão de Carteira

- Cada usuário, seja ele comum ou lojista, deve ter uma carteira virtual que irá armazenar seu saldo.
- Usuários poderão visualizar o saldo de suas carteiras.

### [RF003] - Transferência Financeira

- Usuários devem ser capazes de iniciar transferências de dinheiro.
- Usuário comum pode enviar dinheiro para outros usuários ou lojistas.
- Lojistas apenas receberão dinheiro.
- Deve ser validado se o usuário tem saldo antes da transferência.
- A operação deve ser uma transação e ser revertida em qualquer caso de inconsistência.
- Ao iniciar a transferência, o saldo deve ser bloqueado.
- Antes de finalizar a transferência, deve-se consultar um serviço autorizador externo (https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6).
- O usuário poderá incluir uma mensagem na transferência.

### [RF004] - Notificação de Pagamento

- O sistema deve emitir notificação (e-mail ou SMS) ao usuário que recebeu a transferência.
- O serviço que irá notificar o usuário será: http://o4d9z.mocklab.io/notify.

---

Este é o documento de requisitos do sistema "VettoriPay" versão 1.0. Este documento descreve a funcionalidade planejada para o sistema, incluindo o cadastro de usuários, gestão de carteiras, transferência financeira e notificação de pagamento.
