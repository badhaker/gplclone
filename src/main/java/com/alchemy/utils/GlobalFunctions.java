package com.alchemy.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class GlobalFunctions {
	
	public static final String SF_API = "SF_API";

	
	@Value("${jwt.secret}")
	private static String secret;
	
	public final static String CUSTUM_ATTRIBUTE_USER_ID = "X-user-id";
	public final static String CUSTUM_ATTRIBUTE_USER_ROLES = "X-user-roles";
	public final static String CUSTUM_ATTRIBUTE_USER_PERMISSIONS = "X-user-permission";

	public static String getFileUrl(String url) {
		if (url == null) {
			return null;
		}

		String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(url)
				.toUriString();

		String urls = fileUrl.replace("http", "https");
		return urls;
	}

	public static String date(Date date) {
		return date != null ? (String) date.toString().subSequence(0, 10) : null;
	}

	public static String dateTimestamp(Date date) {
		return date != null ? (String) date.toString() : null;
	}

	public static Timestamp dateConvertIntoTimestamp(Date date, int hours, int mins, int seconds) {
		// Convert Date to LocalDateTime
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		// Set the time component to 00:00:00
		LocalDateTime startOfDay = localDateTime.withHour(hours).withMinute(mins).withSecond(seconds);
		// Create a Timestamp from LocalDateTime
		return Timestamp.valueOf(startOfDay);
	}
	
	private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    public static void prepareSecreteKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}
