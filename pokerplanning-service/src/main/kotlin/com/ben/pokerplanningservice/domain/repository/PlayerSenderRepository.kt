package com.ben.pokerplanningservice.domain.repository

import com.ben.pokerplanningservice.domain.model.Room
import java.util.*

interface PlayerSenderRepository {
    fun broadcastMessage(room: Room, eventName:String)

    fun sendRoomTo(playerId: UUID, room: Room, eventName: String)
}