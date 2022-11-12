package com.yukiii.demo.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

public class CryptUtil {
  public static String randomPassword() {
    return RandomStringUtils.randomAlphanumeric(6);
  }

  public static String getHashSHA256(String input) {
    String enkripsi = null;
    try {
      MessageDigest mDigest = DigestUtils.getSha256Digest();
      mDigest.reset();
      mDigest.update(input.getBytes("UTF-8"));
      Formatter formatter = new Formatter();
      for (byte b : mDigest.digest()) {
        formatter.format("%02x", b); //format ini yang perlu diperhatikan dalam hash sandi tersebut
      } //end of for
      enkripsi = formatter.toString();
    } catch (UnsupportedEncodingException e) {
    }
    return enkripsi;
  }

  public static String encodeBCrypt(String str, int strength) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength);
    return encoder.encode(str);
  }

  public static String encodeBCrypt(String str) {
    return encodeBCrypt(str, 10);
  }

  public static boolean isPasswordMatch(String rawPassword, String encodePassword){
    return isPasswordMatch(rawPassword, encodePassword,10);
  }

  public static boolean isPasswordMatch(String rawPassword, String encodePassword, int strength){
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);
    return passwordEncoder.matches(rawPassword, encodePassword);
  }

  public static String generateUUID() {
    return UUID.randomUUID().toString();
  }

  public static String getHashSHA1(String input) {
    String enkripsi = null;
    try {
      MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
      mDigest.reset();
      mDigest.update(input.getBytes("UTF-8"));
      Formatter formatter = new Formatter();
      for (byte b : mDigest.digest()) {
        formatter.format("%02x", b); //format ini yang perlu diperhatikan dalam hash sandi tersebut
      } //end of for
      enkripsi = formatter.toString();
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
    }
    return enkripsi;
  }
}
