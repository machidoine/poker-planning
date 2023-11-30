package com.ben.pokerplanningservice.application.query

import com.ben.pokerplanningservice.domain.Player
import com.ben.pokerplanningservice.domain.RoomRepository
import com.ben.pokerplanningservice.domain.exception.RoomNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class GetPlayer(private val roomRepository: RoomRepository) {
    fun execute(roomId: String, playerId: UUID): Player {
        return (roomRepository.getRoom(roomId) ?: throw RoomNotFoundException(roomId))
            .getPlayer(playerId)
    }
}