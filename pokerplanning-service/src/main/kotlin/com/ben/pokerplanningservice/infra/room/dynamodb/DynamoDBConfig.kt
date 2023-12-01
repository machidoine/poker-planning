package com.ben.pokerplanningservice.infra.room.dynamodb

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableDynamoDBRepositories
    (basePackages = ["com.ben.pokerplanningservice.infra"])
class DynamoDBConfig {
    @Value("\${amazon.dynamodb.endpoint}")
    private val amazonDynamoDBEndpoint: String? = null

    @Value("\${amazon.aws.accesskey}")
    private val amazonAWSAccessKey: String? = null

    @Value("\${amazon.aws.secretkey}")
    private val amazonAWSSecretKey: String? = null

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        val endpointConfiguration = EndpointConfiguration(amazonDynamoDBEndpoint, "")
        return AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(endpointConfiguration)
            .build()
    }

    @Bean
    fun amazonAWSCredentials(): AWSCredentials {
        return BasicAWSCredentials(
            amazonAWSAccessKey, amazonAWSSecretKey
        )
    }
}