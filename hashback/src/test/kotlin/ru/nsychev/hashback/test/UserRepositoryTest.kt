package ru.nsychev.hashback.test

import io.qameta.allure.Feature
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import ru.nsychev.hashback.model.User
import ru.nsychev.hashback.repo.UserRepository
import kotlin.test.assertEquals

@Container
private val container: PostgreSQLContainer<*> =
    PostgreSQLContainer<PostgreSQLContainer<*>>("postgres:latest")
        .withDatabaseName("hashback")
        .withUsername("hashback")
        .withPassword("hashback")

class UserRepositoryTestInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        container.start()
        TestPropertyValues.of(
            "spring.datasource.url=${container.getJdbcUrl()}",
            "spring.datasource.username=${container.getUsername()}",
            "spring.datasource.password=${container.getPassword()}"
        ).applyTo(configurableApplicationContext.environment)
    }
}

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = [UserRepositoryTestInitializer::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("UserRepository")
class UserRepositoryTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @AfterAll
    fun stop() {
        container.stop()
    }

    @BeforeEach
    fun removeAll() {
        userRepository.deleteAll()
    }

    @Test
    @Feature("Users")
    @DisplayName("Create user")
    fun addUser() {
        userRepository.save(User(-1, "hello", listOf()))
        Assertions.assertEquals(userRepository.count(), 1)
    }

    @Test
    @Feature("Users")
    @DisplayName("Find user by name")
    fun findUser() {
        val expectedUser = userRepository.save(User(-1, "hello", listOf()))
        val actualUser = userRepository.findByUsername("hello")
        Assertions.assertEquals(expectedUser.id, actualUser?.id)
    }
}
