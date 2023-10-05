package multi.tenant.kotlin.example.main.app.dto

data class ErrorResponse (
    val serviceMetadata: String,
    val errorCode: Int,
    val errorTitle: String,
    val errorMessage: String? = null,
    val errorCause: String? = null,
)