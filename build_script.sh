#!/bin/bash

cd gateway
make build-project
make build-docker

cd ../ms-user
make build-project
make build-docker

cd ../gestao-itens
make build-project
make build-docker

cd ../carrinhodecompras
make build-project
make build-docker

cd ..
docker-compose build
docker-compose up -d