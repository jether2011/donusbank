#!/bin/bash

cd docker/ && docker-compose -f backend.yaml down && docker-compose -f backend.yaml up -d && cd ..

./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="-Xmx256m -Xms128m" -Dspring-boot.run.arguments="'--DB_USER=donusbank' '--DB_PASSWORD=dOnUSB@nK' '--DB_DONUS_BANK=donusbank'"