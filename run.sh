#!/usr/bin/env bash

./mvnw clean spring-boot:build-image -DskipTests
cd docker
docker-compose up --build