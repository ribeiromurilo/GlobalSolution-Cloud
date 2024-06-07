# DirtyCode

<p><a href="https://www.youtube.com/watch?v=HRahsNwfS4w" target="_blank">📍 PITCH (Apresentação do projeto)</a> </p>
<p><a href="https://youtu.be/bhJVbf40-5Q?si=fJIaT01FxUcJg4YP" target="_blank">📍 Vídeo da aplicação</a> </p>

<h3>Projeto</h3>
<p> Nosso projeto da DirtyCode visa criar uma plataforma integrada de monitoramento e conservação marinha utilizando tecnologias de inteligência artificial (IA) e machine learning. A plataforma será capaz de coletar e analisar dados ambientais, gerando insights para o governo, ONGs, comunidades locais e outros stakeholders.
<p> Com isso, pretendemos promover práticas de gestão ambiental ecologicamente responsáveis, socialmente inclusivas e economicamente viáveis a longo prazo, atendendo a economia azul, que promove o crescimento econômico baseado na preservação dos ecossistemas marinhos e na sustentabilidade ambiental. </p>
<p>Nossa aplicação será uma API RESTful desenvolvida em Java usando Spring Boot, fornecendo operações CRUD (Create, Read, Update, Delete) de alertas, dados, relatórios, sensores e usuários conectados com o nosso banco de dados. A aplicação utilizará containers com Docker e adaptada com Docker Compose, garantindo fácil implantação e escalabilidade. </p>

--------------------------------------------------

<h3>Integrantes</h3>

- Aleck Ramos Cappucci – RM551340 (2TDSPM)
- Matheus Chagas de Moraes Sampaio – RM550489 (2TDSPH)
- Murilo Ribeiro Valério da Silva – RM550858 (2TDSPF)
- Paulo Henrique Moreira Angueira – RM99704 (2TDSPH)
- Victor Hugo Astorino Barra Mansa – RM550573 (2TDSPH)


--------------------------------------------------

<h3>Como Rodar a Aplicação </h3>

<h4>Pré-requisitos</h4> 

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/#java11) instalado em seu sistema.
- [Eclipse IDE](https://www.eclipse.org/downloads/) instalado em seu sistema.
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado em seu sistema.
- [Maven](https://maven.apache.org/download.cgi) instalado em seu sistema.

<h4>Passos para Executar a Aplicação</h4> 

- Clonar o Repositório:
```bash
git clone https://github.com/ribeiromurilo/GlobalSolution-Java
```

- Abrir o Doker Desktop
- Abrir o cmd (Prompt de Comando)
- Localizar o diretório do arquivo
- Inserir:
```bash
mvn clean install
```
```bash
docker-compose build
```
```bash
docker-compose up -d
```
```bash
docker-compose logs app
```
```bash
docker volume ls
```
```bash
docker volume inspect app-data
```

- Para testar a API, acessar:

[Swagger UI](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)
--------------------------------------------------

<div align="center">
    <img src="./public/readme.gif" alt="project gif" height="400px"/>
</div>

## 🖥 Tecnologias

- [Spring](https://spring.io)
- [Docker](https://www.docker.com)
- [Oracle SQL Developer](https://www.oracle.com/br/database/sqldeveloper/)

<br/>

## 🧾 Funcionalidades

- [x] CRUD de usuários/Relatorios/Alertas/Sensores/Dados.
      
<br/>
