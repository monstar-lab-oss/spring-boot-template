#!/bin/sh
export DATABASE_HOST=
export DATABASE_PORT=
export DATABASE_NAME=
export DATABASE_USER=
export DATABASE_PASSWORD=
export ACTIVE_PROFILE=
export LANGUAGE=$1
docker-compose up database
