# MED04132 Programming Assignment


## Start the web service & database in Docker
You must have Docker installed on your running machine.
- ./start-local-app.bat

## Start the web service without Docker
You still need PostgreSQL server running on the same machine at port 5432
- ./gradlew :observation-app:bootRun 

## Run Unit Tests
Unit tests can be run without starting up any Docker images.
- ./gradlew :observation-app:test 

## Run Integration Tests
Integration tests rely on a PostgreSQL db server.
Therefore, you must start the PostgreSQL Docker image in this package before running integration tests.
- ./gradlew :observation-app:integrationTest 
