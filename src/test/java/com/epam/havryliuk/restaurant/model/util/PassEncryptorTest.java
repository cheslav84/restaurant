package com.epam.havryliuk.restaurant.model.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class PassEncryptorTest {

    private static Stream<Arguments> passwords() {
        return Stream.of(
                Arguments.of("$argon2i$v=19$m=15360,t=2,p=1$s6su5mGtLExcTxehnxXvSQ$kTs2aM2VapvTx5WSQSqMTRFJuqGbLugxcL/fnNABw0g", "password0"),
                Arguments.of("$argon2i$v=19$m=15360,t=2,p=1$P99d0l+GqUolCQ84gMSfBA$MkntNxZfeJb7QSjbHAwPEK2+10gqy7jjhijJYYZbmH8", "password1"),
                Arguments.of("$argon2i$v=19$m=15360,t=2,p=1$CXw2xpEdmqPfJBYGVibIdQ$Td2ybO0xP93Qx2ZgCI13GOBCivYLfi9qMdzMOC87Nr8", "password2"),
                Arguments.of("$argon2i$v=19$m=15360,t=2,p=1$iBcC2VHJaaVJHZtCF1GNQw$ZsdNj/fdliR47KxIT/GpgLtY6lQVZPNKM2EPTZoXHtk", "password9")
        );
    }


    @ParameterizedTest
    @MethodSource("passwords")
    void validateEncryption(String hash, String rawPassword) {
            assertDoesNotThrow(() -> {
                PassEncryptor.verify(hash, rawPassword);
            });

    }

}