package com.alchemy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

public class Validator {

	public static final String VIDEO_URL = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube(-nocookie)?\\.com|youtu.be))(\\/"
			+ "(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$";
	private static final Pattern URL = Pattern.compile(VIDEO_URL);

	public static boolean isValidforVideoUrl(final String videoUrl) {

		Matcher matcher = URL.matcher(videoUrl);
		return matcher.matches();
	}

	public static final String NAME_PATTERN = "^[a-zA-Z0-9_()&\\s]+$";
	public static final Pattern NAME = Pattern.compile(NAME_PATTERN);

	public static boolean isValidforName(final String name) {

		Matcher matcher = NAME.matcher(name);
		return matcher.matches();
	}

	// public static final String URL_VALIDATION =
	// "^https?:\\/\\/(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(?:\\/[\\w-]+)*\\/([\\w-]+\\.(?:jpg|gif|png|jpeg))$";
	public static final String URL_VALIDATION = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private static final Pattern URL1 = Pattern.compile(URL_VALIDATION);

	public static boolean isValidForUrl(final String urlValidation) {
		Matcher master = URL1.matcher(urlValidation);
		return master.matches();
	}

	public static final String IMAGE_VIDEO_URL = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
	private static final Pattern URL2 = Pattern.compile(IMAGE_VIDEO_URL);

	public static boolean isValidForNameVideo(final String urlValidation) {
		Matcher master = URL2.matcher(urlValidation);
		return master.matches();

	}

	public static final String FILE_REGEX = ".*\\.(gif|jpe?g|tiff?|png|webp|bmp)$";
	public static final Pattern IMAGE_FILE = Pattern.compile(FILE_REGEX, Pattern.CASE_INSENSITIVE);

	public static boolean isValidforImageFile(final String name) {

		Matcher matcher = IMAGE_FILE.matcher(name);
		return matcher.matches();
	}

	public static final String OTP_VALIDATER = "^[0-9]{6}$";
	private static final Pattern OTP = Pattern.compile(OTP_VALIDATER);

	public static boolean isValidOtp(final String otp) {
		Matcher matcher = OTP.matcher(otp);
		return matcher.matches();
	}

	public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$";

	private static final Pattern PATTERN = Pattern.compile(PASSWORD_PATTERN);

	public static boolean isValid(final String password) {
		Matcher matcher = PATTERN.matcher(password);
		return matcher.matches();
	}

	public static final String MAIL_PATTERN = "^\\S+@\\S+\\.\\S+$";
	private static final Pattern MAIL = Pattern.compile(MAIL_PATTERN);

	public static boolean isValidforEmail(final String email) {

		Matcher matcher = MAIL.matcher(email);

		return matcher.matches();
	}

	public static final String PHONE_NUBER = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";
	private static final Pattern NUMBER = Pattern.compile(PHONE_NUBER);

	public static boolean isValidforPhoneNumber(final String number) {

		Matcher matcher = NUMBER.matcher(number);

		return matcher.matches();
	}

	public static boolean isValidforPdf(MultipartFile file) {
		if (file == null) {
			return true; // Let @NotNull handle this
		}
		String contentType = file.getContentType();
		return contentType != null && contentType.equals("application/pdf");
	}

	public static final String CITY_PATTERN = "^[a-zA-Z_()&\\s]+$";
	public static final Pattern CITY = Pattern.compile(CITY_PATTERN);

	public static boolean isValidforCityName(final String name) {

		Matcher matcher = CITY.matcher(name);
		return matcher.matches();
	}

}