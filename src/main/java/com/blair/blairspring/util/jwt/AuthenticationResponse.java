package com.blair.blairspring.util.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class AuthenticationResponse implements Serializable {

    private final String jwt;

}