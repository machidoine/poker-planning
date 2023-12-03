package com.ben.pokerplanningservice.infra

import com.ben.pokerplanningservice.domain.model.Room
import com.ben.pokerplanningservice.domain.repository.RoomRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap


@Repository
@Profile("inmemory")
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