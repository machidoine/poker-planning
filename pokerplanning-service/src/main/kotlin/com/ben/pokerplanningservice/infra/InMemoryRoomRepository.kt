package com.ben.pokerplanningservice.infra

import com.ben.pokerplanningservice.domain.Room
import com.ben.pokerplanningservice.domain.RoomRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryRoomRepository : RoomRepository {
    val map = ConcurrentHashMap<String, Room>()

    override fun getRoom(roomId: String): Room? = map[roomId]

    override fun saveRoom(room: Room) {
        map[room.id] = room.copy(lastAccessDate = Instant.now())
    }

    override fun deleteRoom(room: Room) {
        map.remove(room.id)
    }
}