package com.ben.pokerplanningservice.exposition.dto

import com.ben.pokerplanningservice.domain.model.Player
import java.util.*

data class PlayerDto(
    val name: String,
    val card: String?,
    val hasPlayed: Boolean,
    val publicId: UUID
)

fun Player.toPlayerDto(showCard: Boolean) = PlayerDto(
    name = name,
    card = if (showCard) card else null,
    hasPlayed = hasPlayed,
    publicId = id.publicId
)