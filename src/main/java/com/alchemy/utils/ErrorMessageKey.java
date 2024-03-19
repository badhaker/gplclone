package com.alchemy.utils;

public class ErrorMessageKey {

	public static final String USER = "11";
	public static final String ROLE = "12";
	public static final String LEVEL = "14";
	public static final String PERMISSION = "13";
	public static final String LEARNING_TRACK = "17";
	public static final String DEPARTMENT = "16";
	public static final String DESIGNATION = "15";
	public static final String TESTIMONIAL = "18";
	public static final String USER_ROLE = "19";
	public static final String ROLE_PERMISSION = "20";
	public static final String CERTIFICATE = "23";
	public static final String GLOBAL_EXCEPTION = "22";
	public static final String BANNER = "21";
	public static final String BUSINESS_UNIT = "25";
	public static final String CITY = "26";
	public static final String TRAINER = "29";
	public static final String SPONSER = "27";
	public static final String USER_CERTIFICATE = "28";
	public static final String MODULE = "30";

	public static final String TRACKSPONSOR = "31";

	public static final String TRACK_TRAINER = "32";

	public static final String SUBTRACK = "33";

	public static final String USERTRACK = "34";
	public static final String TALENT_PHILOSOPHY = "35";
	public static final String CAREERTRACKS = "36";
	public static final String ATTENDANCE = "37";

	public static final String MAILTEMPLATE = "37";
	public static final String ENROLLSTATUS = "38";
	public static final String EMAIL = "39";

	public static final String FILE = "37";
	public static final String CAREER_ASPIRATION = "40";

	public static final String GPLFUNCTION = "38";
	public static final String GPLDEPARTMENT = "39";
	public static final String GPLROLE = "40";
	public static final String FOOTER_TEXT = "41";

	public static final String PREFERENCES = "41";

	public static final String ROLE_E031201 = "GPL-E03" + ROLE + "01"; // role not found
	public static final String ROLE_E031202 = "GPL-E03" + ROLE + "02"; // role already present
	public static final String ROLE_E031203 = "GPL-E03" + ROLE + "03";// invalid role
	public static final String ROLE_E031204 = "GPL-E03" + ROLE + "04";// role name required

	public static final String USER_E031201 = "GPL-E03" + USER + "01";// user not present
	public static final String USER_E031202 = "GPL-E03" + USER + "02";// Already register
	public static final String USER_E031203 = "GPL-E03" + USER + "03";// Invalid token
	public static final String USER_E031204 = "GPL-E03" + USER + "04";// email and password required
	public static final String USER_E031205 = "GPL-E03" + USER + "05";// invalid otp
	public static final String USER_E031206 = "GPL-E03" + USER + "06";// invalid password format
	public static final String USER_E031207 = "GPL-E03" + USER + "07";// password does not match

	public static final String LEVEL_E031401 = "GPL-E03" + LEVEL + "01";// level not found
	public static final String LEVEL_E031402 = "GPL-E03" + LEVEL + "02";// level already present
	public static final String LEVEL_E031403 = "GPL-E03" + LEVEL + "03";// level name required
	public static final String LEVEL_E031404 = "GPL-E03" + LEVEL + "04";// Maximun 255 character

	public static final String LEARNING_TRACK_E031701 = "GPL-E03" + LEARNING_TRACK + "01";// learning track not found
	public static final String LEARNING_TRACK_E031703 = "GPL-E03" + LEARNING_TRACK + "03";// learning track name
																							// required
	public static final String LEARNING_TRACK_E031704 = "GPL-E03" + LEARNING_TRACK + "04";// Maximun 255 character
	public static final String LEARNING_TRACK_E031705 = "GPL-E03" + LEARNING_TRACK + "05";// Maximun 250 character
	public static final String LEARNING_TRACK_E031706 = "GPL-E03" + LEARNING_TRACK + "06";// Maximun 100 character
	public static final String LEARNING_TRACK_E031707 = "GPL-E03" + LEARNING_TRACK + "07";// Maximun 150 character
	public static final String LEARNING_TRACK_E031708 = "GPL-E03" + LEARNING_TRACK + "08";// Maximun 100 character

	public static final String SUBTRACK_E033301 = "GPL-E03" + SUBTRACK + "01";// subtrack track not found
	public static final String SUBTRACK_E033302 = "*GPL-E03" + SUBTRACK + "02";// Maximun 100 character
																				// SUBTRACK_NOT_PRESENT
	public static final String SUBTRACK_E033303 = "*GPL-E03" + SUBTRACK + "03";// Maximun 100 character Subttrack
																				// already exists
	public static final String SUBTRACK_E033304 = "*GPL-E03" + SUBTRACK + "04";// Maximun 100 character End date should
																				// be greater than start date.
	public static final String SUBTRACK_E033305 = "GPL-E03" + SUBTRACK + "05";// Invalid URI
	public static final String SUBTRACK_E033306 = "GPL-E03" + SUBTRACK + "06";// Maximun 500 character

	public static final String PERMISSION_E031301 = "GPL-E03" + PERMISSION + "01";// permission not found
	public static final String PERMISSION_E031302 = "GPL-E03" + PERMISSION + "02";// permission already present
	public static final String PERMISSION_E031303 = "GPL-E03" + PERMISSION + "03";// invalid permission
	public static final String PERMISSION_E031304 = "GPL-E03" + PERMISSION + "04";// action name required

	public static final String USER_E031101 = "GPL-E03" + USER + "01";// user not present
	public static final String USER_E031102 = "GPL-E03" + USER + "02";// Already register

	public static final String DESIGNATION_E031301 = "GPL-E03" + DESIGNATION + "01";// desigantion already present
	public static final String DESIGNATION_E031302 = "GPL-E03" + DESIGNATION + "02";// desigantion not found
	public static final String DESIGNATION_E031303 = "GPL-E03" + DESIGNATION + "03";// Degignation required
	public static final String DESIGNATION_E031304 = "GPL-E03" + DESIGNATION + "04";// Maximun 255 character

	public static final String USER_ROLE_E031902 = "GPL-E03" + USER_ROLE + "02";// user role not found
	public static final String ROLE_PERMISSION_E032002 = "GPL-E03" + ROLE_PERMISSION + "02";// role permission not found

	public static final String TESTIMONIAL_E031801 = "GPL-E03" + TESTIMONIAL + "01";// Testimonial not present
	public static final String TESTIMONIAL_E031802 = "GPL-E03" + TESTIMONIAL + "02";// Maximun 1000 character
	public static final String TESTIMONIAL_E031803 = "GPL-E03" + TESTIMONIAL + "03";// Testimonial required
	public static final String TESTIMONIAL_E031804 = "GPL-E03" + TESTIMONIAL + "04";// Name required
	public static final String TESTIMONIAL_E031805 = "GPL-E03" + TESTIMONIAL + "05"; // Testimonial char 225 required
	public static final String TESTIMONIAL_E031806 = "GPL-E03" + TESTIMONIAL + "06"; // Testimonial image required

	public static final String DEPARTMENT_E031601 = "GPL-E03" + DEPARTMENT + "01"; // department already exists
	public static final String DEPARTMENT_E031602 = "GPL-E03" + DEPARTMENT + "02"; // department not found
	public static final String DEPARTMENT_E031603 = "GPL-E03" + DEPARTMENT + "03"; // department null
	public static final String DEPARTMENT_E031604 = "GPL-E03" + DEPARTMENT + "04"; // department char 225 required

	public static final String CERTIFICATE_E032301 = "GPL-E03" + CERTIFICATE + "01"; // certificate template not found
	public static final String CERTIFICATE_E032302 = "GPL-E03" + CERTIFICATE + "02"; //
	public static final String CERTIFICATE_E032303 = "GPL-E03" + CERTIFICATE + "03";; // template name invalid

	public static final String GLOBAL_EXCEPTION_E032101 = "GPL-E03" + GLOBAL_EXCEPTION + "01"; // file size too large
	public static final String GLOBAL_EXCEPTION_E032102 = "GPL-E03" + GLOBAL_EXCEPTION + "02"; // file not uploaded
	public static final String GLOBAL_EXCEPTION_E032103 = "GPL-E03" + GLOBAL_EXCEPTION + "03"; // Method not supported
	public static final String GLOBAL_EXCEPTION_E032104 = "GPL-E03" + GLOBAL_EXCEPTION + "04"; // Access denied
	public static final String GLOBAL_EXCEPTION_E032105 = "GPL-E03" + GLOBAL_EXCEPTION + "05"; // URL not found, please
																								// // check URL
	public static final String GLOBAL_EXCEPTION_E032106 = "GPL-E03" + GLOBAL_EXCEPTION + "06"; // something went wrong
	public static final String GLOBAL_EXCEPTION_E032107 = "GPL-E03" + GLOBAL_EXCEPTION + "07"; // Invalid URI
	public static final String GLOBAL_EXCEPTION_E032108 = "GPL-E03" + GLOBAL_EXCEPTION + "08"; // Invalid informayion
	public static final String GLOBAL_EXCEPTION_E032110 = "GPL-E03" + GLOBAL_EXCEPTION + "09"; // Invalid payload

	public static final String BANNER_E032101 = "GPL-E03" + BANNER + "01";// banner not found
	public static final String BANNER_E032102 = "GPL-E03" + BANNER + "02";// already present
	public static final String BANNER_E032103 = "GPL-E03" + BANNER + "03";// Invalid URI
	public static final String BANNER_E032104 = "GPL-E03" + BANNER + "04";// Maximun 500 character
	public static final String BANNER_E032105 = "GPL-E03" + BANNER + "05";// maximum 250 character
//	public static final String GLOBAL_EXCEPTION_E032108 = "GPL-E03" + GLOBAL_EXCEPTION + "08"; // Jwt expired
	public static final String GLOBAL_EXCEPTION_E032109 = "GPL-E03" + GLOBAL_EXCEPTION + "09"; // Missing Jwt

	public static final String BUSINESS_UNIT_E032501 = "GPL-E03" + BUSINESS_UNIT + "01";// Business unit not found
	public static final String BUSINESS_UNIT_E032502 = "GPL-E03" + BUSINESS_UNIT + "02";// Business unit already present
	public static final String BUSINESS_UNIT_E032503 = "GPL-E03" + BUSINESS_UNIT + "03";// Business unit required

	public static final String CITY_E032601 = "GPL-E03" + CITY + "01"; // City not present
	public static final String CITY_E032602 = "GPL-E03" + CITY + "02"; // City required
	public static final String CITY_E032603 = "GPL-E03" + CITY + "03"; // 100 character accept only

	public static final String USER_CERTIFICATE_E032802 = "GPL-E03" + USER_CERTIFICATE + "02"; // already present
	public static final String USER_CERTIFICATE_E032804 = "GPL-E03" + USER_CERTIFICATE + "04";// certificate if required

	public static final String TRAINER_E032901 = "GPL-E03" + TRAINER + "01"; // Trainer not present
	public static final String TRAINER_E032902 = "GPL-E03" + TRAINER + "02"; // Trainer name required
	public static final String TRAINER_E032903 = "GPL-E03" + TRAINER + "03"; // 50 character accept only

	public static final String MODULE_E033001 = "GPL-E03" + MODULE + "01"; // Module already present
	public static final String MODULE_E033002 = "GPL-E03" + MODULE + "02"; // Module not found
	public static final String MODULE_E033003 = "GPL-E03" + MODULE + "03"; // Module required
	public static final String MODULE_E033004 = "GPL-E03" + MODULE + "04"; // 250 character accept only

	public static final String SPONSER_E032701 = "GPL-E03" + SPONSER + "01"; // NOT FOUND
	public static final String SPONSER_E032702 = "GPL-E03" + SPONSER + "02"; // name required
	public static final String SPONSER_E032703 = "GPL-E03" + SPONSER + "03"; // character limit exceed
	public static final String SPONSOR_E032704 = "GPL-03" + SPONSER + "04";// 1000 character accept only
	public static final String SPONSOR_E032705 = "GPL-03" + SPONSER + "05";// profile required

	public static final String TRACKSPONSOR_E033101 = "GPL-E03" + TRACKSPONSOR + "01"; // NOT FOUND
	public static final String TRACKSPONSOR_E033102 = "GPL-E03" + TRACKSPONSOR + "02"; // not updated
	public static final String TRACKSPONSOR_E033103 = "GPL-E03" + TRACKSPONSOR + "03"; // already present
	public static final String TRACKSPONSOR_E033104 = "GPL-E03" + TRACKSPONSOR + "04";// 1000 character accept only
	public static final String TRACKSPONSOR_E033105 = "GPL-E03" + TRACKSPONSOR + "05";// sponsor_message required

	public static final String TRACK_TRAINER_E033201 = "GPL-E03" + TRACK_TRAINER + "01"; // Track trainer not found
	public static final String TRACK_TRAINER_E033202 = "GPL-E03" + TRACK_TRAINER + "02"; // trainer already assign to
																							// learning track

	public static final String USERTRACK_E033401 = "GPL-E03" + USERTRACK + "01";
	public static final String USERTRACK_E033402 = "GPL-E03" + USERTRACK + "02"; // FIELD REQUIRED
	public static final String USERTRACK_E033403 = "GPL-E03" + USERTRACK + "03"; // status update failed

	public static final String TALENT_PHILOSOPHY_E033501 = "GPL-E03" + TALENT_PHILOSOPHY + "01";// not found
	public static final String TALENT_PHILOSOPHY_E033502 = "GPL-E03" + TALENT_PHILOSOPHY + "02";// already exist
	public static final String TALENT_PHILOSOPHY_E033503 = "GPL-E03" + TALENT_PHILOSOPHY + "03";// name required
	public static final String TALENT_PHILOSOPHY_E033504 = "GPL-E03" + TALENT_PHILOSOPHY + "04";// mobile image url
																								// length 500 character

	public static final String CAREERTRACKS_E033601 = "GPL-E03" + CAREERTRACKS + "01";// career tracks added
	public static final String CAREERTRACKS_E033602 = "GPL-E03" + CAREERTRACKS + "02"; // career tracks updated
	public static final String CAREERTRACKS_E033603 = "GPL-E03" + CAREERTRACKS + "03";// career tracks deleted
	public static final String CAREERTRACKS_E033604 = "GPL-E03" + CAREERTRACKS + "04";// career tracks FETCHED
	public static final String CAREERTRACKS_E033605 = "GPL-E03" + CAREERTRACKS + "06";// Maximun 100 character
	public static final String CAREERTRACKS_E033606 = "GPL-E03" + CAREERTRACKS + "07"; // career tracks not found
	public static final String CAREERTRACKS_E033607 = "GPL-E03" + CAREERTRACKS + "08"; // NOT FOUND

	public static final String EMAIL_E033701 = "GPL-E01" + EMAIL + "01"; // Invalide email

	public static final String FILE_UPLOAD_E03701 = "GPL-E03";// FILE not found

	public static final String ATTENDANCE_E033701 = "GPL-E03" + ATTENDANCE + "01";// not found

	public static final String MAILTEMPLATE_E033701 = "GPL-E03" + MAILTEMPLATE + "01";

	public static final String ENROLL_STATUS_E031801 = "GPL-E03" + ENROLLSTATUS + "01";// Enroll status not found

	public static final String CAREER_ASPIRATION_E034001 = "GPL-E03" + CAREER_ASPIRATION + "01";// Career aspiration
	// not added
	// successfully

	public static final String CAREER_ASPIRATION_E034002 = "GPL-E03" + CAREER_ASPIRATION + "02"; // Career aspiration
																									// char 225
																									// required

	public static final String PREFERENCE_E034102 = "GPL-E03" + PREFERENCES + "02";// preference experience size large

	public static final String CAREER_ASPIRATION_E034003 = "GPL-E03" + CAREER_ASPIRATION + "03";// career aspiration
																								// name

	public static final String CAREER_ASPIRATION_E034004 = "GPL-E03" + CAREER_ASPIRATION + "04";// career aspiration
																								// required
	// required

	public static final String CAREER_ASPIRATION_E034005 = "GPL-E03" + CAREER_ASPIRATION + "05";// career aspiration
																								// experience required

	public static final String CAREER_ASPIRATION_E034006 = "GPL-E03" + CAREER_ASPIRATION + "06";// career aspiration
																								// details required

	public static final String GPLFUNCTION_E031901 = "GPL-E03" + GPLFUNCTION + "01"; // Gpl function is not found
	public static final String GPLFUNCTION_E031902 = "GPL-E03" + GPLFUNCTION + "02";// Gpl function required

	public static final String GPLDEPARTMENT_E0311001 = "GPL-E03" + GPLDEPARTMENT + "01"; // Gpl department is not found
	public static final String GPLDEPARTMENT_E0311002 = "GPL-E03" + GPLDEPARTMENT + "02";// Gpl department required

	public static final String GPLROLE_E0311101 = "GPL-E03" + GPLROLE + "01"; // Gpl department is not found
	public static final String GPLROLE_E0311102 = "GPL-E03" + GPLROLE + "02";// Gpl department required

	public static final String GPLROLE_E0311103 = "GPL-E03" + GPLROLE + "02";// Gpl role required
	public static final String GPLROLE_E0311104 = "GPL-E03" + GPLROLE + "03";// Gpl role not found

	public static final String PREFERENCES_E034101 = "GPL-E03" + PREFERENCES + "01";// Preferences
	// not added
	// successfully

	public static final String GPL_CAREER_ASPIRATIONS_E03701 = "GPL-E03";//

	public static final String CAREER_ASPIRATION_E034007 = "error";

	public static final String CAREER_ASPIRATION_E034008 = "GPL-E03" + CAREER_ASPIRATION + "08";

	public static final String FOOTER_TEXT_E0311101 = "GPL-E03" + FOOTER_TEXT + "01";// Gpl department required

	public static final String FOOTER_DOCUMENT_E0311101 = "GPL-E03";// Footer document is not found

}
