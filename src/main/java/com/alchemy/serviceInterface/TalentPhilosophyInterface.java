package com.alchemy.serviceInterface;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.TalentPhilosophyDto;
import com.alchemy.iListDto.IListTalentPhilosophy;

public interface TalentPhilosophyInterface {

	TalentPhilosophyDto addTalentPhilosophy(TalentPhilosophyDto talentPhilosophyDto, MultipartFile file,
			HttpServletRequest request, Long userId) throws Exception;

	Page<IListTalentPhilosophy> getAlltalentPhilosophy(String search, String pageNo, String pageSize) throws Exception;
}
