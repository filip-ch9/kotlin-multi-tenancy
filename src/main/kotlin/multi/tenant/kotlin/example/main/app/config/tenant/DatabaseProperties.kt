package multi.tenant.kotlin.example.main.app.config.tenant

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class DatabaseProperties(
    val db: Map<String, Database>
)

data class Database(
    val url: String,
    val requireSsl: Boolean = true,
    val pool: Pool = Pool()
)

data class Pool(
    val initialSize: Int = 5,
    val maxSize: Int = 20,
    val maxIdleTime: Duration = Duration.ofMinutes(30),
    val maxCreateConnectionTime: Duration = Duration.ofNanos(Long.MAX_VALUE),
    val maxAcquireTime: Duration = Duration.ofNanos(Long.MAX_VALUE),
    val maxLifeTime: Duration = Duration.ofNanos(Long.MAX_VALUE)
)