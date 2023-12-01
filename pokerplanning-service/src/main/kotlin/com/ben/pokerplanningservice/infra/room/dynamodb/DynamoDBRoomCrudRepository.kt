package com.ben.pokerplanningservice.infra.room.dynamodb

import com.ben.pokerplanningservice.infra.room.dynamodb.entities.RoomEntity
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface DynamoDBRoomCrudRepository : CrudRepository<RoomEntity, String>