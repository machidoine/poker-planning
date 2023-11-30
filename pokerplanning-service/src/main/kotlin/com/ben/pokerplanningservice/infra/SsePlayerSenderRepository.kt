package com.ben.pokerplanningservice.infra

import com.ben.pokerplanningservice.domain.PlayerSenderRepository
import com.ben.pokerplanningservice.domain.Room
import com.ben.pokerplanningservice.exposition.SseTemplate
import com.ben.pokerplanningservice.exposition.toRoomDto
import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*

@Repository
class SsePlayerSenderRepository(private val sseTemplate: SseTemplate) : PlayerSenderRepository {
    override fun broadcastMessage(room: Room, eventName: String) {
        room.players.forEach {
            sseTemplate.send(
                it.id.privateId, SseEmitter.event()
                    .name(eventName)
                    .data(room.toRoomDto())
            )
        }

    }

    override fun sendRoomTo(playerId: UUID, room: Room, eventName: String) {
        sseTemplate.send(
            playerId, SseEmitter.event()
                .name(eventName)
                .data(room.toRoomDto())
        )
    }
}