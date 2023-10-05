package multi.tenant.kotlin.example.main.app.dto

import java.time.LocalDateTime

data class Task(
    val taskId: Int,
    val userId: Int,
    val title: String,
    val description: String,
    val dueDate: LocalDateTime,
    val isCompleted: Boolean,
    val category: String
)