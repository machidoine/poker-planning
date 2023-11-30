package com.ben.pokerplanningservice.domain.repository

import com.ben.pokerplanningservice.domain.model.Room

interface RoomRepository {
    fun getRoom(roomId: String): Room?
    fun saveRoom(room: Room)

    fun deleteRoom(room: Room)
}
