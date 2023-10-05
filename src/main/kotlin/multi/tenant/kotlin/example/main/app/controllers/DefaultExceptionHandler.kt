package multi.tenant.kotlin.example.main.app.controllers

import mu.KotlinLogging
import multi.tenant.kotlin.example.main.app.dto.ErrorResponse
import multi.tenant.kotlin.example.main.app.exception.InvalidInputException
import multi.tenant.kotlin.example.main.app.util.ServiceMetadata
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val logger = KotlinLogging.logger {}

@ControllerAdvice
class DefaultExceptionHandler {

    @ExceptionHandler
    fun handleIncorrectResultSizeDataAccessException(e: IncorrectResultSizeDataAccessException): ResponseEntity<ErrorResponse> {
        logger.error(e.message, e)
        val errorResponse = ErrorResponse(
            ServiceMetadata.NAME,
            409,
            "Data result size incorrect",
            e.message,
            e.cause?.message
        )
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(errorResponse)
    }

    @ExceptionHandler
    fun handleDataIntegrityViolationException(e: DataIntegrityViolationException): ResponseEntity<ErrorResponse> {
        logger.error(e.message, e)
        val errorResponse = ErrorResponse(
            ServiceMetadata.NAME,
            400,
            "Bad request: please check input data",
            e.message,
            e.cause?.message
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }

    @ExceptionHandler
    fun handleAnyOtherException(e: Throwable): ResponseEntity<ErrorResponse> {
        logger.error(e.message, e)
        val errorResponse = ErrorResponse(
            ServiceMetadata.NAME,
            500,
            "Internal server error",
            e.message,
            e.cause?.message
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse)
    }

    @ExceptionHandler
    fun handleSearchUnexpectedFieldTypeException(e: InvalidInputException): ResponseEntity<ErrorResponse> {
        logger.error(e.message, e)
        val errorResponse = ErrorResponse(
            ServiceMetadata.NAME,
            400,
            "Invalid input exception, please check provided input params or body",
            e.message,
            e.cause?.message
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }

}
