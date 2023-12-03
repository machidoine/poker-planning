package com.ben.pokerplanningservice.exposition

import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class SseTemplate {
    val map = ConcurrentHashMap<UUID, SseEmitter>()

    fun send(id: UUID, builder: SseEmitter.SseEventBuilder) {
        try {
            map[id]?.send(builder.build())
        } catch (e: IOException) {
            map[id]?.complete()
        }
    }

    fun create(id: UUID): SseEmitter {
        val emitter = SseEmitter(Long.MAX_VALUE)
        map.put(id, emitter)
        return emitter
    }

    fun delete(id: UUID) {
        map.remove(id)
    }
}