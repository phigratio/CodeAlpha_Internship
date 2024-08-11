package com.muqtuu.trading.config;

public class JwtConstant {

    public static final String SECRET_KEY = "io.jsonwebtoken.security.WeakKeyException: The specified key byte array is 96 bits which is not secure enough for any JWT HMAC-SHA algorithm.  The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size).  Consider using the Jwts.SIG.HS256.key() builder (or HS384.key() or HS512.key()) to create a key guaranteed to be secure enough for your preferred HMAC-SHA algorithm.  See https://tools.ietf.org/html/rfc7518#section-3.2 for more information.0";
    public static final String JWT_HEADER = "Authorization";
}
