package com.alchemy.serviceInterface;

import org.springframework.stereotype.Service;

import com.alchemy.entities.ApiLoggerEntity;


@Service
public interface ApiLoggerInterface {

	void createApiLog(ApiLoggerEntity api);

}
