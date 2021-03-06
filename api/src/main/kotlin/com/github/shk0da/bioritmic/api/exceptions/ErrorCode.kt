package com.github.shk0da.bioritmic.api.exceptions

import org.springframework.http.HttpStatus
import java.util.*

enum class ErrorCode(val code: String, val message: String, val httpCode: HttpStatus) {

    JSON_CANT_BE_PARSED("API-400.1", "JSON can't be parsed.", HttpStatus.BAD_REQUEST),
    REQUIRED_PARAMETER("API-400.2", "Parameter [\${${Constants.PARAMETER_NAME}}] value is required.", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER("API-400.3", "Parameter [\${${Constants.PARAMETER_NAME}}] value is invalid.", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER_WITH_VALUE("API-400.4", "Parameter [\${${Constants.PARAMETER_NAME}}] with requested value [\${${Constants.PARAMETER_VALUE}}] is invalid.", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER_SIZE("API-400.5", "The parameter [\${${Constants.PARAMETER_NAME}}] cannot be longer than [\${${Constants.PARAMETER_VALUE_LENGTH}}] characters.", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER_RANGE("API-400.6", "Parameter [\${${Constants.PARAMETER_NAME}}] value is invalid. Valid range of values: [\${${Constants.PARAMETER_VALUE_START}}-\${${Constants.PARAMETER_VALUE_END}}].", HttpStatus.BAD_REQUEST),
    INVALID_RECOVERY_CODE("API-400.7", "Recovery code is invalid.", HttpStatus.BAD_REQUEST),
    MANY_BOOKMARKS("API-400.8", "User has to many bookmarks.", HttpStatus.BAD_REQUEST),
    MANY_MEETINGS("API-400.8", "User has to many meetings.", HttpStatus.BAD_REQUEST),

    USER_EXISTS("API-409", "The user with this email is already registered.", HttpStatus.CONFLICT),

    INVALID_URI("API-404", "Invalid URI.", HttpStatus.NOT_FOUND),
    USER_WITH_EMAIL_NOT_FOUND("API-404", "User with email: [\${${Constants.PARAMETER_VALUE}}] not found.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("API-404", "User not found.", HttpStatus.NOT_FOUND),
    SETTINGS_NOT_FOUND("API-404", "Settings for User not found.", HttpStatus.NOT_FOUND),
    COORDINATES_NOT_FOUND("API-404", "Coordinates for User not found. Please update GIS data.", HttpStatus.NOT_FOUND),
    AUTH_NOT_FOUND("API-404", "Authorization not found.", HttpStatus.NOT_FOUND),
    IMAGE_NOT_FOUND("API-404", "Image not found.", HttpStatus.NOT_FOUND),

    USER_IS_BLOCKED("API-412", "The user blocked you.", HttpStatus.PRECONDITION_FAILED),

    API_INTERNAL_ERROR("API-500", "Unknown error.", HttpStatus.INTERNAL_SERVER_ERROR),
    API_SERVICE_UNAVAILABLE("API-503", "Service unavailable.", HttpStatus.SERVICE_UNAVAILABLE);

    object Constants {
        // parameters
        const val PARAMETER_NAME = "parameterName"
        const val PARAMETER_VALUE = "parameterValue"
        const val PARAMETER_VALUE_LENGTH = "parameterValueLength"
        const val PARAMETER_VALUE_START = "parameterValueStart"
        const val PARAMETER_VALUE_END = "parameterValueEnd"
    }

    companion object {
        fun byCode(code: String?): Optional<ErrorCode> {
            var result: ErrorCode? = null
            for (errorCode in values()) {
                if (errorCode.code == code) {
                    result = errorCode
                    break
                }
            }
            return Optional.ofNullable(result)
        }
    }

    override fun toString(): String {
        return "ErrorCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", httpCode=" + httpCode +
                '}'
    }
}