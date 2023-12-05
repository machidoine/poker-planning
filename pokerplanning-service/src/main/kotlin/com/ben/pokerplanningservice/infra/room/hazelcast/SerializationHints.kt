package com.ben.pokerplanningservice.infra.room.hazelcast

import com.ben.pokerplanningservice.domain.model.PlayerId
import com.ben.pokerplanningservice.infra.room.hazelcast.model.PlayerEntity
import com.ben.pokerplanningservice.infra.room.hazelcast.model.RoomEntity
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.context.annotation.Configuration


@Configuration
@RegisterReflectionForBinding(RoomEntity::class, PlayerEntity::class, PlayerId::class)
class SerializationHints