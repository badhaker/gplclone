package com.alchemy.exceptionHandling;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.entities.ErrorLoggerEntity;
import com.alchemy.entities.MethodEnum;
import com.alchemy.repositories.ErrorLoggerRepository;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@Autowired
	private ErrorLoggerRepository errorLoggerRepository;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {

		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {

			details.add(error.getDefaultMessage());
		}
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(details.get(0).split("\\*", 2)[0]);
		error.setMsgKey(details.get(0).split("\\*", 2)[1]);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponseDto handledMaxUploadSizeExceededException(
			final MaxUploadSizeExceededException exceededException) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(ErrorMessageCode.FILE_SIZE_TOO_LARGE);
		error.setMsgKey(ErrorMessageKey.GLOBAL_EXCEPTION_E032101);
		return error;

	}

	@ExceptionHandler(IOException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponseDto handledMaxUploadSizeExceededException(final IOException ioException) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(ErrorMessageCode.FILE_NOT_UPLOADED);
		error.setMsgKey(ErrorMessageKey.GLOBAL_EXCEPTION_E032102);
		return error;

	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public @ResponseBody ErrorResponseDto handleHttpRequestMethodNotSupportedException(
			final HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {

		ErrorResponseDto errorResponseDto = new ErrorResponseDto();
		errorResponseDto.setMessage(ErrorMessageCode.METHOD_NOT_SUPPORTED);
		errorResponseDto.setMsgKey(ErrorMessageKey.GLOBAL_EXCEPTION_E032103);

		return errorResponseDto;

	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public @ResponseBody ErrorResponseDto handleAccessDeniedException(final AccessDeniedException exception) {

		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(ErrorMessageCode.ACCESS_DENIED);
		error.setMsgKey(ErrorMessageKey.GLOBAL_EXCEPTION_E032104);
		return error;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponseDto noHandlerFoundException(final NoHandlerFoundException exception) {

		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(ErrorMessageCode.METHOD_NOT_SUPPORTED);
		error.setMsgKey(ErrorMessageKey.GLOBAL_EXCEPTION_E032105);
		return error;

	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponseDto handleException(final Exception exception, HttpServletRequest request)
			throws IOException {

		ErrorLoggerEntity errorRequest = new ErrorLoggerEntity();
		errorRequest.setBody(request instanceof StandardMultipartHttpServletRequest ? null
				: request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		errorRequest.setHost(InetAddress.getLoopbackAddress().getHostAddress());
		errorRequest.setMessage(exception.getMessage());
		errorRequest.setMethod(Enum.valueOf(MethodEnum.class, request.getMethod()));
		errorRequest.setUrl(request.getRequestURI());
		errorLoggerRepository.save(errorRequest);

		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(ErrorMessageCode.SOMETHING_WENT_WRONG);
		error.setMsgKey(ErrorMessageKey.GLOBAL_EXCEPTION_E032106);
		return error;

	}

	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public @ResponseBody ErrorResponseDto handleJwtExpiredException(final ExpiredJwtException jwtExpired) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(ErrorMessageCode.JWT_EXPIRED);
		error.setMsgKey(ErrorMessageKey.GLOBAL_EXCEPTION_E032108);
		return error;
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponseDto methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(ErrorMessageCode.INVALID_URI);
		error.setMsgKey(ErrorMessageKey.GLOBAL_EXCEPTION_E032107);
		return error;
	}

	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorResponseDto tokenNotApplied(IllegalStateException exception) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(ErrorMessageCode.MISSING_JWT);
		error.setMsgKey(ErrorMessageKey.GLOBAL_EXCEPTION_E032109);
		return error;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	protected ErrorResponseDto ErrorResponseDtohandleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(ErrorMessageCode.INVALID_FORMAT);
		error.setMsgKey(ErrorMessageKey.GLOBAL_EXCEPTION_E032110);
		return error;
	}

//	@ExceptionHandler(BindException.class)
//	public ResponseEntity<ErrorResponseDto> handleBindException(BindException ex) {
//		BindingResult bindingResult = ex.getBindingResult();
//		List<String> errors = bindingResult.getAllErrors().stream()
//				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
//
//		ErrorResponseDto error = new ErrorResponseDto();
//		String[] firstErrorParts = errors.get(0).split("\\*", 2);
//		error.setMessage(firstErrorParts[0]);
//		error.setMsgKey(firstErrorParts[1]);
//
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponseDto> handleBindException(BindException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<String> errors = bindingResult.getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

		ErrorResponseDto error = new ErrorResponseDto();

		if (errors.get(0).startsWith("Failed to convert property value")) {
			FieldError fieldError = bindingResult.getFieldError();
			String fieldName = fieldError != null ? fieldError.getField() : "";
			String enumClassName = bindingResult.getFieldValue(fieldName).getClass().getSimpleName();
			error.setMessage("Invalid input for field '" + fieldName + "'");
			error.setMsgKey(enumClassName.toLowerCase() + ".invalid");
		} else {
			String[] firstErrorParts = errors.get(0).split("\\*", 2);
			error.setMessage(firstErrorParts[0]);
			error.setMsgKey(firstErrorParts[1]);
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
