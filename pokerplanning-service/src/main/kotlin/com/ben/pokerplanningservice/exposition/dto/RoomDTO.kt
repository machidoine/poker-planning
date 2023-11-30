package com.ben.pokerplanningservice.exposition.dto

import com.ben.pokerplanningservice.domain.model.Room

data class RoomDTO(
    val id: String,
    val players: List<PlayerDto>,
    val cardRevealed: Boolean
)

fun Room.toRoomDto() = RoomDTO(
    id = id,
    cardRevealed = cardRevealed,
    players = players.map { p -> p.toPlayerDto(cardRevealed) }
)
