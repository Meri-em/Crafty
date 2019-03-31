package com.crafty.web.exception.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.crafty.dto.ErrorResponse;
import com.crafty.web.exception.BadRequestException;
import com.crafty.web.exception.ConflictException;
import com.crafty.web.exception.ForbiddenException;
import com.crafty.web.exception.NotFoundException;
import com.crafty.web.exception.UnauthorizedException;
import com.crafty.web.exception.UnprocessableEntityException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CraftyResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts error response JSON back to the original exception class.
     *
     * @param response
     * @throws IOException
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ErrorResponse craftyResponse = objectMapper.readValue(response.getBody(), ErrorResponse.class);
        switch (response.getStatusCode()) {
            case BAD_REQUEST:
                throw new BadRequestException(craftyResponse.getError());
            case UNAUTHORIZED:
                throw new UnauthorizedException(craftyResponse.getError());
            case NOT_FOUND:
                throw new NotFoundException(craftyResponse.getError());
            case CONFLICT:
                throw new ConflictException(craftyResponse.getError());
            case UNPROCESSABLE_ENTITY:
                throw new UnprocessableEntityException(craftyResponse.getError());
            case FORBIDDEN:
                throw new ForbiddenException(craftyResponse.getError());
            default:
                throw new RuntimeException(craftyResponse.getError());

        }
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus.Series series = response.getStatusCode().series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series));
    }
}

