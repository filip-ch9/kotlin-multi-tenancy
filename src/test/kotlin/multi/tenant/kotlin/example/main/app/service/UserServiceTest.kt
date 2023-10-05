package multi.tenant.kotlin.example.main.app.service

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import multi.tenant.kotlin.example.main.app.controller.TaskEndpointTest.Companion.taskList
import multi.tenant.kotlin.example.main.app.converter.toTask
import multi.tenant.kotlin.example.main.app.repository.TaskRepository
import multi.tenant.kotlin.example.main.app.services.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserServiceTest {

    @MockK
    lateinit var taskRepository: TaskRepository

    @InjectMockKs
    lateinit var userService: UserService

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
        userService = UserService(taskRepository)
    }

    @Test
    fun `should get tasks for user from database`() {
        val listOfTasks = taskList().asFlow()

        coEvery {
            taskRepository.getTasksForUser(2)
        } returns listOfTasks

        val res = runBlocking {
            userService.getTasksForUser(2)
        }

        Assertions.assertEquals(res, toTask(taskList()))

        coVerify { taskRepository.getTasksForUser(2) }
    }

}