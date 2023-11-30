package com.ben.pokerplanningservice.exposition

import com.ben.pokerplanningservice.application.command.*
import com.ben.pokerplanningservice.application.query.GetPlayer
import com.ben.pokerplanningservice.application.query.GetRoom
import com.ben.pokerplanningservice.domain.exception.PlayerDoesNotExistException
import com.ben.pokerplanningservice.domain.exception.RoomNotFoundException
import com.ben.pokerplanningservice.domain.model.PlayerId
import com.ben.pokerplanningservice.exposition.dto.RoomDTO
import com.ben.pokerplanningservice.exposition.dto.toRoomDto
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*

@Controller
@RequestMapping("api/rooms")
class RoomController(
    private val sseTemplate: SseTemplate,
    private val createRoom: CreateRoom,
    private val revealCard: RevealCard,
    private val hideCard: HideCard,
    private val reset: Reset,
    private val playCard: PlayCard,
    private val removePlayer: RemovePlayer,
    private val createPlayer: CreatePlayer,
    private val getPlayer: GetPlayer,
    private val getRoom: GetRoom,
    private val welcomePlayer: WelcomePlayer
) {

    @PostMapping("/create")
    @ResponseBody
    fun createRoom(): RoomDTO {
        return createRoom.execute().toRoomDto()
    }

    @GetMapping("/{roomId}")
    @ResponseBody
    fun getRoom(@PathVariable roomId: String): RoomDTO = getRoom.execute(roomId)?.toRoomDto()
        ?: throw RoomNotFoundException(roomId)

    @PostMapping("/{roomId}/reveal-card")
    @ResponseBody
    fun revealCard(@PathVariable roomId: String) {
        revealCard.execute(roomId, null)
    }

    @PostMapping("/{roomId}/hide-card")
    @ResponseBody
    fun hideCard(@PathVariable roomId: String) {
        hideCard.execute(roomId, null)
    }

    @PostMapping("/{roomId}/reset")
    @ResponseBody
    fun reset(@PathVariable roomId: String) {
        reset.execute(roomId, null)
    }

    @PostMapping("/{roomId}/player/{playerId}/play-card")
    @ResponseBody
    fun playCard(@PathVariable roomId: String, @PathVariable playerId: UUID, @RequestBody card: Int) {
        playCard.execute(roomId, PlayCardRequest(playerId, card))
    }

    @PostMapping("/{roomId}/player/{playerId}/quit")
    @ResponseBody
    fun playerQuit(@PathVariable roomId: String, @PathVariable playerId: UUID) {
        removePlayer.execute(roomId, RemovePlayerRequest(playerId))
        sseTemplate.delete(playerId)
    }

    @GetMapping("/{roomId}/player/{playerId}/sse")
    @ResponseBody
    fun register(
        @PathVariable roomId: String,
        @PathVariable playerId: UUID
    ): SseEmitter {
        getPlayer.execute(roomId, playerId)

        val emitter = sseTemplate.create(playerId)

        emitter.onCompletion { this.playerQuit(roomId, playerId) }
        emitter.onTimeout { this.playerQuit(roomId, playerId) }

        welcomePlayer.execute(roomId, WelcomePlayerRequest(playerId))

        return emitter
    }

    @PostMapping("/{roomId}/player/create")
    @ResponseBody
    fun createPlayer(
        @PathVariable roomId: String,
        @RequestParam name: String
    ): PlayerId = createPlayer.execute(roomId, CreatePlayerRequest(name))
        .extra!!.id

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(PlayerDoesNotExistException::class, RoomNotFoundException::class)
    fun translateException() {

    }
}