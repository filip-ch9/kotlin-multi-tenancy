package multi.tenant.kotlin.example.main.app.repository

import multi.tenant.kotlin.example.main.app.entity.UserVO
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository: CoroutineCrudRepository<UserVO, Int>