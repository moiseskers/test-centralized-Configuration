## Projeto Template
### 1 - Dar um replace all nos arquivo:
```
    Caminhos:
        pom.xml:
            De: template-application
            Para: {{nome do novo microserviço}}
    
        pipelinebuild/Dockerfile:
                De: template-application
                Para: {{nome do novo microserviço}}
```
### 2 - Trocar essas linhas:
```
    Caminhos:
        src/main/java/com/gingapay/templateapplication/configuration/OpenApi30Config.java:
        @OpenAPIDefinition(info = @Info(title = {{nome do novo microserviço}}, version = "v1"))
```
### 3 - Configuração do banco (postgres, nem todas as aplicações vão utilizar esse banco):
```
    Caminhos:
        src/main/resources/application-local.yaml:
            De: url: jdbc:postgresql://localhost:25432/dbtemplate
            De: url: jdbc:postgresql://localhost:25432/{{nome do banco do micro serviço}}
```
### 4 - Configuração docker para gerar um banco local:
```
    Caminhos:
        docker/docker-compose.yml:
            De: - POSTGRES_DB=dbtemplate
            Para: De: - POSTGRES_DB={{nome do banco do micro serviço}}
```
### 5 - Renomar classe main:
```
    Caminhos:
        src/main/java/com/gingapay/templateapplication/TemplateApplication.java:
        De: TemplateApplication.java
        Para: {{nome do novo microserviço}}.java
```
### Passos opcionais para rodar o projeto no localhost:
```
    1 - Utilizar o arquivo já configurado no passo 4 para iniciar um banco local (caso a aplicação tenha banco de dados)
        Caminhos:
            docker/docker-compose.yml: 	
    2 - Acessar esse container e criar o nome do banco de acordo com o nome especificado no arquivo docker/docker-compose.yml:
         - POSTGRES_DB={{nome do banco do micro serviço}}
   
  
    3 - Iniciar a aplicação com o perfil spring boot local:
        No editor intellij dá para iniciar a aplicação com a seguinte flag vm options:
            -Dspring.profiles.active=local

        Caso necessário tambem é possivel configurar no arquivo src/main/resources/application.yml dessa forma:
            spring:
                profiles:
                    active: local
   
    4 - Caso o projeto seja necessário a utilização de filas:
        1 - No pom.xml tirar o comentário das seguintes dependências: 

            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-amqp</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.amqp</groupId>
                <artifactId>spring-rabbit-test</artifactId>
                <scope>test</scope>
            </dependency>

        2 - Na classe main do projeto tirar o comentário das seguintes linhas:
            1 - @EnableRabbit
            
            2 - @Bean
                public MessageConverter converter() {
                    return new Jackson2JsonMessageConverter();
                }
```