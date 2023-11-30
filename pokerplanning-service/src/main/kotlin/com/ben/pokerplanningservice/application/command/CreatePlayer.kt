package com.ben.pokerplanningservice.application.command

import com.ben.pokerplanningservice.domain.model.Player
import com.ben.pokerplanningservice.domain.model.Room
import com.ben.pokerplanningservice.domain.repository.PlayerSenderRepository
import com.ben.pokerplanningservice.domain.repository.RoomRepository
import org.springframework.stereotype.Component


@Component
class CreatePlayer(
    roomRepository: RoomRepository,
    private val playerSenderRepository: PlayerSenderRepository
) : RoomCommand<CreatePlayerRequest, Player>(roomRepository) {

    override fun doExecute(room: Room, request: CreatePlayerRequest): RoomCommandResponse<Player> {
        val player = room.createPlayer(request.name)
        val copiedRoom = room.addPlayer(player)

        playerSenderRepository.broadcastMessage(copiedRoom, "new-player")

        return RoomCommandResponse(copiedRoom, player)
    }
}

data class CreatePlayerRequest(val name: String)