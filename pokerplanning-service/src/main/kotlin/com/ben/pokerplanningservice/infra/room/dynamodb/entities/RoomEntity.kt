package com.ben.pokerplanningservice.infra.room.dynamodb.entities

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.ben.pokerplanningservice.domain.model.Room
import java.time.Instant

@DynamoDBTable(tableName = "Rooms")
data class RoomEntity(
    @get:DynamoDBHashKey(attributeName = "id")
    var id: String = "",

    @get:DynamoDBAttribute var players: List<PlayerEntity> = listOf(),
    @get:DynamoDBAttribute var cardRevealed: Boolean = false,
    @get:DynamoDBAttribute var creationDate: Long = Instant.now().toEpochMilli(),
    @get:DynamoDBAttribute var lastAccessDate: Long = Instant.now().toEpochMilli()
)

fun RoomEntity.toDomain() = Room(
    id = id,
    players = players.map { it.toDomain() },
    cardRevealed = cardRevealed,
    creationDate = Instant.ofEpochMilli(creationDate),
    lastAccessDate = Instant.ofEpochMilli(lastAccessDate)
)

fun Room.toEntity() = RoomEntity(
    id = id,
    players = players.map { it.toEntity() },
    cardRevealed = cardRevealed,
    creationDate = creationDate.toEpochMilli(),
    lastAccessDate = lastAccessDate.toEpochMilli()
)