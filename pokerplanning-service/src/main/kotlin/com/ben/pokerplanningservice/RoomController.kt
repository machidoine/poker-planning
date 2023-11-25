package com.ben.pokerplanningservice

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.security.SecureRandom
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Controller
@RequestMapping("api/rooms")
class RoomController {
    private val rooms = ConcurrentHashMap<String, Room>()

    @PostMapping("/create")
    @ResponseBody
    fun createRoom(): RoomDTO {
        val roomId = generate()

        rooms[roomId] = Room(roomId)

        return rooms.getValue(roomId).toRoomDto()
    }

    @PostMapping("/{roomId}/reveal-card")
    @ResponseBody
    fun revealCard(@PathVariable roomId: String) {
        getRoom(roomId).cardRevealed = true

        broadcastRoomToEachPlayer(roomId, "reveal-card")
    }

    private fun getRoom(roomId: String): Room {
        if (!rooms.containsKey(roomId)) {
            throw RoomDoesNotExistException(roomId)
        }
        val room = rooms.getValue(roomId)
        room.lastAccessDate = Instant.now()
        return room
    }

    @PostMapping("/{roomId}/hide-card")
    @ResponseBody
    fun hideCard(@PathVariable roomId: String) {
        getRoom(roomId).cardRevealed = false

        broadcastRoomToEachPlayer(roomId, "hide-card")
    }

    @PostMapping("/{roomId}/reset")
    @ResponseBody
    fun reset(@PathVariable roomId: String) {
        val room = getRoom(roomId)
        room.cardRevealed = false
        room.players.replaceAll { p -> p.copy(card = null, hasPlayed = false) }

        broadcastRoomToEachPlayer(roomId, "reset")
    }

    @PostMapping("/{roomId}/player/{playerId}/play-card")
    @ResponseBody
    fun playCard(@PathVariable roomId: String, @PathVariable playerId: UUID, @RequestBody card: Int) {
        val room = getRoom(roomId)
        val foundPlayerIndex = room.players.indexOfFirst { p -> p.id.privateId == playerId }

        if (foundPlayerIndex != -1) {
            val player = room.players[foundPlayerIndex]
            room.players[foundPlayerIndex] = player?.copy(card = card, hasPlayed = true)

            broadcastRoomToEachPlayer(roomId, "play-card")
        }
    }

    @PostMapping("/{roomId}/player/{playerId}/quit")
    @ResponseBody
    fun playerQuit(@PathVariable roomId: String, @PathVariable playerId: UUID) {
        val room = getRoom(roomId)
        val foundPlayerIndex = room.players.indexOfFirst { p -> p.id.privateId == playerId }

        if (foundPlayerIndex != -1) {
            room.players[foundPlayerIndex].emitter.complete()
            room.players.removeAt(foundPlayerIndex)

            broadcastRoomToEachPlayer(roomId, "play-card")
        }
    }

    private fun broadcastRoomToEachPlayer(roomId: String, eventName: String) {
        val room = getRoom(roomId)

        room.players.forEach { player ->
            try {
                player.emitter.send(
                    SseEmitter.event()
                        .name(eventName)
                        .data(room.toRoomDto())
                        .build()
                )
            } catch (e: IOException) {
                room.players.remove(player)
            }
        }
    }

    @GetMapping("/{roomId}/player/{playerId}/sse")
    @ResponseBody
    fun register(
        @PathVariable roomId: String,
        @PathVariable playerId: String
    ): SseEmitter {
        val room = getRoom(roomId)

        room.players
            .find { p -> p.id.privateId.toString() == playerId }
            ?.let {
                it.emitter = SseEmitter(Long.MAX_VALUE)
                it.emitter.onCompletion { room.players.remove(it) }
                it.emitter.onTimeout { room.players.remove(it) }

                it.emitter.send(
                    SseEmitter.event()
                        .name("new-it-player")
                        .data(it.id)
                        .build()
                )

                this.broadcastRoomToEachPlayer(roomId, "new-player")

                return it.emitter
            }

        throw PlayerDoesNotExistException(roomId, playerId)
    }

    @PostMapping("/{roomId}/player/create")
    @ResponseBody
    fun createPlayer(
        @PathVariable roomId: String,
        @RequestParam name: String
    ): PlayerId {
        val room = getRoom(roomId)

        val player = Player(name, SseEmitter(Long.MAX_VALUE), null)
        room.players.add(player)

        broadcastRoomToEachPlayer(roomId, "new-player")

        return player.toPlayerId()
    }

    private fun getPlayer(
        playerId: String,
        room: Room
    ): Player? {
        return room.players.find { p -> p.id.privateId.toString() == playerId }
//        val foundPlayerIndex =
//            room.players.indexOfFirst { p -> p.id.privateId == UUID.fromString(playerId) }
//        if (foundPlayerIndex != -1) {
//            val player = room.players[foundPlayerIndex].copy(emitter = SseEmitter(Long.MAX_VALUE))
//            room.players[foundPlayerIndex] = player
//
//            return player
//        }
    }

    private val random: SecureRandom = SecureRandom()
    private val encoder = Base64.getUrlEncoder().withoutPadding()

    fun generate(): String {
        val buffer = ByteArray(20)
        random.nextBytes(buffer)
        return encoder.encodeToString(buffer)
    }

}