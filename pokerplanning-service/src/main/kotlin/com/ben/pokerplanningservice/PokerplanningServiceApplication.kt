package com.ben.pokerplanningservice

import com.ben.pokerplanningservice.infra.room.dynamodb.DynamoDBCredentialProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(DynamoDBCredentialProperties::class)
class PokerplanningServiceApplication

fun main(args: Array<String>) {
    runApplication<PokerplanningServiceApplication>(*args)
}
