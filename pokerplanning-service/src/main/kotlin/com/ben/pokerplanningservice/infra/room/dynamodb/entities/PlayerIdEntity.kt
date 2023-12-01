package com.ben.pokerplanningservice.infra.room.dynamodb.entities

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.ben.pokerplanningservice.domain.model.PlayerId
import java.util.*

data class PlayerIdEntity(
    @get:DynamoDBAttribute val publicId: UUID = UUID.randomUUID(),
    @get:DynamoDBAttribute val privateId: UUID = UUID.randomUUID()
)

fun PlayerIdEntity.toDomain() = PlayerId(
    publicId = publicId,
    privateId = privateId
)

fun PlayerId.toEntity() = PlayerIdEntity(
    publicId = publicId,
    privateId = privateId
)