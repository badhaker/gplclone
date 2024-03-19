package com.alchemy.utils;

public class ErrorMessageCode {

	public static final String REDIS_CONNECTION_FAILED = "redisConnectionFail";
	public static final String ROLE_NOT_FOUND = "Role not found";
	public static final String ROLE_ALREADY_PRESENT = "Role already exist";

	public static final String PERMISSION_ALREADY_PRESENT = "Permission already exist";
	public static final String PERMISSION_NOT_FOUND = "Permission not found";
	public static final String PERMISSION_NOT_ADDED = "Permission not added";

	public static final String USER_NOT_PRESENT = "User not present";
	public static final String INVALID_TOKEN = "Invalid token";
	public static final String ALREADY_REGISTER = "Already register";
	public static final String EMAIL_REQUIRED = "Email required";
	public static final String PASSWORD_REQUIRED = "Password required";
	public static final String USER_PASSWORD_NOT_FOUND = "User OR Password not found";
	public static final String ENTER_VALID_INFORMATION = "Enter valid information";

	public static final String LEVEL_NOT_FOUND = "Level not found";
	public static final String LEVEL_NAME_REQUIRED = "Level name required";
	public static final String LEVEL_ALREADY_EXIST = "Level already exist";
	public static final String LEVEL_ID_REQUIRED = "Level id required";

	public static final String LEARNING_TRACK_NOT_FOUND = "Learning track not found";
	public static final String LEARNING_TRACK_REQUIRED = "Learning track required";
	public static final String LEARNING_TRACK_ALREADY_EXIST = "Learning track already exist";
	public static final String LEARNING_TRACK_OBJECTIVE = "Max 250 character accept.";
	public static final String LEARNING_TRACK_NAME = "Max 100 character accept in Track Name";
	public static final String LEARNING_TRACK_CURRICULUM = "Max 150 character accept.";
	public static final String LEARNING_TRACK_TRAINER = "Max 100 character accept.";
	public static final String LEARNING_TRACK_DATE_VALIDATION = "End date should be after start date or same date.";
	public static final String LEARNING_TRACK_ENROLL_VALIDATION = "Enrollment start date should be before enrollment close date or same date.";
	public static final String LEARNING_TRACK_STATUS = "Status is required.";
	public static final String LEARNING_TRACK_SPONCER_ID_REQUIRED = "At least one sponsor ID must be specified.";
	public static final String LEARNING_TRACK_TRAINER_ID_REQUIRED = "At least one Faculty ID must be specified.";
	public static final String LEARNING_TRACK_FUNCTION_ID_REQUIRED = "At least one function ID must be specified.";
	public static final String LEARNING_TRACK_DESCRIPTION_REQUIRED = "Learning track description required";
	public static final String LEARNING_TRACK_MAX_ENROLL_REQUIRED = "Max enroll required.";
	public static final String LEARNING_TRACK_FILE_UPDATED_REQUIRED = "File updated boolean field required.";
	public static final String LEARNING_TRACK_MAX_ENROLL_VALIDATION = "Max enrollment number should be greater than zero.";
	public static final String LEARNING_TRACK_INCOMPLETE = "User has not completed this track";
	public static final String GPL_DEPARTMENT_ID_REQUIRED = "Gpl department ID must be specified.";
	public static final String GPL_FUNCTION_ID_REQUIRED = "Gpl function ID must be specified.";

	public static final String SUBTRACK_ALREADY_EXISTS = "Sub track already exists";
	public static final String SUBTRACK_NOT_PRESENT = "Sub track not present";
	public static final String SUBTRACK_NAME_REQUIRED = "Sub track name required";
	public static final String SUBTRACK_ALREADY_ASSIGN = "Sub track already assign to this learning track";

	public static final String SUBTRACK_TRACK_DATE_VALIDATION = "Sub track end date should be greater than start date or same.";

	public static final String DEPARTMENT_NOT_FOUND = "Department not found";

	public static final String DEPARTMENT_ALREADY_EXISTS = "Department already exists";
	public static final String DEPARTMENT_CHARACTER_REQUIRED = "Maximun 255 character";
	public static final String DEPARTMENT_NAME_CANNOT_BE_NULL = "Department name cannot be null";

	public static final String DESIGNATION_ALREADY_EXISTS = "Designation already exists";
	public static final String DESIGNATION_NOT_FOUND = "Designation not found";
	public static final String DESIGNATION_REQUIRED = "Designation name required";

	public static final String DESIGNATION_ID_REQUIRED = "Designation Id required";
	public static final String DESIGNATION_NAME_NOT_VALID = "Designation name not valid";

	public static final String USER_ROLE_NOT_FOUND = "User role not found";

	public static final String TESTIMONIAL_NOT_PRESENT = "Testimonial not exists";
	public static final String TESTIMONIAL_CHARACTER_REQUIRED = "Maximun 500 character";
	public static final String TESTIMONIAL_REQUIRED = "Testimonial required";
	public static final String TESTIMONIAL_NAME_NOT_VALID = "Testimonial not valid";
	public static final String TESTIMONIAL_ALREADY_PRESENT = "Testimonial already exists";
	public static final String NAME_REQUIRED = "Name required";
	public static final String INVALID_USER_NAME = "User name must not contain special characters";
	public static final String TESTIMONIAL_IMAGE_REQUIRED = "Testimonial Image url required";

	public static final String TEMPLATE_NOT_FOUND = "Certificate not found";
	public static final String TEMPLATE_ALREADY_PRESENT = "Template already exists";
	public static final String TEMPLATE_CHARACTER_REQUIRED = "Maximun 255 character";
	public static final String TEMPLATE_NAME_REQUIRED = "Template name required";
	public static final String FILE_SIZE_TOO_LARGE = "File size should be below 20MB";
	public static final String FILE_NOT_UPLOADED = "File not upload";
	public static final String FILE_REQUIRED = "File required.";
	public static final String METHOD_NOT_SUPPORTED = "Method does not supported";
	public static final String ACCESS_DENIED = "Access denied";
	public static final String URL_NOT_FOUND = "URL not found, please check URL";
	public static final String SOMETHING_WENT_WRONG = "Something went wrong. Please try after some time.";
	public static final String INVALID_URI = "Invalid url";
	public static final String PLEASE_TRY_AGAIN = ". Please try again!";
	public static final String FILE_NOT_FOUND = "File not found ";
	public static final String DIRECTORY_NOT_CREATED = "Could not create the directory where the uploaded files will be stored.";
	public static final String INVALID_FILE = "Sorry! Filename contains invalid path sequence";
	public static final String JWT_EXPIRED = " Jwt token expired";
	public static final String MISSING_JWT = "User authentication fail.";
	public static final String INVALID_INFORMATION = "Invalid email or password.";

	public static final String UNABLE_TO_GET_JWT_TOKEN = "Unable to get JWT token";
	public static final String TOKEN_EXPIRED = "JWT token has expired";

	public static final String INVALID_TEMPLATE_NAME = "Template name must not special characters ";
	public static final String TOKEN_DOES_NOT_BEGIN_WITH_BEARER = "JWT Token does not begin with Bearer String";

	public static final String INVALID_UPLOAD_TYPE = "Invalid upload type";
	public static final String BANNER_NOT_FOUND = "Banner not found";
	public static final String BANNER_ALREADY_PRESENT = "Banner already exists";
	public static final String URL_REQUIRED = "Url required";
	public static final String URI_SIZE = "Max 500 character accept.";
	public static final String BANNER_NAME_REQUIRED = "Banner name required";
	public static final String DISPLAY_ORDER_REQUIRED = "Display order required";
	public static final String CONTENT = "Content required";

	public static final String USER_NOT_FOUND_WITH_EMAIL = "User not found with Email: ";
	public static final String BUSINESS_UNIT_NOT_FOUND = "Business unit not found";
	public static final String BUSINESS_UNIT_ALREADY_PRESENT = "Business unit already exist";
	public static final String USER_NOT_FOUND = "User not found  ";

	public static final String CITY_NOT_PRESENT = "City not found.";
	public static final String CITY_REQUIRED = "City field required";
	public static final String CHARACTER_VALIDATION_FOR_CITY = "100 character accept only";

	public static final String ROLE_NAME_REQUIRED = "Role name required";
	public static final String ACTION_NAME_REQUIRED = "Action name required";
	public static final String SPONSER_NOT_FOUND = "Sponser for given id not found";
	public static final String SPONSER_NAME_REQUIRED = "Sponser name required";
	public static final String CHARACTER_LIMIT_EXCEEDED = "Character limit exceeded for sponser name";

	public static final String TRAINER_NOT_PRESENT = "Faculty not present";
	public static final String TRAINER_NAME_REQUIRED = "Faculty name required";
	public static final String DESCRIPTION_REQUIRED = "Description required";
	public static final String TRAINER_IMAGE_REQUIRED = "Image url required";
	public static final String IMAGE_URL_NOT_EXCEED_FROM_500_CHARACTER = "Image url not exceed from 500 character";
	public static final String MOBILE_IMAGE_URL_NOT_EXCEED_FROM_500_CHARACTER = "Mobile image url not exceed from 500 character";
	public static final String BANNER_URL_NOT_EXCEED_FROM_500_CHARACTER = "Banner url not exceed from 500 character";
	public static final String DESCRIPTION_NOT_EXCEED_FROM_500_CHARACTER = "Description not exceed from 500 character";
	public static final String FIFT_CHARACTER_ACCEPT_ONLY = "50 character accept only";
	public static final String PROFILE_LENGTH = "1500 character accept only";

	public static final String MODULE_ALREADY_PRESENT = "Module already present";
	public static final String MODULE_NOT_FOUND = " Module not found";
	public static final String MODULE_REQUIRED = "Module required";
	public static final String MODULE_ACCEPT_CHARACTER = "250 character accept only";

	public static final String IMAGE_REQUIRED = "Image required";
	public static final String VIDEO_REQUIRED = "Video Is Required";

	public static final String TRACKSPONSOR_ALREADY_ASSIGNED = "Sponsor already assigned to learning track";
	public static final String TRACKSPONSOR_NOT_FOUND = "Track-Sponsor not found";
	public static final String TRACKSPONSOR_NOT_UPDATED = "Track-Sponsor not updated";

	public static final String SPONSOR_MUST_BE_REQUIRED = "Sponsor must be required";
	public static final String SPONSOR_PROFILE_ACCEPT_CHARACTER = "Sponsor profile accept only 1000 character ";
	public static final String SPONSOR_PROFILE_REQUIRED = "Sponsor profile Is required";
	public static final String SPONSOR_MESSAGE_ACCEPT_CHARACTER = "Sponsor message accept only 1000 character";
	public static final String SPONSOR_MESSAGE_REQUIRED = "Sponsor message is required";

	public static final String ROLE_ID_REQUIRED = "Role id required";
	public static final String PERMISSION_ID_REQUIRED = "Permission id required";
	public static final String USER_ROLE_ALREADY_EXIST = "User role already exist";
	public static final String USER_ROLE_PERMISSION_NOT_FOUND = "User role permission not found";

	public static final String TRACK_TRAINER_ID_NOT_FOUND = "Track Faculty  not found";
	public static final String TRAINER_ALREADY_ASSIGN_TO_TRACK = "Faculty already assign to learning track";
	public static final String TRAINER_MUST_BE_REQUIRED = " Field must be required";
	public static final String BUSINESS_UNIT_NAME_REQUIRED = "Business unit name required";
	public static final String INVALID_DEPARTMENT_NAME = "Department name must not contain special characters";
	public static final String IMAGE_URL_REQUIRED = "Image url required";
	public static final String SPONSOR_DESCRIPTION_REQUIRED = "Sponsor description required";

	public static final String CERTIFICATE_ID_REQUIRED = "Certificate id required";
	public static final String CERTIFICATE_USER_NOT_FOUND = "Certificate user not found";
	public static final String USER_CERTIFICATE_ALREADY_PRESENT = "User certificate already exist";

	public static final String CHARACTER_VALIDATION_FOR_NAME = "Name should not be greater than 100 characters";
	public static final String CHARACTER_VALIDATION_FOR_DESCRIPTION = "Description should not be greater than "
			+ Constant.DESCRIPTION_LENGTH + " characters";

	public static final String INVALID_IMAGE_URL = "Image url is invalid";
	public static final String INVALID_BANNER_URL = "Banner url is invalid";

	public static final String FIELD_REQUIRED = "This field is required";
	public static final String USERTRACK_ALREADY_ASSIGNED = "Learning track has already been assigned to user";
	public static final String USERTRACK_DATE_VALIDATION = "End date should be greater than start date";
	public static final String USERTRACK_STATUS_NOT_UPDATED = "Updating learning track status of user failed";
	public static final String DISPLAY_ORDER_LENGTH = "Display order should be in between 1 to 100";

	public static final String DEPARTMENT_ID_REQUIRED = "Department id required";

	public static final String TALENT_PHILOSOPHY_NOT_FOUND = "Talent philosophy not found";
	public static final String TALENT_PHILOSOPHY_ALREADY_EXISTS = "Talent philosophy already exixts";

	public static final String CAREER_TRACKS_IMAGE_REQUIRED = "Image url required";
	public static final String CAREER_TRACKS_NAME_REQUIRED = "career track name required";
	public static final String CAREER_TRACKS_NAME = "Max 100 character accept.";

	public static final String INVALID_FORMAT = "The request payload is invalid. Please ensure that the data is in the correct format";

	public static final String CAREER_TRACKS_NOT_FOUND = "Career tracks not found";
	public static final String SPONSOR_NOT_FOUND = "Sponsor tracks not found";

	public static final String USERTRACK_NOT_FOUND = "Enrollment not found as learning track is deleted ";
	public static final String DATA_NOT_FOUND = "Data not found";
	public static final String FAIL_TO_STORE_FILE_DATA = "Fail to store file data";
	public static final String ID_NOT_FOUND = "Id not found";

	public static final String INVALID_OTP = "Invalid OTP";
	public static final String INVALID_EMAIL = "Invalid email";
	public static final String INVALID_PASSWORD_FORMAT = "Password should have Minimum 8 and "
			+ "maximum 50 characters,at least one uppercase letter, one lowercase letter, "
			+ "one number and one special character and No White Spaces";
	public static final String NULL_OTP = "Please enter otp";
	public static final String OTP_EXPIRED = "OTP has expired";
	public static final String FILE_DATA_NOT_STORED = "File data not stored ";

	public static final String INVALID_UUID = "Invalid UUID";
	public static final String PASSWORD_DOES_NOT_MATCH = "New password and confirm password should match";
	public static final String INVALID_CODE = "Invalid code";
	public static final String MAILTEMPLATE_NOT_FOUND = "MailTemplate not found";
	public static final String USERNAME_REQUIRED = "User name required";
	public static final String PHONE_NUMBER_REQUIRED = "Phone number required";
	public static final String GENDER_REQUIRED = "Gender required";
	public static final String ROLE_REQUIRED = "Role required";
	public static final String CHARACTER_VALIDATION_GENDER = "Max 100 character accepted in gender";
	public static final String CHARACTER_VALIDATION_EMAIL = "Max 100 character accepted in email";
	public static final String CHARACTER_VALIDATION_NUMBER = "Invalid phone number";
	public static final String INVALID_NAME = "Invalid username";
	public static final String OBJECTIVE_REQUIRED = "Objective required";
	public static final String CURRICULUM_REQUIRED = "Curriculum required";
	public static final String START_DATE_REQUIRED = "Start Date required";
	public static final String END_DATE_REQUIRED = "End Date required";
	public static final String ENROLL_DATE_REQUIRED = "Enrollment Date required";
	public static final String USER_NOT_ADMIN = "Login failed as user is not admin";
	public static final String ENROLLMENT_FAILED = "Enrollment failed as learning track is closed";

	public static final String INVALID_TRAINER_NAME = "Faculty name is not same as username";
	public static final String INVALID_SPONSOR_NAME = "Sponsor name is not same as username";
	public static final String PLEASE_UPLOAD_PDF = "Please upload pdf file";
	public static final String CAREER_TRACK_ALREADY_EXIST = "Career track already exists";
	public static final String PLEASE_UPLOAD_FILE = "Please upload file";
	public static final String ERROR_OCCURRED_DURING_FILE_DOWNLOAD = "Error occurred during file download: ";
	public static final String FILE_DOES_NOT_CONTAIN_VALID_COLUMN = "File does not contain valid column";
	public static final String FILE_IS_NOT_VALID_AT_COLUMN_NAME = "File is not valid at column name ";
	public static final String SUBTRACK_NOT_NULL = "Sub track for given user-track id is not null";

	public static final String GPL_THIRD_PARTY_API_DOWN = "GPL SF API is down, please try again later.";
	public static final String FILE_IS_EMPTY = "File is empty";
	public static final String ATTENDANCE_NOT_FOUND = "Attendance not found";

	public static final String CARRER_ASP_UPDATE_NOT_ALLOWED_STRING = "Updating carrer aspiration is disabled by ADMIN";
	public static final String MESSAGE_REQUIRED = "Message required";

	public static final String SPONSER_ALREADY_PRESENT = "Sponsor already present";
	public static final String VALID_IMAGE = "Please upload valid image";
	public static final String LEVE_ID_REQUIRED = "Level is required";
	public static String TRAINER_ALREADY_PRESENT = "Faculty already present";
	public static final String SPONSER_ALREADY_ASSIGN = "Sponser already assign to this Learing track";

	public static final String CAREER_ASPIRATION_STATUS_GRADEENHANCEMENT = "Status is required for gradeEnhancement.";
	public static final String CAREER_ASPIRATION_STATUS_NEXTCAREERMOVE = "Next career move field is required.";
	public static final String CAREER_ASPIRATION_NOT_FOUND = "Career aspiration not found.";
	public static final String CAREER_ASPIRATION_ID_REQUIRED = "Career aspiration ID required.";
	public static final String CAREER_ASPIRATIONS_ID_REQUIRED = "Career aspiration ID must be specified.";
	public static final String CAREER_ASPIRATION_PREFERENCE = "Cannot add more than two preferences for a Career Aspiration";
	public static final String EMPLOYEE_DETAILS_REQUIRED = "Employee details required";
	public static final String CAREER_PREFERENCE_REQUIRED = "At least one career preference is required";

	public static final String CAREER_ASPIRATION_STATUS = "Status is required.";

	public static final String KEY_REQUIRED = "key is required.";
	public static final String KEY_NOT_FOUND = "key is not present.";
	public static final String TEXT_REQUIRED = "Text is required.";

	public static final String GPL_FUNCTION_NOT_FOUND = "Gpl function is not found";
	public static final String GPL_FUNCTION_REQUIRED = "Gpl function name required";
	public static final String GPL_FUNCTION_PRESENT = "Gpl function already present";
	public static final String GPL_FUNCTION_DELETE = "At least one function should be select for deleting function";

	public static final String GPL_DEPARTMENT_NOT_FOUND = "Gpl department is not found";
	public static final String GPL_DEPARTMENT_REQUIRED = "Gpl department name required";
	public static final String GPL_DEPARTMENT_PRESENT = "Gpl department already present";
	public static final String GPL_DEPARTMENT_DELETE = "At least one department should be select for deleting department";

	public static final String GPL_ROLE_NOT_FOUND = "Gpl role is not found";
	public static final String GPL_ROLE_REQUIRED = "Gpl role name required";
	public static final String GPL_ROLE_PRESENT = "Gpl role already present";
	public static final String GPL_ROLE_DELETE = "At least one role should be select for deleting role";
	public static final String GPL_EXPERIENCE_REQUIRED = "Gpl experience required";
	public static final String AT_LEAST_ONE_ID_REQUIRED = "At least one ID required.";
	public static final String FUNCTION_ID_REQUIRED = "Function id required";

	public static final String UPLOAD_CSV = "Please upload csv file only";

	public static final String UPLOAD_PDF = "Please upload pdf file only";
	public static final String FOOTER_TEXT = "Please enter text";
	public static final String UPLOAD_KEY = "Please enter valid key";
	public static final String INVALID_IMAGE_FILE = "Please upload valid image file";
	public static final String ATTENDANCE_LOCKED = "Attendance is locked. Please unlock attendace to update status";
	public static final String THUMBNAIL_FILE_REQUIRED = "Thumbnail file required";
	public static final String ENROLL_CLOSE_DATE_REQUIRED = "Enroll close date is required";
	public static final String ENROLL_START_DATE_REQUIRED = "Enroll start date is required";
	public static final String CANNOT_ENROLL = "User Level and function does not match Track level and function";
	public static final String PLEASE_ENTER_VALID_LEVEL_NAME = "Please enter valid level name";
	public static final String PLEASE_ENTER_VALID_DESIGNATION_NAME = "Please enter valid designation name";
	public static final String SUBTRACK_NAME_VALIDATION = "Maximum 50 characters accepted for Subtrack name";
	public static final String BANNER_ISVISIBLE_LIMIT = "More than 6 banners cannot be visible";
}
