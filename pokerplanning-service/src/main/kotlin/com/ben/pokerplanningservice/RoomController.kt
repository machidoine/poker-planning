package com.ben.pokerplanningservice

import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.*
import java.util.concurrent.ConcurrentHashMap


@Controller
@RequestMapping("api/rooms")
class RoomController {
    private val rooms = ConcurrentHashMap<String, Room>()

    @PostMapping("/{roomId}/reveal-card")
    @ResponseBody
    fun revealCard(@PathVariable roomId: String) {
        rooms[roomId]?.cardRevealed = true
        broadcastRoomToEachPlayer(roomId, "reveal-card")
    }

    @PostMapping("/{roomId}/hide-card")
    @ResponseBody
    fun hideCard(@PathVariable roomId: String) {
        rooms[roomId]?.cardRevealed = false

        broadcastRoomToEachPlayer(roomId, "hide-card")
    }

    @PostMapping("/{roomId}/reset")
    @ResponseBody
    fun reset(@PathVariable roomId: String) {
        rooms[roomId]?.cardRevealed = false
        rooms[roomId]?.players?.replaceAll { p -> p.copy(card = null, hasPlayed = false) }

        broadcastRoomToEachPlayer(roomId, "reset")
    }

    @PostMapping("/{roomId}/player/{playerId}/play-card")
    @ResponseBody
    fun playCard(@PathVariable roomId: String, @PathVariable playerId: UUID, @RequestBody card: Int) {
        val foundPlayerIndex = rooms[roomId]?.players?.indexOfFirst { p -> p.id.privateId == playerId }
        if (foundPlayerIndex != null) {
            val player = rooms[roomId]?.players?.get(foundPlayerIndex)
            rooms[roomId]?.players?.set(foundPlayerIndex, player?.copy(card = card, hasPlayed = true))

            broadcastRoomToEachPlayer(roomId, "play-card")
        }
    }

    private fun broadcastRoomToEachPlayer(roomId: String, eventName: String) {
        rooms[roomId]?.players?.forEach { player ->
            try {
                rooms[roomId]?.toRoomDto()?.let {
                    player.emitter.send(
                        SseEmitter.event()
                            .name(eventName)
                            .data(it)
                            .build()
                    )
                }
            } catch (e: IOException) {
                rooms[roomId]?.players?.remove(player)
            }
        }
    }

    @GetMapping("/{roomId}/register-player")
    @ResponseBody
    fun register(
        @PathVariable roomId: String,
        @RequestParam name: String,
        @RequestParam(required = false) playerId: String?
    ): SseEmitter {
        if (playerId != null) {
            val foundPlayerIndex =
                rooms[roomId]?.players?.indexOfFirst { p -> p.id.privateId == UUID.fromString(playerId) }
            if (foundPlayerIndex != null) {
                val player = rooms[roomId]?.players?.get(foundPlayerIndex)
                val emitter = SseEmitter(Long.MAX_VALUE)
                rooms[roomId]?.players?.set(foundPlayerIndex, player?.copy(emitter = emitter))
                return emitter
            }
            throw PlayerIdNotFoundException()
        } else {
            val player = Player(name, SseEmitter(Long.MAX_VALUE), null)

            rooms.getOrPut(roomId) { Room(roomId) }?.players?.add(player)
            player.emitter.onCompletion { rooms[roomId]?.players?.remove(player) }
            player.emitter.onTimeout { rooms[roomId]?.players?.remove(player) }

            player.emitter.send(
                SseEmitter.event()
                    .name("new-player-id")
                    .data(player.id)
                    .build()
            )

            broadcastRoomToEachPlayer(roomId, "new-player")

            return player.emitter
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    class PlayerIdNotFoundException : RuntimeException() {
    }
}