package multi.tenant.kotlin.example.main.app.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("tasks")
data class TaskVO (
    @Id
    val taskId: Int,
    val userId: Int,
    val title: String,
    val description: String,
    val dueDate: LocalDateTime,
    val isCompleted: Boolean,
    val category: String,
)