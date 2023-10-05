package multi.tenant.kotlin.example.main.app.controllers

import multi.tenant.kotlin.example.main.app.api.TaskEndpoint
import multi.tenant.kotlin.example.main.app.dto.Task
import multi.tenant.kotlin.example.main.app.services.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController(private val userService: UserService) : TaskEndpoint {

    override suspend fun getTasksForUser(userId: Int): List<Task> =
        userService.getTasksForUser(userId)
}