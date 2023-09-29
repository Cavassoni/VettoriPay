package com.cavassoni.vettoripay.config.data;

import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.cavassoni.vettoripay.domain.mongodb.repository")
public class MongoDBDataSourceConfig extends AbstractMongoClientConfiguration {
    @Override
    protected String getDatabaseName() {
        return "vettoriPay";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

//    @Override
//    protected void configureClientSettings(Builder builder) {
//        builder
//                .credential(MongoCredential.createCredential("name", "db", "pwd".toCharArray()));
//    }
}