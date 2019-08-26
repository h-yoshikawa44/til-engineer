FROM openjdk:8-jdk-alpine

ARG CLI_VERSION=2.4.5

RUN wget http://central.maven.org/maven2/io/swagger/swagger-codegen-cli/${CLI_VERSION}/swagger-codegen-cli-${CLI_VERSION}.jar -O /swagger-codegen-cli.jar

RUN apk --update add bash nodejs npm && rm -rf /var/cache/apk/* 

ADD swagger.json swagger.json
RUN \
sed -i -e 's/localhost:8080/localhost:8000/g' swagger.json && \
sed -i -e 's/"license":{}/"license":""/g' swagger.json && \
# date-time型がモックAPIエラーになるので型指定をswagger定義から削除
sed -i -e 's/"format": "date-time",,//g' swagger.json && \

RUN java -jar /swagger-codegen-cli.jar generate -l nodejs-server -i swagger.json -o src && \
    cd src && npm install

CMD ["java", "-jar", "/swagger-codegen-cli.jar"]