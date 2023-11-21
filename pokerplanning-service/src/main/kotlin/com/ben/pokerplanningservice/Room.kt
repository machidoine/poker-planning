package com.ben.pokerplanningservice

import lombok.EqualsAndHashCode
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

data class Room(
    val id: String, // to be chqnged to UUID
    val players: CopyOnWriteArrayList<Player> = CopyOnWriteArrayList<Player>(),
    var cardRevealed: Boolean = false // to be changed to val
)

@EqualsAndHashCode(of = ["id.privateId"])
data class Player(
    val name: String,
    val emitter: SseEmitter,
    val card: Int?,
    val hasPlayed: Boolean = false,
    val id: PlayerId = PlayerId()
)

data class PublicPlayer(
    val name: String,
    val card: Int?,
    val hasPlayed: Boolean,
    val publicId: UUID
)

fun Player.toPublicPlayer(showCard: Boolean) = PublicPlayer(
    name = name,
    card = if (showCard) card else null,
    hasPlayed = hasPlayed,
    publicId = id.publicId
)

data class PlayerId(
    val publicId: UUID = UUID.randomUUID(),
    val privateId: UUID = UUID.randomUUID()
)