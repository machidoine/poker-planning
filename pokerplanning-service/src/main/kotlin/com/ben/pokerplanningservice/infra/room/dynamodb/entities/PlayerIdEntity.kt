package com.ben.pokerplanningservice.infra.room.dynamodb.entities

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import com.ben.pokerplanningservice.domain.model.PlayerId
import java.util.*

@DynamoDBDocument
data class PlayerIdEntity(
    @get:DynamoDBAttribute var publicId: String = UUID.randomUUID().toString(),
    @get:DynamoDBAttribute var privateId: String = UUID.randomUUID().toString()
)

fun PlayerIdEntity.toDomain() = PlayerId(
    publicId = UUID.fromString(publicId),
    privateId = UUID.fromString(privateId)
)

fun PlayerId.toEntity() = PlayerIdEntity(
    publicId = publicId.toString(),
    privateId = privateId.toString()
)