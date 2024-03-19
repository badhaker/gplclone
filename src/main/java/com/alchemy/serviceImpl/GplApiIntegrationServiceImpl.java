package com.alchemy.serviceImpl;

import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alchemy.dto.JwtTokenResponse;
import com.alchemy.dto.ThirdParyApiLoginCredentialsDto;
import com.alchemy.entities.GplToken;
import com.alchemy.entities.MyResponse;
import com.alchemy.entities.ThirdPartyLoginCredentials;
import com.alchemy.entities.UserEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.repositories.GplTokenRepository;
import com.alchemy.repositories.ThirdPartyCredentialsRepo;
import com.alchemy.repositories.UserRepository;
import com.alchemy.serviceInterface.GplApiIntegrationInterface;
import com.alchemy.utils.CacheOperation;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class GplApiIntegrationServiceImpl implements GplApiIntegrationInterface {

	private final RestTemplate restTemplate;

//	@Autowired
//	GlobalFunctions functions;

	@Autowired
	private GplTokenRepository gplTokenRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ThirdPartyCredentialsRepo credentialsRepo;

	@Autowired
	private CacheOperation cacheOperation;

	private static final Logger LOG = LoggerFactory.getLogger(GplApiIntegrationServiceImpl.class);

	@Value("${jwt.secret}")
	private String secret;

	GplApiIntegrationServiceImpl() {
		restTemplate = new RestTemplate(new CustomHttpComponentsClientHttpRequestFactory());
	}

	private final String LOGIN_GPL_API = "https://api.godrejproperties.com:8085/api-container/signIn/authenticate";

	private final String GPL_API_FOR_EMPLOYEE_DATA = "https://api.godrejproperties.com:8085/api-container/getEmpData";

	@Override
	public ThirdParyApiLoginCredentialsDto addThirdParyApiLoginCredentials(
			@RequestBody ThirdParyApiLoginCredentialsDto dto) {

		ThirdPartyLoginCredentials entity = credentialsRepo.findByApiName(GlobalFunctions.SF_API);
		if (entity == null) {
			entity = new ThirdPartyLoginCredentials();
		}

		if (Validator.isValidforEmail(dto.getEmail())) {
			entity.setEmail(dto.getEmail().trim());
		} else {
			throw new IllegalArgumentException(ErrorMessageCode.INVALID_EMAIL);
		}
		entity.setApiName(dto.getApiName());
		entity.setApiUrl(dto.getApiUrl().trim());
		entity.setPassword(GlobalFunctions.encrypt(dto.getPassword(), secret));
		credentialsRepo.save(entity);

//		else {
//			throw new IllegalArgumentException("Login Credentials for this api already exists");
//		}
		return dto;

	}

	@SuppressWarnings("unchecked")
	@Override
	public JwtTokenResponse gplApiResponse() throws Exception {
		LOG.info("FETCH CREDENTIALS OF THIRD PARTY API");
		ThirdPartyLoginCredentials credentials = credentialsRepo.findByApiName(GlobalFunctions.SF_API);
		LOG.info("FETCH SUCCESSFULL");

		if (credentials == null) {
			throw new Exception("GPL SF credentials missing. Please contact the administrator.");

		}

		String decodedString = GlobalFunctions.decrypt(credentials.getPassword(), secret);

		String employeeData = "{\"email\":\"" + credentials.getEmail() + "\",\"password\":\"" + decodedString + "\"}";
		LOG.info("PAYLOAD : " + employeeData);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> employeeMap = new HashMap<>();
		try {
			employeeMap = mapper.readValue(employeeData, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String writeValueAsString = mapper.writeValueAsString(employeeMap);
		LOG.info("MAPPER VALUE AS STRING : " + writeValueAsString);

		HttpEntity<?> entity = new HttpEntity<>(writeValueAsString, headers);

		LOG.info("API CALL TO GET TOKEN - BODY : " + entity.getBody().toString());
		ResponseEntity<String> responseEntity = restTemplate.exchange(LOGIN_GPL_API, HttpMethod.POST, entity,
				String.class);

		LOG.info("RESPONSE : " + responseEntity.toString());
		JwtTokenResponse gplToken = mapper.readValue(responseEntity.getBody(), JwtTokenResponse.class);

		LOG.info("API CALL SUCCESS, TOKEN RETURNED : " + gplToken);
		return gplToken;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public MyResponse gplApiResponseDetail(String tokenEmail) throws Exception, HttpClientErrorException {
//
//		String gplMail = "nimap@godrejproperties.com";
//		String tempMail = tokenEmail.toLowerCase().trim().endsWith("godrejproperties.com") ? tokenEmail
//				: "rohit.soni@godrejproperties.com";
//		String employeeData = "{\"email\":\"" + tempMail + "\"}";
//		ObjectMapper mapper = new ObjectMapper();
//
//		String gplEmail = "gpl_" + tempMail;
//
//		LOG.info("tempMail :" + tempMail);
//		LOG.info("employeeData :" + employeeData);
//		LOG.info("gplEmail :" + gplEmail);
//
//		deleteMoreThanOneGplToken();
//				
//		LOG.info("IS REDIS CLOSED : " + cacheOperation.isConnectionClosed());
//		
//		final boolean isKeyExist = cacheOperation.isKeyExist(gplEmail, gplEmail);
//		LOG.info("IS KEY INSIDE REDIS : " + isKeyExist);
//		
//		if (!cacheOperation.isConnectionClosed() && !isKeyExist) {
//
//			MyResponse response = new MyResponse();
//
//			Map<String, Object> employeeMap = new HashMap<>();
//			try {
//				employeeMap = mapper.readValue(employeeData, Map.class);
//
//			} catch (IOException e) {
//				LOG.info("EXCEPTION");
//				e.printStackTrace();
//			}
//			
//			String writeValueAsString = mapper.writeValueAsString(employeeMap);
//			LOG.info("GET VALUE AS STRING : " + writeValueAsString);
//
//			GplToken dbToken = gplTokenRepository.findGplTokenByEmail(gplMail);
//			LOG.info("GPL TOKEN FETCHED");
//
//			if (dbToken != null) {
//				Date expiryDate = dbToken.getExpiredAt();
//
//				Date currentDate = new Date();
//
//				LOG.info("IS GPL TOKEN EXPIRED :" + expiryDate.compareTo(currentDate));
//				if (expiryDate.compareTo(currentDate) > 0) {
//
//					response = commonFuction(dbToken.getGplToken(), writeValueAsString, mapper);
//
//					if (response == null) {
//						throw new ResourceNotFoundException(
//								"No response from SF API. Please check if " + tokenEmail + " exists in SF data.");
//					}
//					cacheOperation.addInCacheTime(gplEmail, gplEmail, response.toString(), 4l);
//					LOG.info("RETURN");
//					return response;
//				} else {
//					LOG.info("If token is expired delete all token");
//					gplTokenRepository.deleteAll();
//				}
//
//			}
//
//			LOG.info("FOLLOWING CODE WILL RUN IF TOKEN IS NOT FOUND IN DB");
//			JwtTokenResponse gplToken = gplApiResponse();
//			LOG.info("STORE THE TOKEN IN DB");
//			gplTokenStore(gplToken.getToken(), gplMail);
//
//			response = commonFuction(gplToken.getToken(), writeValueAsString, mapper);
//			if (response == null) {
//				throw new ResourceNotFoundException(
//						"No response from SF API. Please check if " + tokenEmail + " exists in SF data.");
//			}
//			
//			LOG.info("RETURN");
//			return response;
//		}
////		if (!cacheOperation.isKeyExist(gplEmail, gplEmail)) {
////
////			MyResponse response = new MyResponse();
////
////			Map<String, Object> employeeMap = new HashMap<>();
////			try {
////				employeeMap = mapper.readValue(employeeData, Map.class);
////
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////			String writeValueAsString = mapper.writeValueAsString(employeeMap);
////
////			GplToken dbToken = gplTokenRepository.findGplTokenByEmail(gplMail);
////
////			if (dbToken != null) {
////				Date expiryDate = dbToken.getExpiredAt();
////
////				Date currentDate = new Date();
////
////				if (expiryDate.compareTo(currentDate) > 0) {
////
////					response = commonFuction(dbToken.getGplToken(), writeValueAsString, mapper);
////					if (response == null) {
////						throw new ResourceNotFoundException(
////								"No response from SF API. Please check if " + tokenEmail + " exists in SF data.");
////					}
////
////					cacheOperation.addInCacheTime(gplEmail, gplEmail, response.toString(), 4l);
////					return response;
////				} else {
////					gplTokenRepository.deleteAll();
////				}
////
////			}
////			JwtTokenResponse gplToken = gplApiResponse();
////			gplTokenStore(gplToken.getToken(), gplMail);
////
////			response = commonFuction(gplToken.getToken(), writeValueAsString, mapper);
////			if (response == null) {
////				throw new ResourceNotFoundException(
////						"No response from SF API. Please check if " + tokenEmail + " exists in SF data.");
////			}
////
////			cacheOperation.addInCacheTime(gplEmail, gplEmail, response.toString(), 4l);
////			return response;
////		} 
//		else {
//			LOG.info("FETCH DATA FROM CACHE");
//			String getResponse = (String) cacheOperation.getFromCache(gplEmail, gplEmail);
//
//			ObjectMapper objectMapper = new ObjectMapper();
//
//			MyResponse response = objectMapper.readValue(getResponse, MyResponse.class);
//			
//			LOG.info("RETURN");
//			return response;
//
//		}
//	}

	@Override
	public MyResponse gplApiResponseDetail(String tokenEmail) throws Exception, HttpClientErrorException {
		try {
			String gplMail = "nimap@godrejproperties.com";
			String tempMail = tokenEmail.toLowerCase().trim().endsWith("godrejproperties.com") ? tokenEmail
					: "rohit.soni@godrejproperties.com";
			String employeeData = "{\"email\":\"" + tempMail + "\"}";
			ObjectMapper mapper = new ObjectMapper();
			String gplEmail = "gpl_" + tempMail;

			LOG.info("tempMail :" + tempMail);
			LOG.info("employeeData :" + employeeData);
			LOG.info("gplEmail :" + gplEmail);

			deleteMoreThanOneGplToken();
			LOG.info("IS REDIS CLOSED : " + cacheOperation.isConnectionClosed());

			MyResponse response = fetchDataFromCacheOrExternalSource(gplEmail, gplEmail, employeeData, mapper, gplMail);

			if (response == null) {
				throw new ResourceNotFoundException(
						"No response from SF API. Please check if " + tokenEmail + " exists in SF data.");
			}

			LOG.info("RETURN");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private MyResponse fetchDataFromCacheOrExternalSource(String cacheKey, String cacheValue, String employeeData,
			ObjectMapper mapper, String gplMail) throws Exception {
		if (cacheOperation.isConnectionClosed()) {
			boolean isKeyExist = cacheOperation.isKeyExist(cacheKey, cacheValue);
			LOG.info("IS KEY INSIDE REDIS : " + isKeyExist);

			if (!isKeyExist) {
				return fetchAndCacheData(cacheKey, cacheValue, employeeData, mapper, gplMail);
			} else {
				LOG.info("FETCH DATA FROM CACHE");
				String getResponse = (String) cacheOperation.getFromCache(cacheKey, cacheValue);
				return mapper.readValue(getResponse, MyResponse.class);
			}
		} else {
			return fetchAndCacheData(cacheKey, cacheValue, employeeData, mapper, gplMail);
		}
	}

	@SuppressWarnings("unchecked")
	private MyResponse fetchAndCacheData(String cacheKey, String cacheValue, String employeeData, ObjectMapper mapper,
			String gplMail) throws Exception {
		Map<String, Object> employeeMap = new HashMap<>();
		try {
			employeeMap = mapper.readValue(employeeData, Map.class);
		} catch (IOException e) {
			LOG.info("EXCEPTION");
			e.printStackTrace();
		}

		String writeValueAsString = mapper.writeValueAsString(employeeMap);
		LOG.info("GET VALUE AS STRING : " + writeValueAsString);

		GplToken dbToken = gplTokenRepository.findGplTokenByEmail(gplMail);
		LOG.info("GPL TOKEN FETCHED");

		if (dbToken != null) {
			Date expiryDate = dbToken.getExpiredAt();
			Date currentDate = new Date();

			LOG.info("IS GPL TOKEN EXPIRED :" + expiryDate.compareTo(currentDate));
			if (expiryDate.compareTo(currentDate) > 0) {
				MyResponse response = commonFuction(dbToken.getGplToken(), writeValueAsString, mapper);
				if (response != null) {
					if (cacheOperation.isConnectionClosed()) {
						cacheOperation.addInCacheTime(cacheKey, cacheValue, response.toString(), 4L);
					}
				}
				return response;
			} else {
				LOG.info("If token is expired delete all tokens");
				long count = this.gplTokenRepository.count();
				if (count > 0) {
					this.gplTokenRepository.deleteAll();
				}

			}
		}

		LOG.info("FOLLOWING CODE WILL RUN IF TOKEN IS NOT FOUND IN DB");
		JwtTokenResponse gplToken = gplApiResponse();
		LOG.info("STORE THE TOKEN IN DB");
		gplTokenStore(gplToken.getToken(), gplMail);

		MyResponse response = commonFuction(gplToken.getToken(), writeValueAsString, mapper);
		if (response != null) {
			if (cacheOperation.isConnectionClosed()) {
				cacheOperation.addInCacheTime(cacheKey, cacheValue, response.toString(), 4L);
			}
		}
		return response;
	}

//	private  MyResponse myResponse(String gplMail,String tokenEmail,String employeeData,ObjectMapper mapper) {
	@SuppressWarnings("unchecked")
	// MyResponse response = new MyResponse();
//
//		Map<String, Object> employeeMap = new HashMap<>();
//		try {
//			employeeMap = mapper.readValue(employeeData, Map.class);
//
//		} catch (IOException e) {
//			LOG.info("EXCEPTION");
//			e.printStackTrace();
//		}
//
//		String writeValueAsString = mapper.writeValueAsString(employeeMap);
//		LOG.info("GET VALUE AS STRING : " + writeValueAsString);
//
//		GplToken dbToken = gplTokenRepository.findGplTokenByEmail(gplMail);
//		LOG.info("GPL TOKEN FETCHED");
//
//		if (dbToken != null) {
//			Date expiryDate = dbToken.getExpiredAt();
//
//			Date currentDate = new Date();
//
//			LOG.info("IS GPL TOKEN EXPIRED :" + expiryDate.compareTo(currentDate));
//			if (expiryDate.compareTo(currentDate) > 0) {
//
//				response = commonFuction(dbToken.getGplToken(), writeValueAsString, mapper);
//
//				if (response == null) {
//					throw new ResourceNotFoundException(
//							"No response from SF API. Please check if " + tokenEmail + " exists in SF data.");
//				}
//				cacheOperation.addInCacheTime(gplEmail, gplEmail, response.toString(), 4l);
//				LOG.info("RETURN");
//				return response;
//			} else {
//				LOG.info("If token is expired delete all token");
//				gplTokenRepository.deleteAll();
//			}
//
//		}
//
//		LOG.info("FOLLOWING CODE WILL RUN IF TOKEN IS NOT FOUND IN DB");
//		JwtTokenResponse gplToken = gplApiResponse();
//		LOG.info("STORE THE TOKEN IN DB");
//		gplTokenStore(gplToken.getToken(), gplMail);
//
//		response = commonFuction(gplToken.getToken(), writeValueAsString, mapper);
//		if (response == null) {
//			throw new ResourceNotFoundException(
//					"No response from SF API. Please check if " + tokenEmail + " exists in SF data.");
//		}
//
//		LOG.info("RETURN");
//		return response;
//	}
	@Override
	public MyResponse gplAzureADSFDetail(String email) throws Exception {

		String gplMail = "nimap@godrejproperties.com";

		String employeeData = "{\"email\":\"" + email + "\"}";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> employeeMap = new HashMap<>();
		try {
			employeeMap = mapper.readValue(employeeData, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String writeValueAsString = mapper.writeValueAsString(employeeMap);
		GplToken dbToken = gplTokenRepository.findGplTokenByEmail(gplMail);

		if (dbToken != null) {
			Date expiryDate = dbToken.getExpiredAt();
			Date currentDate = new Date();
			if (expiryDate.compareTo(currentDate) > 0) {

				ResponseEntity<String> response = restTemplate.getForEntity(GPL_API_FOR_EMPLOYEE_DATA, String.class);
				UserEntity userEntity = this.userRepository.findByEmailIgnoreCase(email);
				if (userEntity == null) {
					if (response.getStatusCode() == HttpStatus.OK) {
						UserEntity entity = new UserEntity();
						entity.setEmail(email);
						entity.setIsAdmin(false);
						userRepository.save(entity);
					} else {
						throw new IllegalArgumentException("Invalid email or password");
					}
				} else {
					return commonFuction(dbToken.getGplToken(), writeValueAsString, mapper);
				}

				return commonFuction(dbToken.getGplToken(), writeValueAsString, mapper);
			} else {
				gplTokenRepository.deleteAll();
				JwtTokenResponse toeknGpl = gplApiResponse();
				gplTokenStore(toeknGpl.getToken(), gplMail);
				return commonFuction(toeknGpl.getToken(), writeValueAsString, mapper);
			}
		}
		JwtTokenResponse gplToken = gplApiResponse();
		gplTokenStore(gplToken.getToken(), gplMail);

		return commonFuction(gplToken.getToken(), writeValueAsString, mapper);

	}

	private MyResponse commonFuction(String tokenGpl, String writeValueAsString, ObjectMapper mapper)
			throws JsonMappingException, JsonProcessingException {
		try {
			String token = "Bearer " + tokenGpl;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", token);
			HttpEntity<String> request = new HttpEntity<>(writeValueAsString, headers);

			LOG.info("FETCH EMPLOYEE DATA");
			ResponseEntity<String> response = restTemplate.exchange(GPL_API_FOR_EMPLOYEE_DATA, HttpMethod.GET, request,
					String.class);

			LOG.info("DATA FETCHED SUCCESSFULLY");
			String responceData = response.getBody();
			MyResponse readValue = mapper.readValue(responceData, MyResponse.class);
			return readValue;
		} catch (Exception e) {
			MyResponse readValue = null;
			LOG.info("ERROR IN FETCHING DATA");
			return readValue;
		}
	}

	public void gplTokenStore(String gplToken, String email) {
		long count = this.gplTokenRepository.count();
		if (count > 0) {
			this.gplTokenRepository.deleteAll();
		}
		GplToken tokenGplToken = new GplToken();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, 23);
		tokenGplToken.setExpiredAt(cal.getTime());
		tokenGplToken.setGplToken(gplToken);
		tokenGplToken.setEmail(email);
		this.gplTokenRepository.save(tokenGplToken);

	}

	private void deleteMoreThanOneGplToken() {
		long tokenCount = gplTokenRepository.count();
		if (tokenCount > 1) {
			gplTokenRepository.deleteAll();
		}

	}

	private static final class CustomHttpComponentsClientHttpRequestFactory
			extends HttpComponentsClientHttpRequestFactory {

		@Override
		protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {

			if (HttpMethod.GET.equals(httpMethod)) {
				return new HttpEntityEnclosingGetRequestBase(uri);
			}
			return super.createHttpUriRequest(httpMethod, uri);
		}
	}

	private static final class HttpEntityEnclosingGetRequestBase extends HttpEntityEnclosingRequestBase {

		public HttpEntityEnclosingGetRequestBase(final URI uri) {
			super.setURI(uri);
		}

		@Override
		public String getMethod() {
			return HttpMethod.GET.name();
		}
	}

}
