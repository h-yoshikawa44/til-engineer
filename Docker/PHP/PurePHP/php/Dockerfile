FROM php:7.2-fpm

COPY php.ini /usr/local/etc/php/

RUN apt-get update && \
  # PHPのExtensionをインストール
  docker-php-ext-install pdo_mysql mysqli