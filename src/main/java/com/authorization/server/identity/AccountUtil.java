package com.authorization.server.identity;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

/*
 * Utility for domain logic.
 */
public class AccountUtil {

    private static final Random random = new Random();

    /*
     * Generate a UUID
     */
    static UUID generateUUIDv7() {
        long epochMillis = Instant.now().toEpochMilli();
        long mostSigBits = (epochMillis & 0xFFFFFFFFFFFFL) << 16;
        mostSigBits |= 0x7000; // version 7
        mostSigBits |= random.nextInt(0x1000);
        long leastSigBits = random.nextLong();
        return new UUID(mostSigBits, leastSigBits);
    }
}