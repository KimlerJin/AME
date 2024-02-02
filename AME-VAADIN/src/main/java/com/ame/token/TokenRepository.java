package com.ame.token;

public interface TokenRepository {

    void add(TokenInfo tokenInfo);

    TokenInfo get(String token);

    void delete(TokenInfo tokenInfo);
}
