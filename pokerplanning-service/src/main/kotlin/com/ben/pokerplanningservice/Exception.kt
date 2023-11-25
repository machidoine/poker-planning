package com.ben.pokerplanningservice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class RoomDoesNotExistException(roomId: String) : RuntimeException("The room $roomId does not exist")

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class PlayerDoesNotExistException(roomId: String, playerId: String) :
    RuntimeException("The player $playerId does not exist in the room $roomId")
