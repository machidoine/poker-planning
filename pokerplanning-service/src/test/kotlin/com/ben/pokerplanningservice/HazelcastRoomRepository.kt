package com.ben.pokerplanningservice

import com.ben.pokerplanningservice.domain.model.Player
import com.ben.pokerplanningservice.domain.model.Room
import com.ben.pokerplanningservice.domain.repository.RoomRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("hazelcast")
class HazelcastRoomRepository(@Autowired private val repo: RoomRepository) {

    @Test
    fun emptyRoomCreation() {
        repo.saveRoom(Room("1"))
        val room = repo.getRoom("1")

        assertThat(room).isNotNull
    }

    @Test
    fun roomUpdate() {
        repo.saveRoom(Room("2"))
        repo.saveRoom(Room("2", cardRevealed = true))
        val room = repo.getRoom("2")

        assertThat(room!!)
            .extracting(Room::cardRevealed)
            .isEqualTo(true)
    }

    @Test
    fun savePlayer() {
        repo.saveRoom(Room("3"))
        val player = Player("test")
        repo.saveRoom(Room("3", players = listOf(player)))
        val room = repo.getRoom("3")

        assertThat(room?.players)
            .containsExactlyInAnyOrder(player)
    }

}
