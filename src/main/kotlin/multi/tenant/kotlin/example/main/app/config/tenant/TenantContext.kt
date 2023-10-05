package multi.tenant.kotlin.example.main.app.config.tenant

import kotlinx.coroutines.ThreadContextElement
import kotlinx.coroutines.asContextElement
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
object TenantContext {
    const val DEFAULT: String = "master"

    private val currentTenant = InheritableThreadLocal<String?>()

    fun getTenant() : String {
        val current = currentTenant.get()
        if (current == null) {
            logger.error { "verify_multi_tenancy: tenant is not read from thread, use the default master" }
        }
        return current ?: DEFAULT
    }

    @Synchronized
    fun set(tenantId : String) {
        val currentThread = Thread.currentThread()
        logger.info { "verify_multi_tenancy: setTenantId, currentThread:$currentThread, tenantId: $tenantId" }
        currentTenant.set(tenantId)
    }

    fun asContextElement(): ThreadContextElement<String?> {
        logger.debug("[d] asContextElement ${getTenant()}")
        return currentTenant.asContextElement(getTenant())
    }
}