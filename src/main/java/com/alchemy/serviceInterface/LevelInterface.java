package com.alchemy.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;
import com.alchemy.dto.LevelDto;
import com.alchemy.iListDto.IListLevel;

public interface LevelInterface {

	LevelDto addLevel(LevelDto levelDto,Long userId);
	
	LevelDto updateLevel(LevelDto levelDto ,Long id,Long userId);
	
	public void deleteLevelById(Long levelId);
	
	Page<IListLevel> getAllLevels(String search, String pageNo, String pageSize) throws Exception;

	List<IListLevel> findAllList(String search, Class<IListLevel> class1);
}
