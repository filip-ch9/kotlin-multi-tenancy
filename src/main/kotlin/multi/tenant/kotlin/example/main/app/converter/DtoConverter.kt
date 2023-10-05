package multi.tenant.kotlin.example.main.app.converter

import mu.KotlinLogging
import multi.tenant.kotlin.example.main.app.dto.Task
import multi.tenant.kotlin.example.main.app.entity.TaskVO

private val logger = KotlinLogging.logger {}

fun toTask(tasks: List<TaskVO>): List<Task> {
    return tasks.map {
        Task(
            taskId = it.taskId,
            userId = it.userId,
            title = it.title,
            description = it.description,
            dueDate = it.dueDate,
            isCompleted = it.isCompleted,
            category = it.category
        )
    }
}