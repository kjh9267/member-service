package me.jun.memberservice.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.memberservice.core.application.dto.TokenResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
abstract public class TokenFixture {

    public static final String JWT_KEY = "bUM2q6grP497hK4NKzCFbM9X8EBFbLgXXQXqmdShvNlkHNbQkk460rELLYxVA4cU+0rg9AIIl4ReodO5LKm2j+vlT+z844EznNTQzSEigdnGeE8E+KQNk4vmsBEqg8s4VJLSyv+EyadfrZMtmArlScxQaK69cmwppQ4Fv3hkB+U=";

    public static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.6ay5LRrQAzHAxXepa18xAOzlfcPZn2WAk4KRTt__bL7qk-IaaFBp5Y5OECNF1hoJip7Kl0MNWxRoqA1s4YjupA";

    public static TokenResponse tokenResponse() {
        return TokenResponse.of(TOKEN);
    }
}
