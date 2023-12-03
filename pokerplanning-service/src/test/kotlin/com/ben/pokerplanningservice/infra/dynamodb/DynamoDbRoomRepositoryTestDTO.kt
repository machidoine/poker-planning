package com.ben.pokerplanningservice.infra.dynamodb

import com.ben.pokerplanningservice.domain.model.Player
import com.ben.pokerplanningservice.domain.model.Room
import com.ben.pokerplanningservice.domain.repository.RoomRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.InstanceOfAssertFactories
import org.assertj.core.api.InstanceOfAssertFactory
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@ActiveProfiles("test", "dynamodb")
@Testcontainers
class DynamoDbRoomRepositoryTestDTO(@Autowired private val repo: RoomRepository) {

    companion object {
        @DynamicPropertySource
        @JvmStatic
        fun dynamoDbProperties(registry: DynamicPropertyRegistry) {
            registry.add("amazon.aws.dbEndpoint") { "http://localhost:" + genericContainer.firstMappedPort }
            registry.add("amazon.aws.accessKey") { "key" }
            registry.add("amazon.aws.secretKey") { "secret" }
        }

        @Container
        @JvmStatic
        var genericContainer = GenericContainer(
            DockerImageName.parse("amazon/dynamodb-local")
        ).withExposedPorts(8000)
    }

    @Test
    fun emptyRoomCreation() {
        repo.saveRoom(Room("1"))
        val room = repo.getRoom("1")

        Assertions.assertThat(room).isNotNull
    }

    @Test
    fun roomUpdate() {
        repo.saveRoom(Room("2"))
        repo.saveRoom(Room("2", cardRevealed = true))
        val room = repo.getRoom("2")

        Assertions.assertThat(room!!)
            .extracting(Room::cardRevealed)
            .isEqualTo(true)
    }

    @Test
    fun savePlayer() {
        repo.saveRoom(Room("3"))
        repo.saveRoom(Room("3", players = listOf(Player("test"))))
        val room = repo.getRoom("3")

        Assertions.assertThat(room!!)
            .extracting { it?.players }
            //.asInstanceOf(InstanceOfAssertFactories.list())

            .isEqualTo(true)
    }

}
