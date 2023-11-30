package com.ben.pokerplanningservice.application.command

import com.ben.pokerplanningservice.domain.model.Room
import com.ben.pokerplanningservice.domain.repository.PlayerSenderRepository
import com.ben.pokerplanningservice.domain.repository.RoomRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class RemovePlayer(
    roomRepository: RoomRepository,
    private val playerSenderRepository: PlayerSenderRepository
) :
    RoomCommand<RemovePlayerRequest, Nothing>(roomRepository) {

    override fun doExecute(room: Room, request: RemovePlayerRequest): RoomCommandResponse<Nothing> {
        if (room.playerNotExist(request.playerId)) {
            return RoomCommandResponse(room)
        }

        val copiedRoom = room.removePlayer(request.playerId)

        playerSenderRepository.broadcastMessage(copiedRoom, "player-quit")

        return RoomCommandResponse(copiedRoom)
    }
}

data class RemovePlayerRequest(val playerId: UUID)