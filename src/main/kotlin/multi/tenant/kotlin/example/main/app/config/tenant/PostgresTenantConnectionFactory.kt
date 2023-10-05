package multi.tenant.kotlin.example.main.app.config.tenant

import io.r2dbc.spi.ConnectionFactoryMetadata
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory
import reactor.core.publisher.Mono

class PostgresTenantConnectionFactory: AbstractRoutingConnectionFactory() {

    internal class PostgresqlConnectionFactoryMetadata private constructor() : ConnectionFactoryMetadata {
        override fun getName(): String {
            return "PostgreSQL"
        }

        companion object {
            val INSTANCE =
                PostgresqlConnectionFactoryMetadata()
        }
    }

    override fun determineCurrentLookupKey(): Mono<Any> {
        return Mono.just(TenantContext.getTenant())
    }

    override fun getMetadata(): ConnectionFactoryMetadata {
        return PostgresqlConnectionFactoryMetadata.INSTANCE
    }

}