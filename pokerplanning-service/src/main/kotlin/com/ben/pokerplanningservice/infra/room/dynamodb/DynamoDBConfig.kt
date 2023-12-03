package com.ben.pokerplanningservice.infra.room.dynamodb

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableDynamoDBRepositories
    (basePackages = ["com.ben.pokerplanningservice.infra"])
class DynamoDBConfig {

    @Bean
    fun amazonDynamoDB(prop: DynamoDBCredentialProperties): AmazonDynamoDB {
        val endpointConfiguration = EndpointConfiguration(prop.dbEndpoint, "")
        return AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(endpointConfiguration)
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(
                        prop.accessKey, prop.secretKey
                    )
                )
            )
            .build()
    }

//    @Bean
//    fun amazonAWSCredentials(prop: DynamoDBCredentialProperties): AWSCredentials {
//        return BasicAWSCredentials(
//            prop.accessKey, prop.secretKey
//        )
//    }
//
//    @Bean
//    fun amazonAWSCredentialsProvider(prop: DynamoDBCredentialProperties): AWSCredentialsProvider {
//        return BasicAWSCredentials(
//            prop.accessKey, prop.secretKey
//        )
//    }

//    @Bean
//    fun dynamoProperties(): DynamoDBCredentialProperties {
//        return DynamoDBCredentialProperties()
//    }
}