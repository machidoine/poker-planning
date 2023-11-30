package com.ben.pokerplanningservice.application.command

import com.ben.pokerplanningservice.domain.PlayerSenderRepository
import com.ben.pokerplanningservice.domain.Room
import com.ben.pokerplanningservice.domain.RoomRepository
import org.springframework.stereotype.Component

@Component
class Reset(
    roomRepository: RoomRepository,
    private val playerSenderRepository: PlayerSenderRepository
) :
    RoomCommand<Nothing?, Nothing>(roomRepository) {

    override fun doExecute(room: Room, request: Nothing?): RoomCommandResponse<Nothing> {
        val resettedRoom = room.reset()

        playerSenderRepository.broadcastMessage(resettedRoom, "reset")

        return RoomCommandResponse(resettedRoom)
    }
}