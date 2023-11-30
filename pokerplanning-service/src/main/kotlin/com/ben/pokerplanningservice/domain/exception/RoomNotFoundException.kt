package com.ben.pokerplanningservice.domain.exception

class RoomNotFoundException(roomId: String) : RuntimeException("the room $roomId is not found")