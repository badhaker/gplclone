package com.alchemy.interceptors;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alchemy.entities.UserEntity;
import com.alchemy.entities.UserRoleEntity;
import com.alchemy.repositories.RolePermissionRepository;
import com.alchemy.repositories.RoleRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.repositories.UserRoleRepository;
import com.alchemy.serviceInterface.JwtTokenUtilInterface;
import com.alchemy.utils.ApiUrls;

@Component
public class AuthLogger implements HandlerInterceptor {
	@Autowired
	private JwtTokenUtilInterface tokenUtilInterface;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	RolePermissionRepository rolePermissionRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String authHeader = request.getHeader("Authorization");
		String tokenString = (null != authHeader) ? authHeader.split(" ")[1] : null;

		ArrayList<String> urlsWithoutHeader = new ArrayList<>(Arrays.asList(ApiUrls.URLS_WITHOUT_HEADER));
		final String requestUrl = request.getRequestURI();

		if (!urlsWithoutHeader.contains(requestUrl)) {

			if (null != tokenString) {
				final String emailString = tokenUtilInterface.getUsernameFromToken(tokenString);

				UserEntity userEntity = userRepository.findByEmail(emailString);

				if (null != userEntity) {
					ArrayList<UserRoleEntity> userRoleEntity = userRoleRepository.getRolesOfUser(userEntity.getId());

					ArrayList<String> roles = new ArrayList<>();

					for (int i = 0; i < userRoleEntity.size(); i++) {
						roles.add(userRoleEntity.get(i).getRoleId().getRoleName());
					}
					ArrayList<String> rolePermissionEntities = this.rolePermissionRepository
							.getPermissionOfUser(userEntity.getId());

					request.setAttribute("X-user-permission", rolePermissionEntities);
					request.setAttribute("X-user-roles", roles);
					request.setAttribute("X-user-id", userEntity.getId());

				}
			}
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
