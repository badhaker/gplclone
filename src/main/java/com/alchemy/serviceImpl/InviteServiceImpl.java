package com.alchemy.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alchemy.entities.InviteEntity;
import com.alchemy.repositories.InviteReposiotry;
import com.alchemy.serviceInterface.InviteServiceInterface;

@Service
public class InviteServiceImpl implements InviteServiceInterface {

	@Autowired
	private InviteReposiotry inviteReposiotry;

	@Override
	public void add(UUID uuid, Long userId) {
		try {

			InviteEntity inviteEntity = new InviteEntity();
			inviteEntity.setUserId(userId);
			inviteEntity.setCode(uuid);

			this.inviteReposiotry.save(inviteEntity);

		} catch (Exception e) {
		}

	}

	@Override
	public void deleteCodeByUser(Long userId) {

		this.inviteReposiotry.deleteByUserId(userId);

	}
}
