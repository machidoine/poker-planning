package com.ben.pokerplanningservice.application.query

import com.ben.pokerplanningservice.domain.Room
import com.ben.pokerplanningservice.domain.RoomRepository
import org.springframework.stereotype.Component

@Component
class GetRoom(private val roomRepository: RoomRepository) {
    fun execute(roomId: String): Room? {
        return roomRepository.getRoom(roomId)
    }
}