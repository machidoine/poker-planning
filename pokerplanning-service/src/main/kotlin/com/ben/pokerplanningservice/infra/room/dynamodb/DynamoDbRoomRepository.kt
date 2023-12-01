package com.ben.pokerplanningservice.infra.room.dynamodb

import com.ben.pokerplanningservice.domain.model.Room
import com.ben.pokerplanningservice.domain.repository.RoomRepository
import com.ben.pokerplanningservice.infra.room.dynamodb.entities.toDomain
import com.ben.pokerplanningservice.infra.room.dynamodb.entities.toEntity
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Profile("dynamodb")
@Repository
class DynamoDbRoomRepository(private val repo: DynamoDBRoomCrudRepository) : RoomRepository {

    override fun getRoom(roomId: String): Room? {
        return repo.findByIdOrNull(roomId)?.toDomain()
    }

    override fun saveRoom(room: Room) {
        repo.save(room.toEntity())
    }

    override fun deleteRoom(room: Room) {
        repo.deleteById(room.id)
    }

}