**API para serviço**

API feita no treinamento de desenvolvimento da **AlgaWorks**
Usando:
			- [Spring Boot ](https://spring.io/projects/spring-boot "Spring Boot ")
			- Maven
			- MySQL
#### ROTAS:

------------

- /clientes [GET]-> para listar todos 
- /clientes [POST]-> para adicionar
- /clientes/{id} [PUT]-> para atualizar
- /clientes/{id} [DELETE]-> para deletar
- /clientes/{id} [GET]-> para buscar

------------
- /ordens-servico [POST]-> para adicionar
- /ordens-servico/{id}/comentarios [POST]-> add comentário em ordem espec.
- /ordens-servico/{id}/finalizacao [PUT]-> finalizar uma ordem espec.
- /ordens-servico/{id}/comentarios [GET]->listar comentario espec.
- /ordens-servico/comentarios [GET]->listar comentarios
- /ordens-servico/{id} [GET]->buscar ordem específica

