docker run -d -p 3299:3306 --name springsecurity_oauth_mysql \
-e MYSQL_ROOT_PASSWORD=$(cat .env | grep MYSQL_ROOT_PASSWORD | cut -d '=' -f2) mysql:latest
