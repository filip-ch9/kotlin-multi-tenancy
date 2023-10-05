package multi.tenant.kotlin.example.main.app.services

import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.toList
import mu.KotlinLogging
import multi.tenant.kotlin.example.main.app.config.tenant.TenantDispatcher
import multi.tenant.kotlin.example.main.app.converter.toTask
import multi.tenant.kotlin.example.main.app.dto.Task
import multi.tenant.kotlin.example.main.app.repository.TaskRepository
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class UserService(
    private val taskRepository: TaskRepository) {

    suspend fun getTasksForUser(userId: Int): List<Task> {
        val resultList = taskRepository.getTasksForUser(userId)
            .flowOn(TenantDispatcher.IO)
            .toList()
        logger.info { "Found ${resultList.size} tasks for user $userId" }
        return toTask(resultList)
    }

}
