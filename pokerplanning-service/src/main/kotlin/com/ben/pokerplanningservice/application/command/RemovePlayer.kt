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

// {"id":"gIumeZ1KgrLkPP9NxpkQgyz8tkg","players":[{"name":"tete","card":null,"hasPlayed":true,"publicId":"144dc7fa-e152-47c8-b468-4b9fec5fc4ce"}],"cardRevealed":false}