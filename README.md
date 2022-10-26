## Projeto Template

###1 - Dar um replace all nos arquivo:
    Caminhos:
        pom.xml:
            De: template-application
            Para: ** {{nome do novo microserviço}}
    
        pipelinebuild/Dockerfile:
                De: template-application
                Para: {{nome do novo microserviço}}

###2 - Trocar essas linhas:
    Caminhos:
        src/main/java/com/gingapay/templateapplication/configuration/OpenApi30Config.java:
        @OpenAPIDefinition(info = @Info(title = {{nome do novo microserviço}}, version = "v1"))

###3 - Configuração do banco (postgres, nem todas as aplicações vão utilizar esse banco):
    Caminhos:
        src/main/resources/application-local.yaml:
            De: url: jdbc:postgresql://localhost:25432/dbtemplate
            De: url: jdbc:postgresql://localhost:25432/{{nome do banco do micro serviço}}

###4 - Configuração docker para gerar um banco local:
    Caminhos:
        docker/docker-compose.yml:
            De: - POSTGRES_DB=dbtemplate
            Para: De: - POSTGRES_DB={{nome do banco do micro serviço}}****
