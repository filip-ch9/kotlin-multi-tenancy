package multi.tenant.kotlin.example.main.app.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class UserVO(
    @Id
    val userId: Int,
    val username: String,
    val password: String,
    val email: String
)