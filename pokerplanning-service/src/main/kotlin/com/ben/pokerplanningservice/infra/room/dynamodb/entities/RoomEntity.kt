package com.ben.pokerplanningservice.infra.room.dynamodb.entities

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.ben.pokerplanningservice.domain.model.Room
import java.time.Instant

@DynamoDBTable(tableName = "Rooms")
data class RoomEntity(
    @get:DynamoDBHashKey(attributeName = "id")
    val id: String,

    @get:DynamoDBAttribute val players: List<PlayerEntity> = listOf(),
    @get:DynamoDBAttribute val cardRevealed: Boolean = false,
    @get:DynamoDBAttribute val creationDate: Instant = Instant.now(),
    @get:DynamoDBAttribute val lastAccessDate: Instant = Instant.now()
)

fun RoomEntity.toDomain() = Room(
    id = id,
    players = players.map { it.toDomain() },
    cardRevealed = cardRevealed,
    creationDate = creationDate,
    lastAccessDate = lastAccessDate
)

fun Room.toEntity() = RoomEntity(
    id = id,
    players = players.map { it.toEntity() },
    cardRevealed = cardRevealed,
    creationDate = creationDate,
    lastAccessDate = lastAccessDate
)