package com.ben.pokerplanningservice

import lombok.EqualsAndHashCode
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.Instant
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

data class Room(
    val id: String, // to be chqnged to UUID
    val players: CopyOnWriteArrayList<Player> = CopyOnWriteArrayList<Player>(),
    var cardRevealed: Boolean = false, // to be changed to val
    val creationDate: Instant = Instant.now(),
    var lastAccessDate: Instant = Instant.now()
)

@EqualsAndHashCode(of = ["id.privateId"])
data class Player(
    val name: String,
    var emitter: SseEmitter,
    val card: Int?,
    val hasPlayed: Boolean = false,
    val id: PlayerId = PlayerId()
)

fun Player.toPlayerId() = PlayerId(
    publicId = id.publicId,
    privateId = id.privateId
)

data class PlayerId(
    val publicId: UUID = UUID.randomUUID(),
    val privateId: UUID = UUID.randomUUID()
)