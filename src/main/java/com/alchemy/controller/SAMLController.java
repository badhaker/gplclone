package com.alchemy.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alchemy.dto.AuthResponseDto;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.ValidateUUID;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.serviceInterface.InviteServiceInterface;
import com.alchemy.serviceInterface.SSOInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.SAML)
public class SAMLController {

	@Value("${sso.redirect.url}")
	private String REDIRECT_URL;

	@Autowired
	SSOInterface ssoInterface;

	@Autowired
	InviteServiceInterface inviteServiceInterface;

	@PostMapping(path = "/response", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public void processSamlResponse(HttpServletResponse response, @RequestBody MultiValueMap<String, String> paramMap)
			throws Exception {

		try {
			String samlResponse = paramMap.get("SAMLResponse").get(0);

			System.out.println("LOOP BREAK SUCCESSFUL " + samlResponse);

			String email = ssoInterface.ssoAuthentication(samlResponse);

			System.out.println("EMAIL :" + email);

			if (email != null) {
				AuthResponseDto authResponseDto = ssoInterface.generateAccessToken(email);
				UUID uuid = UUID.randomUUID();

				inviteServiceInterface.deleteCodeByUser(authResponseDto.getId());
				inviteServiceInterface.add(uuid, authResponseDto.getId());
//			response.addCookie(new Cookie("accessToken", authResponseDto.getJwtToken()));
//			response.addCookie(new Cookie("refreshToken", authResponseDto.getRefreshToken()));
//			response.addCookie(new Cookie("email", authResponseDto.getEmail()));
//			response.addCookie(new Cookie("name", authResponseDto.getName().split(" ")[0]));
//			response.addCookie(new Cookie("careerAspiration", authResponseDto.getCareerAspiration()));
//			response.addCookie(
//					new Cookie("updateCareerAspiration", authResponseDto.getUpdateCareerAspiration().toString()));
//			response.addCookie(new Cookie("id", authResponseDto.getId().toString()));
//			response.addCookie(new Cookie("roles", authResponseDto.getRoles().toString()));
//			response.addCookie(new Cookie("permissions", authResponseDto.getPermission().toString()));
				response.sendRedirect(REDIRECT_URL + uuid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/login")
	public ResponseEntity<?> generateSAMLRequest(HttpServletResponse response) throws IOException {

		String url = ssoInterface.generateRedirectUrl();

		return new ResponseEntity<>(
				new SuccessResponseDto(SuccessMessageCode.BANNER_ADDED, SuccessMessageKey.BANNER_M032104, url),
				HttpStatus.OK);
	}

	@PostMapping("/validate")
	public ResponseEntity<?> generateSAMLRequest(@RequestBody ValidateUUID dto) throws IOException {

		if (dto.getUuid() == null) {
			throw new ResourceNotFoundException(ErrorMessageCode.INVALID_UUID);
		}

		try {
			AuthResponseDto authResponseDto = ssoInterface.validateUUID(dto.getUuid());
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.SUCCESS, SuccessMessageKey.USER_M031102, authResponseDto),
					HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ErrorResponseDto("Invalid user", "Invalid user"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.print("HEHE" + e);
			e.printStackTrace();
			return new ResponseEntity<>(new ErrorResponseDto("Invalid user", "Invalid user"), HttpStatus.BAD_REQUEST);
		}
	}

}
