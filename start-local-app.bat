@echo off

call ./gradlew.bat :observation-app:clean
call ./gradlew.bat :observation-app:bootJar

docker build -t observation-db -f observation-db/Dockerfile ./observation-db
docker build -t observation-app -f observation-app/Dockerfile ./observation-app

docker-compose up