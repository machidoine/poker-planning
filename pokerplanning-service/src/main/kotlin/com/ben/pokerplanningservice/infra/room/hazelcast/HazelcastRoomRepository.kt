package com.ben.pokerplanningservice.infra.room.hazelcast

import com.ben.pokerplanningservice.domain.model.Room
import com.ben.pokerplanningservice.domain.repository.RoomRepository
import com.ben.pokerplanningservice.infra.room.hazelcast.model.RoomEntity
import com.ben.pokerplanningservice.infra.room.hazelcast.model.toDomain
import com.ben.pokerplanningservice.infra.room.hazelcast.model.toEntity
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.map.IMap
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.time.Instant


@Repository
@Profile("hazelcast")
class HazelcastRoomRepository(private val hazelcastInstance: HazelcastInstance) : RoomRepository {
    private fun getMap(): IMap<String, RoomEntity> {
        return hazelcastInstance.getMap("room")
    }

    override fun getRoom(roomId: String): Room? = getMap()[roomId]?.toDomain()

    override fun saveRoom(room: Room) {
        getMap()[room.id] = room.copy(lastAccessDate = Instant.now()).toEntity()
    }

    override fun deleteRoom(room: Room) {
        getMap().remove(room.id)
    }

}
