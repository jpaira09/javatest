package org.dtna.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
@Slf4j
public class RandomReferenceId {
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    public  String generateRandomId() {
        log.info("Generating random id");
        Random random = new Random();

        // Generate a random length between 8 and 16
        int length = 8 + random.nextInt(9);

        StringBuilder idBuilder = new StringBuilder();

        // Add the first letter
        idBuilder.append(LETTERS.charAt(random.nextInt(LETTERS.length())));

        // Add the remaining alphanumeric characters
        for (int i = 1; i < length; i++) {
            idBuilder.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
        }
        log.info("Generated random id: {}", idBuilder.toString());

        return idBuilder.toString();
    }
}
