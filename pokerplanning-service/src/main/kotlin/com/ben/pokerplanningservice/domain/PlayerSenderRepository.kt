package com.ben.pokerplanningservice.domain

import java.util.*

interface PlayerSenderRepository {
    fun broadcastMessage(room: Room, eventName:String)

    fun sendRoomTo(playerId: UUID, room: Room, eventName: String)
}