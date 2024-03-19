package com.alchemy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.LevelDto;
import com.alchemy.entities.LevelEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListLevel;
import com.alchemy.repositories.LevelRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.serviceInterface.LevelInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class LevelServiceImpl implements LevelInterface {

	@Autowired
	private LevelRepository levelRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public LevelDto addLevel(LevelDto levelDto, Long userId) {
		LevelEntity level = this.levelRepository.findByLevelNameIgnoreCase(levelDto.getLevelName());
		if (level != null) {

			throw new ResourceNotFoundException(ErrorMessageCode.LEVEL_ALREADY_EXIST);
		}

		LevelEntity levelEntity = new LevelEntity();
		levelEntity.setLevelName(levelDto.getLevelName());
		levelEntity.setDescription(levelDto.getDescription());
		levelEntity.setCreatedBy(userId);
		this.levelRepository.save(levelEntity);
		return levelDto;
	}

	@Override
	public LevelDto updateLevel(LevelDto levelDto, Long id, Long userId) {
		LevelEntity levelEntity = this.levelRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEVEL_NOT_FOUND));

		LevelEntity level = this.levelRepository.findByLevelNameIgnoreCase(levelDto.getLevelName());
		if (level != null) {
			if (level.getId() != levelEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.LEVEL_ALREADY_EXIST);
			}
		}
		levelEntity.setLevelName(levelDto.getLevelName());
		levelEntity.setDescription(levelDto.getDescription());
		levelEntity.setUpdatedBy(userId);
		this.levelRepository.save(levelEntity);

		return levelDto;
	}

	@Override
	public void deleteLevelById(Long levelId) {

		LevelEntity levelEntity = levelRepository.findById(levelId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEVEL_NOT_FOUND));
		List<UserEntity> userList = this.userRepository.findByLevelId(levelEntity);
		for (int i = 0; i < userList.size(); i++) {
			userList.get(i).setLevelId(null);
		}
		this.userRepository.saveAll(userList);
		this.levelRepository.deleteById(levelId);
	}

	@Override
	public Page<IListLevel> getAllLevels(String search, String pageNo, String pageSize) throws Exception {

		Page<IListLevel> iListLevel;

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		if (search == "" || search == null || search.length() == 0) {

			iListLevel = this.levelRepository.findByOrderByIdDesc(pageable, IListLevel.class);
		} else {

			iListLevel = this.levelRepository.findByLevelNameContainingIgnoreCase(search, pageable, IListLevel.class);
		}
		return iListLevel;
	}

	@Override
	public List<IListLevel> findAllList(String search, Class<IListLevel> class1) {
		List<IListLevel> levelDto = this.levelRepository.findByOrderByIdDesc(IListLevel.class);
		return levelDto;
	}

}
