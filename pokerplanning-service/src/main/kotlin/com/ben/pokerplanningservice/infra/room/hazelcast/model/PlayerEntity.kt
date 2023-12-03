package com.ben.pokerplanningservice.infra.room.hazelcast.model

import com.ben.pokerplanningservice.domain.model.Player
import lombok.EqualsAndHashCode

@EqualsAndHashCode(of = ["id.privateId"])
data class PlayerEntity(
    val name: String = "",
    val card: String? = null,
    val hasPlayed: Boolean = false,
    val id: PlayerIdEntity = PlayerIdEntity()
)

fun PlayerEntity.toDomain() = Player(
    id = id.toDomain(),
    name = name,
    card = card,
    hasPlayed = hasPlayed
)

fun Player.toEntity() = PlayerEntity(
    id = id.toEntity(),
    name = name,
    card = card,
    hasPlayed = hasPlayed
)
