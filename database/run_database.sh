#!/bin/sh
export DATABASE_HOST=localhost
export DATABASE_PORT=5432
export DATABASE_NAME=postgres
export DATABASE_USER=postgres
export DATABASE_PASSWORD=admin
export ACTIVE_PROFILE=dev
export LANGUAGE=$1
docker-compose up database
