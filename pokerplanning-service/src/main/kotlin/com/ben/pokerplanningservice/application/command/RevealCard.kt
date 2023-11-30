package com.ben.pokerplanningservice.application.command

import com.ben.pokerplanningservice.domain.PlayerSenderRepository
import com.ben.pokerplanningservice.domain.Room
import com.ben.pokerplanningservice.domain.RoomRepository
import org.springframework.stereotype.Component

@Component
class RevealCard(
    roomRepository: RoomRepository,
    private val playerSenderRepository: PlayerSenderRepository
) :
    RoomCommand<Nothing?, Nothing>(roomRepository) {

    override fun doExecute(room: Room, request: Nothing?): RoomCommandResponse<Nothing> {
        val revealedRoom = room.revealCard()

        playerSenderRepository.broadcastMessage(revealedRoom, "reveal-card")

        return RoomCommandResponse(revealedRoom)
    }
}