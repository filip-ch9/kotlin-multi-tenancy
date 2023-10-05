package multi.tenant.kotlin.example.main.app.config.tenant

import multi.tenant.kotlin.example.main.app.exception.TenantFilterException
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class TenantFilter : WebFilter {

    private val tenantHeader = "X-TenantId"

    override fun filter(
        serverWebExchange: ServerWebExchange,
        webFilterChain: WebFilterChain
    ): Mono<Void> {
        val tenant = serverWebExchange.request.headers[tenantHeader]

        if (tenant.isNullOrEmpty()) {
            setTenant(TenantContext.DEFAULT)
        } else {
            setTenant(tenant.first())
        }
        return webFilterChain.filter(serverWebExchange)
    }

    private fun setTenant(tenant: String) {
        try {
            TenantContext.set(tenant)
        } catch (e: Exception) {
            throw TenantFilterException("Something went wrong while setting the tenant: $tenant, with error message: ${e.message}")
        }
    }
}