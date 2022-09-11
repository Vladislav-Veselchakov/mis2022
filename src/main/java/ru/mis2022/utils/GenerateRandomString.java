package ru.mis2022.utils;

import java.util.Random;

public class GenerateRandomString {
    public static String getRndStr(int len){
        String abc = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(abc.charAt(rnd.nextInt(abc.length())));
        }
        return sb.toString();
    }
}
