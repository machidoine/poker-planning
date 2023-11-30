package com.ben.pokerplanningservice.domain.model

import com.ben.pokerplanningservice.application.command.PlayCardRequest
import com.ben.pokerplanningservice.domain.exception.PlayerDoesNotExistException
import java.time.Instant
import java.util.*

data class Room(
    val id: String,
    val players: List<Player> = listOf(),
    val cardRevealed: Boolean = false,
    val creationDate: Instant = Instant.now(),
    val lastAccessDate: Instant = Instant.now()
) {
    fun playerNotExist(privateId: UUID): Boolean = !players.any { it.id.privateId == privateId }
    private fun findPlayer(privateId: UUID): Player? = players.find { it.id.privateId == privateId }

    fun getPlayer(privateId: UUID): Player = findPlayer(privateId) ?: throw PlayerDoesNotExistException(id, privateId)
    fun createPlayer(name: String): Player = Player(name)
    fun addPlayer(player: Player): Room = this.copy(players = this.players + player)
    fun hideCard(): Room = this.copy(cardRevealed = false)
    fun playCard(request: PlayCardRequest): Room = copy(players = players.map {
        if (it.id.privateId == request.playerId)
            it.copy(card = request.card, hasPlayed = true)
        else
            it
    })

    fun removePlayer(playerId: UUID): Room = copy(players = players.filter {
        it.id.privateId != playerId
    })

    fun reset(): Room = copy(
        cardRevealed = false,
        players = players.map {
            it.copy(card = null, hasPlayed = false)
        })

    fun revealCard(): Room = this.copy(cardRevealed = true)

    companion object {
        fun createRoom(): Room {
            val roomId: String = RoomIdGenerator().generate()
            return Room(roomId)
        }
    }
}

