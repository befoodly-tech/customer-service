package com.befoodly.be.utils;

import java.util.Random;

public class CommonUtils {

    public static String GenerateOtp() {
        Random random = new Random();

        return String.format("%04d", random.nextInt(10000));
    }
}
