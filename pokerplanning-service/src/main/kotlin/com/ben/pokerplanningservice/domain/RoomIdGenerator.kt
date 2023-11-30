package com.ben.pokerplanningservice.domain

import java.security.SecureRandom
import java.util.*

class RoomIdGenerator {
    private val random: SecureRandom = SecureRandom()
    private val encoder = Base64.getUrlEncoder().withoutPadding()

    fun generate(): String {
        val buffer = ByteArray(20)
        random.nextBytes(buffer)
        return encoder.encodeToString(buffer)
    }
}