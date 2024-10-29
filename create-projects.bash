#!/usr/bin/env bash

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=employees-service \
--package-name=com.watchstore.employees \
--groupId=com.watchstore.employees \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
employees-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=inventory-service \
--package-name=com.watchstore.inventory \
--groupId=com.watchstore.inventory \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
inventory-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=stores-service \
--package-name=com.watchstore.stores \
--groupId=com.watchstore.stores \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
stores-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=sales-service \
--package-name=com.watchstore.sales \
--groupId=com.watchstore.sales \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
sales-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=api-gateway \
--package-name=com.watchstore.apigateway \
--groupId=com.watchstore.apigateway \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
api-gateway