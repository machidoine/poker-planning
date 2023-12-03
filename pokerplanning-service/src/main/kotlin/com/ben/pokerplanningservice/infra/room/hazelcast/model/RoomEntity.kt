package com.ben.pokerplanningservice.infra.room.hazelcast.model

import com.ben.pokerplanningservice.domain.model.Room
import java.time.Instant

data class RoomEntity(
    val id: String = "",
    val players: List<PlayerEntity> = listOf(),
    val cardRevealed: Boolean = false,
    val creationDate: Long = Instant.now().toEpochMilli(),
    val lastAccessDate: Long = Instant.now().toEpochMilli()
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