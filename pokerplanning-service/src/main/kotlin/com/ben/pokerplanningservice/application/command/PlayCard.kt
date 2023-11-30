package com.ben.pokerplanningservice.application.command

import com.ben.pokerplanningservice.domain.PlayerSenderRepository
import com.ben.pokerplanningservice.domain.Room
import com.ben.pokerplanningservice.domain.RoomRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class PlayCard(
    roomRepository: RoomRepository,
    private val playerSenderRepository: PlayerSenderRepository
) :
    RoomCommand<PlayCardRequest, Nothing>(roomRepository) {

    override fun doExecute(room: Room, request: PlayCardRequest): RoomCommandResponse<Nothing> {
        if (room.playerNotExist(request.playerId)) {
            return RoomCommandResponse(room)
        }

        val copiedRoom = room.playCard(request)

        playerSenderRepository.broadcastMessage(copiedRoom, "play-card")

        return RoomCommandResponse(copiedRoom)
    }


}

data class PlayCardRequest(val playerId: UUID, val card: Int)