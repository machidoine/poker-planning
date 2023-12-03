package com.ben.pokerplanningservice.infra.room.dynamodb

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "amazon.aws")
data class DynamoDBCredentialProperties(
    var accessKey: String = "",
    var dbEndpoint: String = "",
    var secretKey: String = ""
)
