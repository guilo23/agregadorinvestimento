# Agregador de Investimento

Um projeto para a criação de usuarios, contas e associação de ações com informações atualizadas da b3 através da api da brapi

## 🚀 Começando

.Crie uma conta na  api da Brapi e adcione como variavél de ambiente com o nome "TOKEN", iniciar o docker descktop e ultilizar o comando:
```
docker compose up
```
### 📋 Pré-requisitos

- docker
- postman
- Token da Brapi

  ## ⚙️ Executando os testes

-endpoint para criar user: http://localhost:8081/v1/users
```
request:
{
    "username":"teste",
    "email":"teste@gmail.com",
    "password":"senhateste"
}
```
- endpoint para criar uma account http://localhost:8080/v1/users/{userId}/accounts
```
  request:
{
    "accountName":"Gui"
}
  ```
 - endpoint para associar uma stock: http://localhost:8080/v1/accounts/{accountId}/stocks
```
  request:
{
    "stockId":"ELET6",
    "purchasePrice":"30.0",
    "quantity":"10"
}
```



    
