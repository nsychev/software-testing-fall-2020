package ru.nsychev.hashback.test

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import ru.nsychev.hashback.model.SavedHash
import ru.nsychev.hashback.model.User
import ru.nsychev.hashback.repo.SavedHashRepository
import ru.nsychev.hashback.service.SavedHashService
import kotlin.test.assertEquals

@SpringJUnitConfig
@DisplayName("SavedHash Service")
class SavedHashServiceTest {
    @TestConfiguration
    class Configuration {
        @MockBean
        private lateinit var savedHashRepository: SavedHashRepository
        @Bean
        fun savedHashService() = SavedHashService()
    }

    @Autowired
    private lateinit var savedHashRepository: SavedHashRepository
    @Autowired
    private lateinit var savedHashService: SavedHashService

    @Test
    fun checkService() {
        val hash = SavedHash(1, "ab", "cd", User())

        given(savedHashRepository.findByHash("ab")).willReturn(hash)
        assertEquals(hash, savedHashService.retrieve("ab"))
    }
}
