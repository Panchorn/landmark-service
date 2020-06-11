package com.nonpcn.asciidocumentation.landmarkservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Any?> {
        return ResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

}