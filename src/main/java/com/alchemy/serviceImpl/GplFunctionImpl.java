package com.alchemy.serviceImpl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.GplFunctionDto;
import com.alchemy.entities.GplDepartmentEntity;
import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.entities.GplRoleEntity;
import com.alchemy.entities.TestimonialEntity;
import com.alchemy.entities.TrackGplFunctionEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListGplFunctionDto;
import com.alchemy.repositories.GplDepartmentRepository;
import com.alchemy.repositories.GplFunctionRepository;
import com.alchemy.repositories.GplRoleRepository;
import com.alchemy.repositories.TestimonialRepository;
import com.alchemy.repositories.TrackGplFunctionRepository;
import com.alchemy.serviceInterface.GplFunctionInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class GplFunctionImpl implements GplFunctionInterface {

	@Autowired
	private GplFunctionRepository gplFunctionRepository;

	@Autowired
	private GplDepartmentRepository gplDepartmentRepository;

	@Autowired
	private GplRoleRepository gplRoleRepository;

	@Autowired
	private TestimonialRepository testimonialRepository;

	@Autowired
	private TrackGplFunctionRepository trackGplFunctionRepository;

	@Override
	public @Valid GplFunctionDto addGplFunction(@Valid GplFunctionDto gplFunctionDto, Long userId) {
		GplFunctionEntity GplFunctionName = gplFunctionRepository
				.findByNameIgnoreCaseAndIsActiveTrue(gplFunctionDto.getName());
		if (GplFunctionName != null) {

			throw new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_PRESENT);
		}
		GplFunctionEntity gplFunctionEntity = new GplFunctionEntity();
		gplFunctionEntity.setName(gplFunctionDto.getName());
		gplFunctionEntity.setCreatedBy(userId);
		gplFunctionRepository.save(gplFunctionEntity);
		return gplFunctionDto;

	}

	@Override
	public GplFunctionDto updateGplFunction(Long id, Long userId, GplFunctionDto gplFunctionDto) {
		GplFunctionEntity GplFunctionName = gplFunctionRepository
				.findByNameIgnoreCaseAndIsActiveTrue(gplFunctionDto.getName());

		GplFunctionEntity gplFunctionEntity = gplFunctionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND));

		if (GplFunctionName != null) {
			if (GplFunctionName.getId() != gplFunctionEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_PRESENT);
			}
		}
		gplFunctionEntity.setName(gplFunctionDto.getName());
		gplFunctionEntity.setUpdatedBy(userId);
		gplFunctionRepository.save(gplFunctionEntity);
		return gplFunctionDto;
	}

	@Override
	public void deleteGplFunction(DeleteId ids, Long userId) throws Exception {

		if (ids.getIds().isEmpty()) {
			throw new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_DELETE);
		}

		List<TestimonialEntity> list = null;
		for (int i = 0; i < ids.getIds().size(); i++) {
			list = this.testimonialRepository.findByFunctionId(ids.getIds().get(i));
			for (int j = 0; j < list.size(); j++) {
				list.get(j).setFunction(null);

			}
		}
		// if gpl_function is deleted by admin then its all mapping should be set to
		// false
		List<TrackGplFunctionEntity> mappingList = new LinkedList<>();
		for (int i = 0; i < ids.getIds().size(); i++) {
			List<TrackGplFunctionEntity> entities = this.trackGplFunctionRepository
					.findByGplFunctionEntityId(ids.getIds().get(i));
			entities.forEach(entity -> {
				entity.setIsActive(false);
				mappingList.add(entity);
			});
		}

		gplFunctionRepository.deleteGplFunctionById(userId, ids.getIds());
		trackGplFunctionRepository.saveAll(mappingList);
		testimonialRepository.saveAll(list);
		gplDepartmentRepository.deleteGplDepartmentById(userId, ids.getIds());

	}

	@Override
	public Page<IListGplFunctionDto> getAllGplFunction(String search, String pageNo, String pageSize) {

		Page<IListGplFunctionDto> iListGplFunctionDto;

		String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
		String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
		Pageable pageable = new Pagination().getPagination(pageNumber, pages);
		if (pageNo.isBlank() && pageSize.isBlank()) {
			pageable = Pageable.unpaged();
		}

		iListGplFunctionDto = this.gplFunctionRepository.findByName(search, pageable, IListGplFunctionDto.class);

		return iListGplFunctionDto;

	}

	@SuppressWarnings("resource")
	@Override
	public void uploadMasterDetails(MultipartFile file, Long userId) throws IOException {
		XSSFCell cells;

		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		int cell = worksheet.getRow(0).getPhysicalNumberOfCells();

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			XSSFRow row = worksheet.getRow(i);
			for (int j = 0; j < cell; j++) {
				cells = row.getCell(j);
				if (cells.toString().isEmpty()) {
					throw new IllegalArgumentException(
							"Row " + (i + 1) + " and  Column " + Constant.columnNames[j] + " is emplty.");
				}

			}
		}

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			XSSFRow row = worksheet.getRow(i);
			GplFunctionEntity functionEntity = null;
			GplDepartmentEntity gplDepartment = null;
			GplRoleEntity gplRoleEntity = null;
			GplFunctionEntity savedEntity = null;

			for (int j = 0; j < cell; j++) {
				cells = row.getCell(j);
				switch (j) {

				case 0:
					if (cells != null) {

						functionEntity = this.gplFunctionRepository.findByNameIgnoreCase(cells.toString());
						if (functionEntity == null) {

							functionEntity = new GplFunctionEntity();
							functionEntity.setName(cells.toString());
							functionEntity.setCreatedBy(userId);
//							gplFunctionRepository.save(functionEntity);
							savedEntity = gplFunctionRepository.save(functionEntity);

						}
					}
					break;
				case 1:
					String department = cells.getStringCellValue();
					// gplDepartment =
					// gplDepartmentRepository.findByNameIgnoreCaseAndIsActiveTrue(department);

					gplDepartment = gplDepartmentRepository
							.findByNameIgnoreCaseAndIsActiveTrueAndGplFunctionId(department, savedEntity);

					if (cells != null) {
						if (gplDepartment == null) {
							gplDepartment = new GplDepartmentEntity();
							gplDepartment.setName(cells.getStringCellValue());
							gplDepartment.setCreatedBy(userId);
							gplDepartment.setGplFunctionId(functionEntity);
							gplDepartmentRepository.save(gplDepartment);
						}

					}

					break;

				case 2:
					String role = cells.getStringCellValue();
					// GplRoleEntity gplRole =
					// gplRoleRepository.findByNameIgnoreCaseAndIsActiveTrue(role);

					if (cells != null) {
						// if (gplRole == null) {
						gplRoleEntity = new GplRoleEntity();
						gplRoleEntity.setName(role);
						gplRoleEntity.setCreatedBy(userId);
						gplRoleEntity.setGplDepartmentEntity(gplDepartment);
						gplRoleRepository.save(gplRoleEntity);
						// }
					}
					break;

				}
			}

		}

	}

}
