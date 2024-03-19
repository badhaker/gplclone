package com.alchemy.interceptors;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alchemy.entities.ApiLoggerEntity;
import com.alchemy.serviceInterface.ApiLoggerInterface;
import com.alchemy.utils.ApiUrls;

@Component
public class ApiLogger implements HandlerInterceptor {

	public ApiLogger() {
		super();

	}

	@Autowired
	private ApiLoggerInterface apiLoggerInterface;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		ArrayList<String> urlsWithoutHeader = new ArrayList<>(Arrays.asList(ApiUrls.URLS_WITHOUT_HEADER));

		final String requestUrl = request.getRequestURI();

		if (!urlsWithoutHeader.contains(requestUrl)) {

			ApiLoggerEntity apiDetail = new ApiLoggerEntity();
			apiDetail.setIpAddress(request.getRemoteAddr());
			apiDetail.setUrl(requestUrl);
			apiDetail.setMethod(request.getMethod());
			apiDetail.setHost(request.getRemoteHost());
//			apiDetail.setBody(request instanceof StandardMultipartHttpServletRequest ? null
//					: request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
			apiLoggerInterface.createApiLog(apiDetail);
			return true;

		}

		return true;

	}
}