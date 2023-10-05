package multi.tenant.kotlin.example.main.app.repository

import kotlinx.coroutines.flow.Flow
import multi.tenant.kotlin.example.main.app.entity.TaskVO
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TaskRepository: CoroutineCrudRepository<TaskVO, Int> {

    @Query("""
        SELECT * FROM tasks
        WHERE user_id = :userId
    """)
    fun getTasksForUser(userId: Int): Flow<TaskVO>
}