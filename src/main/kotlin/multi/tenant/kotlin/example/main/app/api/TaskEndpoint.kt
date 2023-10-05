package multi.tenant.kotlin.example.main.app.api

import io.swagger.v3.oas.annotations.Operation
import multi.tenant.kotlin.example.main.app.dto.ErrorResponse
import multi.tenant.kotlin.example.main.app.dto.Task
import multi.tenant.kotlin.example.main.app.exception.StatusNotFoundException
import multi.tenant.kotlin.example.main.app.util.ServiceMetadata
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/task")
interface TaskEndpoint {

    @Operation(summary = "Get tasks for user",
        description = "Get all tasks in the database for given user id")
    @GetMapping("/users/{user-id}")
    suspend fun getTasksForUser(
        @PathVariable("user-id") userId: Int): List<Task>

    @ExceptionHandler
    fun handleOpexNotFound(e: StatusNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            ServiceMetadata.NAME,
            404,
            "Unable to fetch tasks by provided user",
            e.message,
            e.cause?.message
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(errorResponse)
    }
}