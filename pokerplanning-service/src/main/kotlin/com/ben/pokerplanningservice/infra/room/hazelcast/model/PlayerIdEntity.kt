package com.ben.pokerplanningservice.infra.room.hazelcast.model

import com.ben.pokerplanningservice.domain.model.PlayerId
import java.util.*

data class PlayerIdEntity(
    val publicId: String = "",
    val privateId: String = ""
)

fun PlayerIdEntity.toDomain() = PlayerId(
    publicId = UUID.fromString(publicId),
    privateId = UUID.fromString(privateId)
)

fun PlayerId.toEntity() = PlayerIdEntity(
    publicId = publicId.toString(),
    privateId = privateId.toString()
)