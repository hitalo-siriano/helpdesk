# Api CRUD de um sistema de Help-Desk

Um simples crud para gerencia uma rotina de chamados , foi desenvolvido para auxilia no estudo 
do ecossitema java e spring . 


## Demostração

link: https://api-helpdesk-ll7g.onrender.com/{rotas}

## ⚙️ Rotas

fazer o login
Post: /api/v1/auth/login
{
    "login" : "teste",
    "password" : "senha123"
}

Cadastra usuario , atenção definir role (USER ou ADMIN) , se for tenico importante cadastra com job "Support Help" para sastifazer a regra na consulta , optei do definir essa 
essa rega na consulta. 

Post: /api/v1/auth/register
{
      "name" : "teste",
      "Job" : "Support Help Desk",
      "email" : "teste@gmail.com",
      "department" : "TI",  
      "login" : "teste",
      "password" : "senha123", 
      "role" : "ADMIN"
}

Cadasta um novo chamado(ticket) , no userId passa o uuid que pegou no login.Atento aos tamanhos do caracteres possivel retorno de erro.
Post: /api/v1/ticket
{
   "userId":"2fc06998-04e2-4a12-8602-9bc4cd263d8c", 
   "title":"Wifi nao funciana",
   "description":"sem rede nao navega na rede bloquedo",
   "local":"Finance Department" ,
   "contact":"3456" 

}

Retorna todos os meus chamados.
GET: /api/v1/ticket/{uuid}

Retona todos meus chamado fechados
GET:/api/v1/ticket/close/{id}

Retorna todos meus chamado abertos
GET: /api/v1/ticket/open/{id}

Atualizar informação do chamado
PUT : /api/v1/ticket/{id}
{
    "ticketId":"",
    "title":"",
    ""
}

Rotas de ADMIN
info:  "message" e comentario do tecnico 

Retorna todos chamdos existente no sistema , essa rota e somente para tecnicos 
GET: /api/v1/ticket/all/{id}

Fecha chamado
PUT : /v1/ticket/update/status/closed/{id}
{
    "ticketId":"",
  "title":"",
   "description":"",
   "local":"" ,
   "contact":"" 

}

Reabrir chamado 
PUT: /api/v1/ticket/update/status/open/{id}

{
    "ticketId":"",
    "message":""
}
Deleta chamado
DELETE: /api/v1/ticket/{id-user}/delete/{idTicket}

Atender chamado
PUT: /api/v1/ticket/update/status/atende/{id}
{
    "ticketId":"",
    "message":""
}



## 🛠️ Construído com

Mencione as ferramentas que você usou para criar seu projeto

* [Spring Boot]([http://www.dropwizard.io/1.0.2/docs/](https://spring.io/projects/spring-boot/)) - Usado para gera as rotas 
* [Spring Security](https://spring.io/projects/spring-security/)) - Usados para autenticar e definir os nivel de acesso
* [JWT](https://rometools.github.io/rome/) - Usada para gera os tokes de acesso
* [JPA](https://hibernate.org/orm/) - Usada para fazer as consultas no banco de dados.


