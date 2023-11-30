package com.ben.pokerplanningservice.application.command

import com.ben.pokerplanningservice.domain.model.Room
import com.ben.pokerplanningservice.domain.repository.RoomRepository
import org.springframework.stereotype.Component

@Component
class CreateRoom(private val roomRepository: RoomRepository) {
    fun execute(): Room {
        val room = Room.createRoom()

        roomRepository.saveRoom(room)

        return room
    }
}