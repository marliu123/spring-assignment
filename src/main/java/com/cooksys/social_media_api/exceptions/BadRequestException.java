package com.cooksys.social_media_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = -4894478144519526776L;

    private String message;
}
