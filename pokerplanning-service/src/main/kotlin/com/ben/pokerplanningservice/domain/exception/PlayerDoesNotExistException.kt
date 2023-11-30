package com.ben.pokerplanningservice.domain.exception

import java.util.*

class PlayerDoesNotExistException(roomId: String, playerId: UUID) :
    RuntimeException("The player $playerId does not exist in the room $roomId")