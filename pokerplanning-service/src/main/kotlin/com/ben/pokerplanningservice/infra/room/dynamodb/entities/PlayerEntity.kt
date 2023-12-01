package com.ben.pokerplanningservice.infra.room.dynamodb.entities

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.ben.pokerplanningservice.domain.model.Player

data class PlayerEntity(
    @get:DynamoDBAttribute val name: String,
    @get:DynamoDBAttribute val card: Int? = null,
    @get:DynamoDBAttribute val hasPlayed: Boolean = false,
    @get:DynamoDBAttribute val id: PlayerIdEntity = PlayerIdEntity()
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
