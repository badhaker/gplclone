package com.alchemy.serviceImpl;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.Deflater;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.alchemy.dto.AssignRole;
import com.alchemy.dto.AuthResponseDto;
import com.alchemy.entities.InviteEntity;
import com.alchemy.entities.LoggerEntity;
import com.alchemy.entities.RoleEntity;
import com.alchemy.entities.SystemConfiguration;
import com.alchemy.entities.UserEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.repositories.AuthRepository;
import com.alchemy.repositories.InviteReposiotry;
import com.alchemy.repositories.LoggerRepository;
import com.alchemy.repositories.RoleRepository;
import com.alchemy.repositories.SystemConfigurationRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.serviceInterface.AuthInterface;
import com.alchemy.serviceInterface.JwtTokenUtilInterface;
import com.alchemy.serviceInterface.SSOInterface;
import com.alchemy.serviceInterface.UserRoleInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;

@Service
public class SSOServiceImpl implements SSOInterface {

	@Value("${sso.tenant-id}")
	private String TENANT_ID;

	@Value("${sso.assertion-service-url}")
	private String ASSERTION_URL;

	@Value("${sso.issuer}")
	private String ISSUER;

	@Autowired
	private SystemConfigurationRepository systemConfigurationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleInterface userRoleInterface;

	@Autowired
	private AuthInterface authInterface;

	@Autowired
	private JwtTokenUtilInterface jwtTokenUtilInterface;

	@Autowired
	private LoggerRepository loggerRepository;

	@Autowired
	private InviteReposiotry inviteReposiotry;

	@Override
	public String generateRedirectUrl() {

		String redirectUrl = "https://login.microsoftonline.com/" + TENANT_ID + "/saml2" + "?SAMLRequest=";

		String samlRequest = "<samlp:AuthnRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" "
				+ "xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\" " + "ID=\"" + "_" + UUID.randomUUID().toString()
				+ "\" " + "Version=\"2.0\" " + "IssueInstant=\"" + DateTime.now() + "\" "
				+ "ProtocolBinding=\"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST\" "
				+ "AssertionConsumerServiceURL=\"" + ASSERTION_URL + "\">" + "<saml:Issuer>" + ISSUER + "</saml:Issuer>"
				+ "<samlp:NameIDPolicy Format=\"urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified\"/>"
				+ "</samlp:AuthnRequest>";

		byte[] compressedBytes = new byte[samlRequest.length()];
		Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION, true);
		deflater.setInput(samlRequest.getBytes());
		deflater.finish();
		int compressedSize = deflater.deflate(compressedBytes);
		deflater.end();

		String base64Encoded = Base64.getEncoder()
				.encodeToString(Arrays.copyOfRange(compressedBytes, 0, compressedSize));

		String encodedString = UriUtils.encode(base64Encoded, StandardCharsets.UTF_8);
		String relayStateString = redirectUrl + encodedString;
		return relayStateString;
	}

	@Override
	public String ssoAuthentication(String samlResponse)
			throws ParserConfigurationException, SAXException, IOException {
		System.out.println("ENTERED SERVICE FILE " + samlResponse);

		byte[] decodedBytes = Base64.getDecoder().decode(samlResponse);// Decode the Base64 string
		String decodedString = new String(decodedBytes); // Convert the decoded bytes to a string
		System.out.println("RESPONSE DECODED " + decodedString);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		System.out.println("DocumentBuilderFactory instance created " + decodedString);

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			System.out.println("NEW BUILDER created " + decodedString);

			Document document = builder.parse(new InputSource(new StringReader(decodedString)));

			System.out.println("DOCUMENT PARSE SUCCESSFUL " + document.toString());

			NodeList signatureFromXml = document.getElementsByTagName("SignatureValue");
			NodeList attributes = document.getElementsByTagName("Attribute");
			NodeList certificateFromXml = document.getElementsByTagName("X509Certificate");
			NodeList statusFromXml = document.getElementsByTagName("samlp:Status");

			System.out.println("STATUS EXTRACTED " + statusFromXml);

			Element certificateElement = (Element) certificateFromXml.item(0);
			String certificate = certificateElement.getTextContent();

			Element statusCodeElement = (Element) document.getElementsByTagName("samlp:StatusCode").item(0);

			String status = statusCodeElement.getAttribute("Value");

			System.out.println("statusElement " + status);

//			Element statusElement = (Element) statusFromXml.item(0);
//			String stateName = statusElement.getAttribute("Name");
//			String status = statusElement.getTextContent();
//			System.out.println("status " + status.toString());

//			Element signatureElement = (Element) signatureFromXml.item(0);
//			String signature = signatureElement.getTextContent();

			if (status.equals("urn:oasis:names:tc:SAML:2.0:status:Success")) {
				String email = null;
				for (int i = 0; i < attributes.getLength(); i++) {
					Element attribute = (Element) attributes.item(i);
					String name = attribute.getAttribute("Name");
					if (name.equals("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress")) {
						email = attribute.getElementsByTagName("AttributeValue").item(0).getTextContent();
					}
				}

				if (email == null) {
					return null;
				}

				// Certificate match logic
				SystemConfiguration certFormDB = systemConfigurationRepository.findByKey(Constant.SAML_CERTIFICATE);
				if (certificate.equals(certFormDB.getValue())) {
					return email;
				} else {
					return null;
				}
			}

		} catch (ParserConfigurationException e) {
			// Handle configuration exceptions
			e.printStackTrace();
		} catch (SAXException e) {
			// Handle parsing exceptions
			e.printStackTrace();
		} catch (IOException e) {
			// Handle I/O exceptions
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// Handle invalid arguments
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public AuthResponseDto generateAccessToken(String email) throws Exception {

		UserEntity userEntity = this.userRepository.findByEmailIgnoreCase(email);

		if (userEntity == null) {
			// Get details from SF API
			// We need to set email here since saveGplEmployeeDetail functions expects email
			// to be present in userEntity
			userEntity = new UserEntity();
			userEntity.setEmail(email);
			userEntity.setPassword("");
			this.userRepository.save(userEntity);

			authServiceImpl.saveGplEmployeeDetail(userEntity);

			RoleEntity role = roleRepository.findByRoleNameIgnoreCaseAndIsActiveTrue("USER");

			ArrayList<Long> array = new ArrayList<>();
			array.add(role.getId());

			AssignRole addRole = new AssignRole(userEntity.getId(), array);

			userRoleInterface.add(addRole);

		}
		final UserDetails userDetails = this.authInterface.loadUserByUsername(email);

		final String token = this.jwtTokenUtilInterface.generateToken(userDetails);

		ArrayList<String> permissions = authInterface.getPermissionByUser(userEntity.getId());

		List<String> roles = this.userRoleInterface.getRoleByUserId(userEntity.getId());

		String refreshToken = jwtTokenUtilInterface.refreshToken(token, userDetails);

		LoggerEntity loggerEntity = loggerRepository.getLog(userEntity.getId());

		if (loggerEntity == null) {
			loggerEntity = new LoggerEntity();
		}
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		loggerEntity.setLoginAt(date);
		cal.add(Calendar.HOUR_OF_DAY, 20);
		loggerEntity.setUserId(userEntity);
		authInterface.saveGplEmployeeDetail(userEntity);

		loggerRepository.save(loggerEntity);

		SystemConfiguration configuration = systemConfigurationRepository.findByKey(Constant.CAREER_ASPIRATION);

		return new AuthResponseDto(token, refreshToken, permissions, userEntity.getEmail(), userEntity.getName(),
				userEntity.getId(), roles, configuration.getFlag());
	}

	@Override
	public AuthResponseDto validateUUID(String uuid) throws HttpClientErrorException, Exception {

		InviteEntity inviteEntity = this.inviteReposiotry.findbyUuid(UUID.fromString(uuid));

		if (inviteEntity == null) {
			throw new ResourceNotFoundException(ErrorMessageCode.INVALID_UUID);
		}

		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime()); // assuming date is initialized as in your example
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.add(Calendar.MINUTE, 5);
		ts.setTime(cal.getTimeInMillis());

		if (ts.compareTo(inviteEntity.getCreatedAt()) == -1) {
			throw new ResourceNotFoundException(ErrorMessageCode.INVALID_UUID);
		}

		UserEntity userEntity = this.authRepository.findById(inviteEntity.getUserId()).get();

		inviteReposiotry.delete(inviteEntity);

		final UserDetails userDetails = this.authInterface.loadUserByUsername(userEntity.getEmail());

		final String token = this.jwtTokenUtilInterface.generateToken(userDetails);

		ArrayList<String> permissions = authInterface.getPermissionByUser(userEntity.getId());

		List<String> roles = this.userRoleInterface.getRoleByUserId(userEntity.getId());

		String refreshToken = jwtTokenUtilInterface.refreshToken(token, userDetails);

		LoggerEntity loggerEntity = loggerRepository.getLog(userEntity.getId());

		if (loggerEntity == null) {
			loggerEntity = new LoggerEntity();
		}

		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		loggerEntity.setLoginAt(today);
		calendar.add(Calendar.HOUR_OF_DAY, 20);
		loggerEntity.setUserId(userEntity);

		authInterface.saveGplEmployeeDetail(userEntity);

		loggerRepository.save(loggerEntity);

		SystemConfiguration configuration = systemConfigurationRepository.findByKey(Constant.CAREER_ASPIRATION);

		return new AuthResponseDto(token, refreshToken, permissions, userEntity.getEmail(), userEntity.getName(),
				userEntity.getId(), roles, configuration.getFlag());

	}

}
