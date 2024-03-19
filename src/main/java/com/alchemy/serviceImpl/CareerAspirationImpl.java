package com.alchemy.serviceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.AspirationDto;
import com.alchemy.dto.CareerAspExportDto;
import com.alchemy.dto.CareerAspirationPrefereceDto;
import com.alchemy.dto.CityList;
import com.alchemy.dto.DeleteId;
import com.alchemy.entities.CareerAspirationEntity;
import com.alchemy.entities.CityMasterEntity;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.entities.GplDepartmentEntity;
import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.entities.GplRoleEntity;
import com.alchemy.entities.PreferenceEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListCareerAspiration;
import com.alchemy.iListDto.IListCareerAspirationPreference;
import com.alchemy.iListDto.PreferenceList;
import com.alchemy.repositories.CareerAspirationRepository;
import com.alchemy.repositories.CityMasterRepository;
import com.alchemy.repositories.GplDepartmentRepository;
import com.alchemy.repositories.GplFunctionRepository;
import com.alchemy.repositories.GplRoleRepository;
import com.alchemy.repositories.PreferencesRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.serviceInterface.CareerAspirationInterface;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.Validator;

@Service
public class CareerAspirationImpl implements CareerAspirationInterface {

	@Autowired
	private CareerAspirationRepository careerAspirationRepository;

	@Autowired

	private PreferencesRepository preferencesRepository;

	@Autowired
	private GplDepartmentRepository gplDepartmentRepository;

	@Autowired
	private GplFunctionRepository gplFunctionRepository;

	@Autowired
	private GplRoleRepository gplRoleRepository;

	@Autowired
	private FileUploadInterface fileUploadInterface;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CityMasterRepository cityMasterRepository;

	@Override
	public AspirationDto addaspiration(MultipartFile file, HttpServletRequest request,
			@Valid AspirationDto aspirationDto, Long userId) throws Exception {

		UserEntity entity = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USER_NOT_FOUND));

		CareerAspirationEntity aspirationEntity = this.careerAspirationRepository.findByUserId(entity);

		if (aspirationEntity == null) {

			CareerAspirationEntity newAspirationEntity = new CareerAspirationEntity();

			newAspirationEntity.setAdditionalDetails(aspirationDto.getDetails());

			if (aspirationDto.getNextcareerMove()) {

				if (aspirationDto.getCity1() == null) {
					throw new IllegalArgumentException("City1 fiels is required");
				}

				CityMasterEntity city1 = this.cityMasterRepository.findById(aspirationDto.getCity1())
						.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.CITY_NOT_PRESENT));
				newAspirationEntity.setCity1(city1);

				if (aspirationDto.getCity2() != null) {
					CityMasterEntity city2 = this.cityMasterRepository.findById(aspirationDto.getCity2())
							.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.CITY_NOT_PRESENT));
					newAspirationEntity.setCity2(city2);

				}
				if (aspirationDto.getCity3() != null) {
					CityMasterEntity city3 = this.cityMasterRepository.findById(aspirationDto.getCity3())
							.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.CITY_NOT_PRESENT));
					newAspirationEntity.setCity3(city3);
				}
			}

			newAspirationEntity.setNextcareerMove(aspirationDto.getNextcareerMove());
			newAspirationEntity.setUserId(entity);
			newAspirationEntity.setCreatedBy(userId);

			if (file != null && !file.isEmpty()) {

				if (Validator.isValidforPdf(file)) {
					FileUploadEntity uploadEntity = fileUploadInterface.storeFile(file, request);
					newAspirationEntity.setFile(uploadEntity);
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.UPLOAD_PDF);
				}

			} else {
				throw new FileNotFoundException(ErrorMessageCode.FILE_REQUIRED);

			}

			careerAspirationRepository.save(newAspirationEntity);

			PreferenceEntity preferenceEntity1 = new PreferenceEntity();

			GplFunctionEntity functionEntity = gplFunctionRepository.findById(aspirationDto.getFunctionId1())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND));
			GplDepartmentEntity departmentEntity = gplDepartmentRepository.findById(aspirationDto.getDepartmentId1())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DEPARTMENT_NOT_FOUND));
			GplRoleEntity roleEntity = gplRoleRepository.findById(aspirationDto.getRoleId1())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ROLE_NOT_FOUND));

			preferenceEntity1.setCareerAspirationId(newAspirationEntity);
			preferenceEntity1.setFunctionId(functionEntity);
			preferenceEntity1.setDepartmentId(departmentEntity);
			preferenceEntity1.setRoleId(roleEntity);
			preferenceEntity1.setExperience(aspirationDto.getExperience1());
			preferenceEntity1.setCreatedBy(userId);

			Boolean isSecondPreference = false;
			PreferenceEntity preferenceEntity2 = new PreferenceEntity();

			// 2nd Preference
			if (aspirationDto.getFunctionId2() != null || aspirationDto.getDepartmentId2() != null
					|| aspirationDto.getRoleId2() != null || aspirationDto.getExperience2() != null) {

				isSecondPreference = true;

				preferenceEntity2 = checkSecondPrefrencAndSave(preferenceEntity2, aspirationDto);

				preferenceEntity2.setCareerAspirationId(newAspirationEntity);
				preferenceEntity2.setExperience(aspirationDto.getExperience2());
				preferenceEntity2.setCreatedBy(userId);

			}

			preferencesRepository.save(preferenceEntity1);
			if (isSecondPreference) {
				preferencesRepository.save(preferenceEntity2);
			}

			return aspirationDto;

		}

		else {

			if (aspirationDto.isFileUpdate() == true) {
				if (file != null && !file.isEmpty()) {
					if (Validator.isValidforPdf(file)) {
						FileUploadEntity uploadEntity = fileUploadInterface.storeFile(file, request);
						aspirationEntity.setFile(uploadEntity);
					} else {
						throw new IllegalArgumentException(ErrorMessageCode.UPLOAD_PDF);
					}
				} else {
					throw new FileNotFoundException(ErrorMessageCode.FILE_REQUIRED);
				}
			}

			aspirationEntity.setCreatedBy(userId);
			aspirationEntity.setAdditionalDetails(aspirationDto.getDetails());

			if (aspirationDto.getNextcareerMove()) {
				if (aspirationDto.getCity1() == null) {
					throw new IllegalArgumentException("City1 field is required");
				}

				CityMasterEntity city1 = this.cityMasterRepository.findById(aspirationDto.getCity1())
						.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.CITY_NOT_PRESENT));
				aspirationEntity.setCity1(city1);
				if (aspirationDto.getCity2() != null) {
					CityMasterEntity city2 = this.cityMasterRepository.findById(aspirationDto.getCity2())
							.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.CITY_NOT_PRESENT));
					aspirationEntity.setCity2(city2);
				} else {
					aspirationEntity.setCity2(null);

				}
				if (aspirationDto.getCity3() != null) {
					CityMasterEntity city3 = this.cityMasterRepository.findById(aspirationDto.getCity3())
							.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.CITY_NOT_PRESENT));
					aspirationEntity.setCity3(city3);
				} else {
					aspirationEntity.setCity3(null);
				}

			} else {
				aspirationEntity.setCity1(null);
				aspirationEntity.setCity2(null);
				aspirationEntity.setCity3(null);

			}

			aspirationEntity.setNextcareerMove(aspirationDto.getNextcareerMove());
			aspirationEntity.setUpdatedBy(userId);

			List<PreferenceEntity> preferenceList = this.preferencesRepository
					.findByCareerAspirationIdOrderByIdAsc(aspirationEntity);

			// Above list should never be NULL, because 1 preference is mandatory.

			PreferenceEntity preferenceEntity1 = preferenceList.get(0);

			GplFunctionEntity functionEntity = gplFunctionRepository.findById(aspirationDto.getFunctionId1())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND));
			GplDepartmentEntity departmentEntity = gplDepartmentRepository.findById(aspirationDto.getDepartmentId1())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DEPARTMENT_NOT_FOUND));
			GplRoleEntity roleEntity = gplRoleRepository.findById(aspirationDto.getRoleId1())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ROLE_NOT_FOUND));

			preferenceEntity1.setFunctionId(functionEntity);
			preferenceEntity1.setDepartmentId(departmentEntity);
			preferenceEntity1.setRoleId(roleEntity);
			preferenceEntity1.setExperience(aspirationDto.getExperience1());
			preferenceEntity1.setUpdatedBy(userId);

			// 2nd Preference
			if (preferenceList.size() == 2) {
				// While adding for the first time user added 2 preference
				PreferenceEntity preferenceEntity2 = preferenceList.get(1);

				// Check if the user has updated the 2nd preference or not. If user has removed
				// the 2nd preference, then delete the same.
				if (aspirationDto.getFunctionId2() != null || aspirationDto.getDepartmentId2() != null
						|| aspirationDto.getRoleId2() != null || aspirationDto.getExperience2() != null) {

					preferenceEntity2 = checkSecondPrefrencAndSave(preferenceEntity2, aspirationDto);

					preferenceEntity2.setCareerAspirationId(aspirationEntity);
					preferenceEntity2.setUpdatedBy(userId);

				} else {
					preferenceEntity2.setFunctionId(null);
					preferenceEntity2.setDepartmentId(null);
					preferenceEntity2.setRoleId(null);
					preferenceEntity2.setExperience(null);
					preferenceEntity2.setUpdatedBy(userId);
					preferenceEntity2.setIsActive(false);
				}

				preferencesRepository.save(preferenceEntity1);
				preferencesRepository.save(preferenceEntity2);

			} else if (preferenceList.size() == 1) {

				// This condition is used to check if the user did not provide 2nd preference
				// for the first time and then added 2 preferences 2nd time.

				if (aspirationDto.getFunctionId2() != null || aspirationDto.getDepartmentId2() != null
						|| aspirationDto.getRoleId2() != null || aspirationDto.getExperience2() != null) {

					PreferenceEntity preferenceEntity2 = new PreferenceEntity();

					preferenceEntity2 = checkSecondPrefrencAndSave(preferenceEntity2, aspirationDto);

					preferenceEntity2.setCareerAspirationId(aspirationEntity);
					preferenceEntity2.setCreatedBy(userId);

					preferencesRepository.save(preferenceEntity2);
				}

			}

			careerAspirationRepository.save(aspirationEntity);

		}

		return aspirationDto;

	}

	private PreferenceEntity checkSecondPrefrencAndSave(PreferenceEntity preferenceEntity2,
			AspirationDto aspirationDto) {

		if (aspirationDto.getFunctionId2() != null) {
			GplFunctionEntity functionEntity2 = gplFunctionRepository.findById(aspirationDto.getFunctionId2())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND));
			preferenceEntity2.setFunctionId(functionEntity2);
		} else {
			preferenceEntity2.setFunctionId(null);
		}

		if (aspirationDto.getDepartmentId2() != null) {
			GplDepartmentEntity departmentEntity2 = gplDepartmentRepository.findById(aspirationDto.getDepartmentId2())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DEPARTMENT_NOT_FOUND));
			preferenceEntity2.setDepartmentId(departmentEntity2);
		} else {
			preferenceEntity2.setDepartmentId(null);

		}

		if (aspirationDto.getRoleId2() != null) {
			GplRoleEntity roleEntity2 = gplRoleRepository.findById(aspirationDto.getRoleId2())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ROLE_NOT_FOUND));
			preferenceEntity2.setRoleId(roleEntity2);
		} else {
			preferenceEntity2.setRoleId(null);
		}

		if (aspirationDto.getExperience2() != null) {
			preferenceEntity2.setExperience(aspirationDto.getExperience2());
		} else {
			preferenceEntity2.setExperience(null);
		}

		return preferenceEntity2;
	}

	@Override
	public List<CareerAspirationPrefereceDto> getAllApirationPreference(String search, String pageNo, String pageSize)
			throws Exception {

		Pageable pageable = new Pagination().getPagination(pageSize, pageNo);

		List<IListCareerAspirationPreference> list = this.careerAspirationRepository.getAllAspirationPreference(search,
				pageable, IListCareerAspirationPreference.class);

		List<CareerAspirationPrefereceDto> aspirationPrefereceDtos = new ArrayList<>();
		List<CityList> cityList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			IListCareerAspirationPreference aspirationPreference = list.get(i);
			// check if email is same as previous
			if (i == 0 || !aspirationPreference.getEmail().equals(list.get(i - 1).getEmail())) {
				CareerAspirationPrefereceDto careerAspiration = new CareerAspirationPrefereceDto();
				CityList city = new CityList();
				careerAspiration.setEmail(aspirationPreference.getEmail());
				careerAspiration.setUsername(aspirationPreference.getUser());
				careerAspiration.setFileUrl(aspirationPreference.getFileUrl());
				careerAspiration.setNextCareerMove(aspirationPreference.getCareerMove());
				careerAspiration.setExtraDetails(aspirationPreference.getExtraDetails());
				city.setCity1(aspirationPreference.getCity1());
				city.setCityId1(list.get(i).getCityId1());
				city.setCity2(aspirationPreference.getCity2());
				city.setCityId2(list.get(i).getCityId2());
				city.setCity3(aspirationPreference.getCity3());
				city.setCityId3(list.get(i).getCityId3());

				cityList.add(city);
				careerAspiration.setCities(cityList);

				List<PreferenceList> preferenceLists = new ArrayList<>();
				PreferenceList preference = new PreferenceList();

				preference.setDeprtment(aspirationPreference.getDepartment());
				preference.setDepartmentId(aspirationPreference.getDepartmentId());
				preference.setFunction(aspirationPreference.getFunction());
				preference.setFuncitonId(aspirationPreference.getFuncitonId());
				preference.setRole(aspirationPreference.getRole());
				preference.setRoleId(aspirationPreference.getRoleId());
				preference.setExperience(aspirationPreference.getExperience());
				preferenceLists.add(preference);
				careerAspiration.setPreference(preferenceLists);
				aspirationPrefereceDtos.add(careerAspiration);
			} else {
				CareerAspirationPrefereceDto lastAspiration = aspirationPrefereceDtos
						.get(aspirationPrefereceDtos.size() - 1);

				PreferenceList preference = new PreferenceList();

				preference.setDeprtment(aspirationPreference.getDepartment());
				preference.setDepartmentId(aspirationPreference.getDepartmentId());
				preference.setFunction(aspirationPreference.getFunction());
				preference.setFuncitonId(aspirationPreference.getFuncitonId());
				preference.setRole(aspirationPreference.getRole());
				preference.setRoleId(aspirationPreference.getRoleId());
				preference.setExperience(aspirationPreference.getExperience());
				lastAspiration.getPreference().add(preference);

			}
		}

		return aspirationPrefereceDtos;
	}

	@Override
	public CareerAspirationPrefereceDto getApiration(Long userId) throws Exception {
		List<IListCareerAspirationPreference> list = this.careerAspirationRepository.getAspirationByUserId(userId,
				IListCareerAspirationPreference.class);
		List<PreferenceList> preferenceList = new ArrayList<PreferenceList>();
		CareerAspirationPrefereceDto careerAspirationPrefereceDto = new CareerAspirationPrefereceDto();
		List<CityList> cityList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			PreferenceList preference = new PreferenceList();
			CityList city = new CityList();
			if (i == 0) {
				careerAspirationPrefereceDto.setUsername(list.get(i).getUser());
				careerAspirationPrefereceDto.setEmail(list.get(i).getEmail());
				careerAspirationPrefereceDto.setFileUrl(list.get(i).getFileUrl());
				careerAspirationPrefereceDto.setNextCareerMove(list.get(i).getCareerMove());
				careerAspirationPrefereceDto.setExtraDetails(list.get(i).getExtraDetails());
				city.setCity1(list.get(i).getCity1());
				city.setCityId1(list.get(i).getCityId1());
				city.setCity2(list.get(i).getCity2());
				city.setCityId2(list.get(i).getCityId2());
				city.setCity3(list.get(i).getCity3());
				city.setCityId3(list.get(i).getCityId3());
				cityList.add(city);

				careerAspirationPrefereceDto.setCities(cityList);

			}

			preference.setDeprtment(list.get(i).getDepartment());
			preference.setDepartmentId(list.get(i).getDepartmentId());
			preference.setFunction(list.get(i).getFunction());
			preference.setFuncitonId(list.get(i).getFuncitonId());
			preference.setRole(list.get(i).getRole());
			preference.setRoleId(list.get(i).getRoleId());
			preference.setExperience(list.get(i).getExperience());
			preferenceList.add(preference);

		}
		careerAspirationPrefereceDto.setPreference(preferenceList);

		return careerAspirationPrefereceDto;
	}

	@Override
	public Page<CareerAspExportDto> getUserAspiration(String search, String function, String department,
			Pageable pageable, Boolean blankRecords, String exportIds) {

		Page<IListCareerAspiration> careerAspirationList = this.careerAspirationRepository.getCrAspiration(search,
				blankRecords, function, department, exportIds, Pageable.unpaged(), IListCareerAspiration.class);

		HashMap<String, Integer> emailList = new HashMap<>();

		List<CareerAspExportDto> list = new ArrayList<>();

		for (int i = 0; i < careerAspirationList.getContent().size(); i++) {
			String emailString = careerAspirationList.getContent().get(i).getEmail().trim().toLowerCase();

			if (emailList.get(emailString) == null) {
				CareerAspExportDto entity = setToObject(careerAspirationList.getContent().get(i));

				emailList.put(emailString, 1);

				if (entity.getFunctionId1() == entity.getFunctionId2()
						&& entity.getDepartmentId1() == entity.getDepartmentId2()
						&& entity.getRoleId1() == entity.getRoleId2() && entity.getExperience1() != null
						&& entity.getExperience2() != null && entity.getExperience1().equals(entity.getExperience2())) {

					entity.setFunctionId2(null);
					entity.setFunctionName2(null);
					entity.setDepartmentId2(null);
					entity.setDepartmentName2(null);
					entity.setRoleId2(null);
					entity.setRole2(null);
					entity.setExperience2(null);
				}

				list.add(entity);
			} else if (emailList.get(emailString) != null) {
				Integer count = emailList.get(emailString);
				count = count + 1;
				emailList.put(emailString, count);

				CareerAspExportDto temp = setToObject(careerAspirationList.getContent().get(i));

				if (count == 2
						&& (temp.getRoleId1() != temp.getRoleId2() || temp.getFunctionId1() != temp.getFunctionId2()
								|| temp.getDepartmentId1() != temp.getDepartmentId2()
								|| !temp.getExperience1().equals(temp.getExperience2()))) {
					for (CareerAspExportDto entity : list) {
						if (entity.getEmail().equals(emailString)) {

							entity.setRole1(temp.getRole1());
							entity.setRole2(temp.getRole2());
							entity.setRoleId1(temp.getRoleId1());
							entity.setRoleId2(temp.getRoleId2());

							entity.setFunctionName1(temp.getFunctionName1());
							entity.setFunctionName2(temp.getFunctionName2());
							entity.setFunctionId1(temp.getFunctionId1());
							entity.setFunctionId2(temp.getFunctionId2());

							entity.setDepartmentName1(temp.getDepartmentName1());
							entity.setDepartmentName2(temp.getDepartmentName2());
							entity.setDepartmentId1(temp.getDepartmentId1());
							entity.setDepartmentId2(temp.getDepartmentId2());

							entity.setExperience1(temp.getExperience1());
							entity.setExperience2(temp.getExperience2());
						}
					}
					;
				}
			}

		}

		Page<CareerAspExportDto> newListPage = new PageImpl<>(list, pageable, 0);

		return newListPage;

	}

	private CareerAspExportDto setToObject(IListCareerAspiration temp) {
		CareerAspExportDto entity = new CareerAspExportDto();
		entity.setUserId(temp.getUserId());
		entity.setEdp(temp.getEdp());
		entity.setEmail(temp.getEmail());
		entity.setName(temp.getName());
		entity.setFunctionName(temp.getFunctionName());
		entity.setDepartmentName(temp.getDepartmentName());

		entity.setRole1(temp.getRole1());
		entity.setRole2(temp.getRole2());
		entity.setRoleId1(temp.getRoleId1());
		entity.setRoleId2(temp.getRoleId2());

		entity.setFunctionName1(temp.getFunctionName1());
		entity.setFunctionName2(temp.getFunctionName2());
		entity.setFunctionId1(temp.getFunctionId1());
		entity.setFunctionId2(temp.getFunctionId2());

		entity.setDepartmentName1(temp.getDepartmentName1());
		entity.setDepartmentName2(temp.getDepartmentName2());
		entity.setDepartmentId1(temp.getDepartmentId1());
		entity.setDepartmentId2(temp.getDepartmentId2());

		entity.setExperience1(temp.getExperience1());
		entity.setExperience2(temp.getExperience2());

		entity.setNextCareerMove(temp.getNextCareerMove());
		entity.setAdditionalDetails(temp.getAdditionalDetails());
		entity.setCareerFileURL(temp.getCareerFileURL());

		entity.setLastUpdated(temp.getLastUpdated());

		entity.setCity1(temp.getCity1());
		entity.setCity2(temp.getCity2());
		entity.setCity3(temp.getCity3());
		return entity;
	}

	@Override
	public void exportToExcel(HttpServletResponse response, Page<CareerAspExportDto> list) throws IOException {
		StringBuilder builder = new StringBuilder();

		builder.append("User").append(",").append("Email").append(",").append("ExtraDetails").append(",")
				.append("NextCareerMove").append(",").append("FileUrl").append(",").append("Function-1").append(",")
				.append("Department-1").append(",").append("Role-1").append(",").append("Experience-1").append(",")
				.append("Function-2").append(",").append("Department-2").append(",").append("Role-2").append(",")
				.append("Experience-2").append(",").append("City1").append(",").append("City2").append(",")
				.append("City3");
		builder.append("\n");

		for (CareerAspExportDto temp : list) {
//			CareerAspExportDto temp = list.getContent().get(i);

			builder.append(temp.getName().replace(",", " ").replace("\n", " ")).append(",")
					.append(temp.getEmail().trim()).append(",")
					.append(temp.getAdditionalDetails() != null
							? temp.getAdditionalDetails().replace(",", " ").replace("\n", " ")
							: "")
					.append(",")
					.append(temp.getNextCareerMove() != null ? (temp.getNextCareerMove() == true) ? "YES" : "NO" : "")
					.append(",").append(temp.getCareerFileURL() != null ? temp.getCareerFileURL() : "").append(",")
					.append(temp.getFunctionName1() != null
							? temp.getFunctionName1().trim().replace(",", " ").replace("\n", " ")
							: "")
					.append(",")
					.append(temp.getDepartmentName1() != null
							? temp.getDepartmentName1().trim().replace(",", " ").replace("\n", " ")
							: "")
					.append(",")
					.append(temp.getRole1() != null ? temp.getRole1().trim().replace(",", " ").replace("\n", " ") : "")
					.append(",")
					.append(temp.getExperience1() != null
							? temp.getExperience1().trim().replace(",", " ").replace("\n", " ")
							: "")
					.append(",")
					.append(temp.getFunctionName2() != null
							? temp.getFunctionName2().trim().replace(",", " ").replace("\n", " ")
							: "")
					.append(",")
					.append(temp.getDepartmentName2() != null
							? temp.getDepartmentName2().trim().replace(",", " ").replace("\n", " ")
							: "")
					.append(",")
					.append(temp.getRole2() != null ? temp.getRole2().trim().replace(",", " ").replace("\n", " ") : "")
					.append(",")
					.append(temp.getExperience2() != null
							? temp.getExperience2().trim().replace(",", " ").replace("\n", " ")
							: "")
					.append(",")
					.append(temp.getCity1() != null ? temp.getCity1().trim().replace(",", " ").replace("\n", " ") : "")
					.append(",")
					.append(temp.getCity2() != null ? temp.getCity2().trim().replace(",", " ").replace("\n", " ") : "")
					.append(",")
					.append(temp.getCity3() != null ? temp.getCity3().trim().replace(",", " ").replace("\n", " ") : "");

			builder.append("\n");
		}

		PrintWriter printWriter = response.getWriter();
		printWriter.print(builder.toString());
		printWriter.flush();
		printWriter.close();

	}

	@Override
	public void deleteMultiple(DeleteId dto, Long userId) {
		// TODO Auto-generated method stub

		if (dto.getIds().isEmpty()) {
			throw new ResourceNotFoundException(ErrorMessageCode.AT_LEAST_ONE_ID_REQUIRED);
		}
		careerAspirationRepository.deleteAll(userId, dto.getIds());

		List<Long> careerAspirationIds = careerAspirationRepository.findByUserId(dto.getIds());

		preferencesRepository.deleteAll(userId, careerAspirationIds);

	}

}