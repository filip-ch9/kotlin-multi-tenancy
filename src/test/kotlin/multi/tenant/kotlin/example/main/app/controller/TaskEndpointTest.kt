package multi.tenant.kotlin.example.main.app.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import multi.tenant.kotlin.example.main.app.converter.toTask
import multi.tenant.kotlin.example.main.app.dto.Task
import multi.tenant.kotlin.example.main.app.entity.TaskVO
import multi.tenant.kotlin.example.main.app.services.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [TaskEndpointTest::class])
class TaskEndpointTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var userService: UserService

    @Test
    fun `should get task for given user`() {
        val listOfTasks = toTask(taskList())

        coEvery {
            userService.getTasksForUser(2)
        } returns listOfTasks

        val result = webTestClient.get()
            .uri("/task/users/2")
            .accept(MediaType.APPLICATION_JSON)
            .header("X-TenantId", "master")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Task::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(result, listOfTasks)
    }

    companion object {
        fun taskList(): List<TaskVO> {
            return listOf(
                TaskVO(1, 2, "Title", "Description", LocalDateTime.now(), false, "Sport"),
                TaskVO(2, 2, "Title1", "Description1", LocalDateTime.now(), false, "Movie"),
                TaskVO(3, 2, "Title2", "Description2", LocalDateTime.now(), false, "Today"),
                TaskVO(4, 2, "Title3", "Description3", LocalDateTime.now(), false, "Personal"),
                TaskVO(5, 2, "Title4", "Description4", LocalDateTime.now(), false, "Sport"),
            )
        }
    }
}