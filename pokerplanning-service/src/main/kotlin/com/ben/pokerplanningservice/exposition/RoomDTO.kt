package com.ben.pokerplanningservice.exposition

import com.ben.pokerplanningservice.domain.model.Player
import com.ben.pokerplanningservice.domain.model.Room
import java.util.*

data class RoomDTO(
    val id: String,
    val players: List<PlayerDto>,
    val cardRevealed: Boolean
)

data class PlayerDto(
    val name: String,
    val card: Int?,
    val hasPlayed: Boolean,
    val publicId: UUID
)

fun Room.toRoomDto() = RoomDTO(
    id = id,
    cardRevealed = cardRevealed,
    players = players.map { p -> p.toPlayerDto(cardRevealed) }
)

fun Player.toPlayerDto(showCard: Boolean) = PlayerDto(
    name = name,
    card = if (showCard) card else null,
    hasPlayed = hasPlayed,
    publicId = id.publicId
)