package com.alchemy.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alchemy.entities.ApiLoggerEntity;
import com.alchemy.repositories.ApiLoggerRepository;
import com.alchemy.serviceInterface.ApiLoggerInterface;

@Service("apiLoggerServiceImpl")
public class ApiLoggerServiceImpl implements ApiLoggerInterface {

	@Autowired
	private ApiLoggerRepository apiLoggerRepository;

	public ApiLoggerServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createApiLog(ApiLoggerEntity api) {

		apiLoggerRepository.save(api);
		

	}

}
