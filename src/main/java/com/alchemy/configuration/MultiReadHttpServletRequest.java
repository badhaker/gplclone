//package com.alchemy.configuration;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//
//import org.springframework.util.StreamUtils;
//import org.springframework.util.StringUtils;
//
//public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
//
//	private byte[] cacheBody;
//
//	public MultiReadHttpServletRequest(HttpServletRequest request) throws IOException {
//		super(request);
//		System.err.println("CONTENT " + request.getContentType());
//		if ((request.getContentType() == null)
//				|| (request.getContentType().toLowerCase().indexOf("multipart/form-data") <= -1)
//				|| (request.getContentType().toLowerCase().indexOf("application/x-www-form-urlencoded") <= -1)) {
//			InputStream inputStream = request.getInputStream();
//			this.cacheBody = StreamUtils.copyToByteArray(inputStream);
//
//		}
//	}
//
//	@Override
//	public ServletInputStream getInputStream() throws IOException {
//		return new CachedBodyServletInputStream(cacheBody);
//
//	}
//
//	@Override
//	public BufferedReader getReader() throws IOException {
//		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cacheBody);
//		String encoding = StringUtils.isEmpty(this.getCharacterEncoding()) ? StandardCharsets.UTF_8.name()
//				: this.getCharacterEncoding();
//		return new BufferedReader(new InputStreamReader(byteArrayInputStream, encoding));
//
//	}
//
//}
