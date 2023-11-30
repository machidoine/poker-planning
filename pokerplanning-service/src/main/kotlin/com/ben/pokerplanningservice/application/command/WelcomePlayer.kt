package com.ben.pokerplanningservice.application.command

import com.ben.pokerplanningservice.domain.model.Room
import com.ben.pokerplanningservice.domain.repository.PlayerSenderRepository
import com.ben.pokerplanningservice.domain.repository.RoomRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class WelcomePlayer(
    roomRepository: RoomRepository,
    private val playerSenderRepository: PlayerSenderRepository
) :
    RoomCommand<WelcomePlayerRequest, Nothing>(roomRepository) {

    override fun doExecute(room: Room, request: WelcomePlayerRequest): RoomCommandResponse<Nothing> {
        playerSenderRepository.sendRoomTo(request.playerId, room, "new-player")

        return RoomCommandResponse(room)
    }
}

data class WelcomePlayerRequest(val playerId: UUID)