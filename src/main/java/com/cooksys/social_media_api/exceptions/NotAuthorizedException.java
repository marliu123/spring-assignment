package com.cooksys.social_media_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = -2069928772805239791L;

    private String message;
}
