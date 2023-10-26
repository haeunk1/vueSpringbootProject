package com.gallery.backend.Service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String getToken(String key, Object value);
    Claims getClaims(String token);

    //사용자가 올바른지
    boolean isValue(String token);

    int getId(String token);
}
