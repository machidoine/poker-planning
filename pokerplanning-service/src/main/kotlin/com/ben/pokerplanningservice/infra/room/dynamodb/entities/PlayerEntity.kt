package com.ben.pokerplanningservice.infra.room.dynamodb.entities

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import com.ben.pokerplanningservice.domain.model.Player

@DynamoDBDocument
data class PlayerEntity(
    @get:DynamoDBAttribute var name: String = "",
    @get:DynamoDBAttribute var card: Int? = null,
    @get:DynamoDBAttribute var hasPlayed: Boolean = false,
    @get:DynamoDBAttribute var id: PlayerIdEntity = PlayerIdEntity()
)
fun PlayerEntity.toDomain() = Player(
    name = name,
    card = card,
    hasPlayed = hasPlayed,
    id = id.toDomain()
)

fun Player.toEntity() = PlayerEntity(
    name = name,
    card = card,
    hasPlayed = hasPlayed,
    id = id.toEntity()
)
