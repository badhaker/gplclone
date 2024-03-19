package com.alchemy.serviceInterface;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public interface InviteServiceInterface {
	public void add(UUID uuid, Long userId);

	public void deleteCodeByUser(Long userId);

}
