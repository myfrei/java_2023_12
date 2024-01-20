package org.example;

import com.google.common.base.Joiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloOtus {
    private static final Logger logger = LoggerFactory.getLogger(HelloOtus.class);
    public static void main(String[] args) {
        String joinedString = Joiner.on(", ").join(new String[]{"Hello", "Otus"});
        logger.info(joinedString);
    }
}
