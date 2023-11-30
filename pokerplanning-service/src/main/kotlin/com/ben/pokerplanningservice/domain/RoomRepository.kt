package com.ben.pokerplanningservice.domain

interface RoomRepository {
    fun getRoom(roomId: String): Room?
    fun saveRoom(room: Room)

    fun deleteRoom(room: Room)
}
