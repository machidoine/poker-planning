package com.ben.pokerplanningservice.application.command

import com.ben.pokerplanningservice.domain.exception.RoomNotFoundException
import com.ben.pokerplanningservice.domain.model.Room
import com.ben.pokerplanningservice.domain.repository.RoomRepository

abstract class RoomCommand<P, R>(private val roomRepository: RoomRepository) {
    fun execute(roomId: String, request: P): RoomCommandResponse<R> {
        val response = roomRepository.getRoom(roomId)
            ?.let { doExecute(it, request) }
            ?: throw RoomNotFoundException(roomId)

        roomRepository.saveRoom(response.room)

        return response
    }

    protected abstract fun doExecute(room: Room, request: P): RoomCommandResponse<R>
}

data class RoomCommandResponse<R>(val room: Room, val extra: R? = null)
