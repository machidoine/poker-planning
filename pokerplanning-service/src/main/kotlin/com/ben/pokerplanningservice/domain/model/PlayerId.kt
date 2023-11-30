package com.ben.pokerplanningservice.domain.model

import java.util.*

data class PlayerId(
    val publicId: UUID = UUID.randomUUID(),
    val privateId: UUID = UUID.randomUUID()
)