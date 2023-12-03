package com.ben.pokerplanningservice

import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test", "dynamodb")
class AppTest {

    @Test
    fun contextLoad() {
    }
}