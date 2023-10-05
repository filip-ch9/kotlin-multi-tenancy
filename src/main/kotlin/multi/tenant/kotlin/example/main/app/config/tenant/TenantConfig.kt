package multi.tenant.kotlin.example.main.app.config.tenant

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy
import org.springframework.data.r2dbc.core.R2dbcEntityOperations
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.dialect.DialectResolver
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.reactive.TransactionalOperator
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(DatabaseProperties::class)
@EnableR2dbcRepositories(basePackages = ["multi.tenant.kotlin.example.main.app.repository"],
    entityOperationsRef = "tenantEntityTemplate")
class TenantConfig(databaseProperties: DatabaseProperties) {

    val tenantConnectionFactoriesMap: Map<String, ConnectionFactory> =
        databaseProperties.db
            .filter { it.value.url.isNotBlank() }
            .mapValues {
                ConnectionPool(
                    ConnectionPoolConfiguration.builder(ConnectionFactories.get("r2dbc:${it.value.url}${getSslPostfix(it.value.requireSsl)}"))
                        .name(it.key)
                        .initialSize(it.value.pool.initialSize)
                        .maxSize(it.value.pool.maxSize)
                        .maxLifeTime(it.value.pool.maxLifeTime)
                        .maxIdleTime(it.value.pool.maxIdleTime)
                        .maxCreateConnectionTime(it.value.pool.maxCreateConnectionTime)
                        .maxAcquireTime(it.value.pool.maxAcquireTime)
                        .build()
                )
            }

    fun createMasterDataSource(): DataSource {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver") // Replace with your database driver
            .url("jdbc:postgresql://localhost:5432/master_pet_project")
            .username("master_pet")
            .password("another_password")
            .build()
    }

    fun createTenantDataSource(): DataSource {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver") // Replace with your database driver
            .url("jdbc:postgresql://localhost:5432/tenant_pet_project")
            .username("tenant_pet")
            .password("my_password")
            .build()
    }

    private fun getSslPostfix(sslRequired: Boolean): String {
        if (sslRequired) {
            return "?sslMode=require"
        }
        return ""
    }
    @Bean
    @Qualifier(value = "masterConnectionFactory")
    fun masterConnectionFactory(): ConnectionFactory {
        return tenantConnectionFactoriesMap.filter { it.key == "master" }.firstNotNullOf { it.value }
    }

    @Bean
    @Qualifier("tenantConnectionFactory")
    fun tenantConnectionFactory(): ConnectionFactory {
        val tenantConnectionFactory = PostgresTenantConnectionFactory()
        tenantConnectionFactory.setDefaultTargetConnectionFactory(masterConnectionFactory())
        tenantConnectionFactory.setTargetConnectionFactories(tenantConnectionFactoriesMap.filter { it.key != "master" })
        return tenantConnectionFactory
    }

    @Bean
    fun tenantEntityTemplate(@Qualifier("tenantConnectionFactory") connectionFactory: ConnectionFactory): R2dbcEntityOperations {
        val dialect = DialectResolver.getDialect(connectionFactory)
        val strategy = DefaultReactiveDataAccessStrategy(dialect)
        val databaseClient: DatabaseClient = DatabaseClient.builder()
            .connectionFactory(connectionFactory)
            .bindMarkers(dialect.bindMarkersFactory)
            .build()
        return R2dbcEntityTemplate(databaseClient, strategy)
    }

    @Bean
    fun transactionalOperator(transactionManager: ReactiveTransactionManager): TransactionalOperator {
        return TransactionalOperator.create(transactionManager)
    }

    @Bean
    fun transactionManager(@Qualifier("tenantConnectionFactory") connectionFactory: ConnectionFactory): ReactiveTransactionManager {
       return  R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    fun liquibaseMaster(): SpringLiquibase {
        val liquibase = SpringLiquibase()
        liquibase.dataSource = createMasterDataSource()
        liquibase.changeLog = "classpath:db/changelog/master.yaml"
        liquibase.defaultSchema = "public"
        liquibase.databaseChangeLogTable =  "master_pet_project_databasechangelog"
        liquibase.databaseChangeLogLockTable = "master_pet_project_databasechangeloglock"
        return liquibase
    }

    @Bean
    fun liquibaseTenant(): SpringLiquibase {
        val liquibase = SpringLiquibase()
        liquibase.dataSource = createTenantDataSource()
        liquibase.changeLog = "classpath:db/changelog/master.yaml"
        liquibase.defaultSchema = "public"
        liquibase.databaseChangeLogTable =  "tenant_pet_project_databasechangelog"
        liquibase.databaseChangeLogLockTable = "tenant_pet_project_databasechangeloglock"
        return liquibase
    }

}