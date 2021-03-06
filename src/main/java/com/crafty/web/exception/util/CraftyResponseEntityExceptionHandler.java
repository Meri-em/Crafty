package com.crafty.web.exception.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.crafty.dto.ErrorResponse;
import com.crafty.web.exception.BadRequestException;
import com.crafty.web.exception.ConflictException;
import com.crafty.web.exception.ForbiddenException;
import com.crafty.web.exception.NotFoundException;
import com.crafty.web.exception.UnauthorizedException;
import com.crafty.web.exception.UnprocessableEntityException;


@ControllerAdvice
public class CraftyResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Handles mapping BadRequestException to response entity.
     * Maps to BAD_REQUEST (400).
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles mapping UnauthorizedException to response entity.
     * Maps to UNAUTHORIZED (401).
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ UnauthorizedException.class })
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles mapping NotFoundException to response entity.
     * Maps to NOT_FOUND (404).
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles mapping ConflictException to response entity.
     * Maps to CONFLICT (409).
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ ConflictException.class })
    public ResponseEntity<ErrorResponse> handleConflictException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * Handles mapping UnprocessableEntityException to response entity.
     * Maps to UNPROCESSABLE_ENTITY (422).
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ UnprocessableEntityException.class })
    public ResponseEntity<ErrorResponse> handleUnprocessableEntityException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Catch all for any other RuntimeException.
     * Maps to INTERNAL_SERVER_ERROR (500).
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<ErrorResponse> handleRuntimeException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles mapping AccessDeniedException to response entity.
     * Maps to UNAUTHORIZED (401).
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles mapping ForbiddenException to response entity.
     * Maps to NOT_FOUND (403).
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ ForbiddenException.class })
    public ResponseEntity<ErrorResponse> handleForbiddenException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
    }
}

