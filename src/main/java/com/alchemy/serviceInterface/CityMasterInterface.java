package com.alchemy.serviceInterface;

import org.springframework.data.domain.Page;

import com.alchemy.dto.CityMasterDto;
import com.alchemy.dto.DeleteId;
import com.alchemy.iListDto.IListCityMaster;

public interface CityMasterInterface {

	public CityMasterDto addCity(CityMasterDto cityMasterDto, Long userId);

	public CityMasterDto editCity(Long id, CityMasterDto cityMasterDto, Long userId);

	public void deleteCity(Long id);

	Page<IListCityMaster> getAllCities(String search, String pageNo, String pageSize) throws Exception;

	public void deleteMultipleCities(DeleteId id, Long userId);

}
