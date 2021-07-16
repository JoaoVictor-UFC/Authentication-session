
 <h1> Authentication-session </h1>
 <p id="sobre" align="center">
📱 Descrição   <!-- Uma aplicação para fazer autenticação de usuarios e salvar a sessão no Redis -->

Tabela de conteúdos 
================= 
<!--ts-->
 * [Sobre](#sobre) 
  
 *  * [Documentação] (#documentacao)
 *  * [Pre Requisitos](#pre-requisitos)
 *  * [Executando a aplicação](#rodando)
 * * [Tecnologias](#tecnologias)
 *  * [Autor](#autor)
 <!--te-->

### Documentação<a id="documentacao"></a>
 
 Documentaçao do projeto foi gerada pelo Swagger-ui
 Então depois de roda a aplicação acesse http://localhost:8888/swagger-ui/
 
### 🛒 Pré-requisitos<a id="pre-requisitos"></a>

  Para roda a aplicação bastar ter o docker instalado na sua maquina:
 [Docker](https://www.docker.com/)
 
 Caso queira gerenciar o banco, recomendo usar o [DBeaver Community](https://dbeaver.io/)
 
   ### 📀Rodando a Aplicação<a id="rodando"></a>
   
````bash 
 # Clone este repositório
 git clone https://github.com/JoaoVictor-UFC/Authentication-session.git
 
 # Acesse a pasta do projeto no terminal
 cd Authentication-session/deploy/
 
 # Rode o comando docker-compose up -d
  Isso ira criar 3 containers com postgres, redis e o da aplicação.
  
 # Tambem é possivel baixar a imagem do projeto no Docker Hub
  docker push johnnykeys/authetication-session:v1
 
 # O servidor iniciará na porta:8888
 # Acesse http://localhost:8888
  Utilizando o insomnia ou postman, deixei as colletions no repositorio
 ````
 
### 🛠 Tecnologias<a id="tecnologias"></a>
 As seguintes ferramentas foram usadas na construção do projeto:
 
  - [Java 8](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html) 
  - [Redis](https://redis.io/)
  - [PostgreSQL](https://www.postgresql.org/)
  - [Spring Boot](https://spring.io/projects/spring-boot)

### 👨‍💻Autor <a id="autor"> </a>

---
<a href="https://github.com/JoaoVictor-UFC" style="text-decoration: none;">
<img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/56576465?v=4" width="100px;"  alt="João Victor"/>

<br />
<span> Feito por João Victor 
  Data:16/07/2021! </span> 
</a> 
