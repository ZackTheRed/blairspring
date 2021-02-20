package com.blair.blairspring.util.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthenticationResponse implements Serializable {

    private final String jwt;

}