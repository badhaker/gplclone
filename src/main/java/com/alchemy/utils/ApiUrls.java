package com.alchemy.utils;

public class ApiUrls {
	public static final String API = "/api";
	public static final String AUTH = "/auth";
	public static final String REGISTER = "/register";
	public static final String ROLES = "/roles";
	public static final String GET_ALL = "/all";
	public static final String PERMISSIONS = "/permissions";
	public static final String BY_MODULE = "/byModule";
	public static final String SPONSOR = "/sponsor";
	public static final String USER = "/user";
	public static final String TESTIMONIAL = "/testimonial";
	public static final String LEVEL = "/level";
	public static final String LEARNINGTRACK = "/learningTrack";
	public static final String DEPARTMENT = "/department";
	public static final String DESIGNATION = "/designation";
	public static final String USER_ROLE = "/user_role";
	public static final String ROLE_PERMISSION = "/role_permission";
	public static final String CERTIFICATE = "/certificate";
	public static final String CITY = "/city";
	public static final String TRACKSPONSOR = "/track_sponsor";
	public static final String TRACK_TRAINER = "/track-trainer";
	public static final String LOGIN = "/login";
	public static final String BANNER = "/banner";
	public static final String DOWNLOAD_FILE = "/downloadFile/{fileName:.+}";
	public static final String BUSINESS_UNIT = "/business_unit";
	public static final String TRAINER = "/trainer";
	public static final String MODULE = "/module";
	public static final String SUBTRACK = "/subTrack";
	public static final String USERTRACK = "/user-track";
	public static final String ALL = "/all";
	public static final String UPLOAD = "/upload";
	public static final String GPL_EMPLOYEE_INFO = "/gpl-employee-info";
	public static final String SYSTEM_CONFIG = "/config";
	public static final String GPL_FUNCTION = "/gpl-function";
	public static final String GPL_DEPARTMENT = "/gpl-department";
	public static final String GPL_ROLE = "/gpl-role";
	public static final String CAREERTRACKS = "/career-tracks";
	public static final String USER_SF_DETAIL = "/user-sf-detail";

	public static final String TALENT_PHILOSOPHY = "/talent-philosophy";
	public static final String ENROLL_NOW = "/enrollNow";
	public static final String ENROLL_NOW_UPLOAD = "/upload";
	public static final String ATTENDANCE = "/attendance";
	public static final String ATTENDANCE_UPLOAD = "/upload";
	public static final String SAML = "/saml";

	public static final String[] URLS_WITHOUT_HEADER = { ApiUrls.API + ApiUrls.AUTH + ApiUrls.LOGIN,
			ApiUrls.API + ApiUrls.AUTH + ApiUrls.REGISTER, ApiUrls.API + ApiUrls.REFRESH_TOKEN,
			ApiUrls.AUTH + ApiUrls.FORGOT_PASSWORD, ApiUrls.SAML + "/login", ApiUrls.SAML + "/response",
			ApiUrls.SAML + "/validate" };
	public static final String USER_CERTIFICATE = "/user_certificate";

	public static final String[] SWAGGER_URLS = { "/v3/api-docs", "/v2/api-docs", "/swagger-resources/**",
			"/swagger-ui/**", "/webjars/**", "/api/swagger-ui/index.html" };
	public static final String REFRESH_TOKEN = "/refresh-token";
	public static final String FORGOT_PASSWORD = "/forgot-password";
	public static final String FORGOT_PASSWORD_CONFIRM = "/forgot-password/confirm";

	public static final String MAILTEMPLATE = "/mailtemplate";
	public static final String ONBOARD = "/onboard";
	public static final String ADD_USER = "/add-user";
	public static final String FILE_LIST = "/fileList";
	public static final String FILE_UPLOAD = "/upload-file";
	public static final String FILE_DELETE = "/delete-file";
	public static final String CAREER_ASPIRATION = "/career-aspiration";
	public static final String PREFERENCES = "/preferences";
	public static final String ATTENDANCE_LOCK = "/lock";
	public static final String ATTENDANCE_STATUS = "/status";
	public static final String ENROLL_STATUS = "status";
	public static final String ATTENDANCE_STAR = "/star";
	public static final String GPL_THIRD_PARTY_CREDENTIALS = "/thirdPartyCredentials";
}
