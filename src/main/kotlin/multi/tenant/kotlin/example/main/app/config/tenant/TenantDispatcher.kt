package multi.tenant.kotlin.example.main.app.config.tenant

import kotlinx.coroutines.Dispatchers
import org.springframework.stereotype.Component
import kotlin.coroutines.CoroutineContext

@Component
object TenantDispatcher{
    val IO: CoroutineContext get() = Dispatchers.IO + TenantContext.asContextElement()
}