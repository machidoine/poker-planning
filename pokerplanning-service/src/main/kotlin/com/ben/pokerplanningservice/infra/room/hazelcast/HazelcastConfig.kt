package com.ben.pokerplanningservice.infra.room.hazelcast

import com.hazelcast.config.Config
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
@EnableConfigurationProperties(HazelcastConfig::class)
@ConfigurationProperties(prefix = "hazelcast", ignoreUnknownFields = false)
@Primary
class HazelcastConfig: Config()
