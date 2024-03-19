package com.alchemy.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.BannerDto;
import com.alchemy.dto.DeleteId;
import com.alchemy.dto.IsVisibleDto;
import com.alchemy.dto.VisibleContentDto;
import com.alchemy.entities.BannerEntity;
import com.alchemy.entities.ContentEnum;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.entities.SystemConfiguration;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListBanner;
import com.alchemy.repositories.BannerRepository;
import com.alchemy.repositories.SystemConfigurationRepository;
import com.alchemy.serviceInterface.BannerInterface;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.Validator;

@Service
public class BannerServiceImpl implements BannerInterface {

	@Autowired
	private BannerRepository bannerRepository;

	@Autowired
	private FileUploadImpl fileUploadImpl;

	@Autowired
	private FileUploadInterface fileUploadInterface;

	@Autowired
	private SystemConfigurationRepository systemConfigurationRepository;

	@Override
	public @Valid BannerDto addBanner(@Valid BannerDto bannerDto, MultipartFile file, HttpServletRequest request,
			Long userId) throws Exception {

		BannerEntity entity = this.bannerRepository.findByNameIgnoreCaseAndIsActiveTrue(bannerDto.getName());
		if (entity != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.BANNER_ALREADY_PRESENT);
		}
		BannerEntity bannerEntity = new BannerEntity();
		bannerEntity.setDescription(bannerDto.getDescription());
		bannerEntity.setName(bannerDto.getName());

		boolean displayOrder = this.bannerRepository.existsByDisplayOrder(bannerDto.getDisplayOrder());
		if (bannerDto.getDisplayOrder() > 0) {
			if (!displayOrder) {
				bannerEntity.setDisplayOrder(bannerDto.getDisplayOrder());
			} else {
				throw new IllegalArgumentException("This display order is already assign to banner");
			}
		} else {
			throw new IllegalArgumentException("Display order is greater than zero");

		}
		ContentEnum contentEnum = ContentEnum.valueOf(bannerDto.getShowContent().toString());
		bannerEntity.setShowContent(contentEnum.value);

		switch (contentEnum.value) {
		case 0:
			if (bannerDto.getShowContent().equals(ContentEnum.IMAGE) && file != null && !file.isEmpty()) {
				String originalFileName = file.getOriginalFilename();
				if (Validator.isValidforImageFile(originalFileName)) {
					FileUploadEntity fileEntity = this.fileUploadImpl.storeFile(file, request);
					bannerEntity.setFileId(fileEntity);
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);

				}
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.FILE_REQUIRED);
			}
			break;
		case 1:
			if (bannerDto.getShowContent().equals(ContentEnum.VIDEO) && bannerDto.getUrl() != null
					&& !bannerDto.getUrl().isEmpty() && !bannerDto.getUrl().isBlank()) {

				if (Validator.isValidForNameVideo(bannerDto.getUrl())) {

					bannerEntity.setUrl(bannerDto.getUrl());
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.INVALID_URI);

				}
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.URL_REQUIRED);
			}
			break;
		default:
			throw new IllegalArgumentException(ErrorMessageCode.FIELD_REQUIRED);
		}
		if (bannerDto.getIsVisible() == null) {
			bannerEntity.setIsVisible(false);
		} else {
			bannerEntity.setIsVisible(bannerDto.getIsVisible());
		}

		bannerEntity.setCreatedBy(userId);

		bannerRepository.save(bannerEntity);
		return bannerDto;

	}

	@Override
	public BannerDto updateBanner(BannerDto bannerDto, Long id, MultipartFile file, HttpServletRequest request,
			Long userId) throws Exception {

		BannerEntity bannerEntity = this.bannerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.BANNER_NOT_FOUND));
		BannerEntity entity = this.bannerRepository.findByNameIgnoreCaseAndIsActiveTrue(bannerDto.getName());
		if (entity != null) {
			if (entity.getId() != bannerEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.BANNER_ALREADY_PRESENT);
			}
		}

		bannerEntity.setDescription(bannerDto.getDescription());
		bannerEntity.setName(bannerDto.getName());
		if (bannerDto.getDisplayOrder() > 0) {
			if (bannerEntity.getDisplayOrder().equals(bannerDto.getDisplayOrder())) {
				bannerEntity.setDisplayOrder(bannerDto.getDisplayOrder());
			} else {
				Boolean existsByDisplayOrder = this.bannerRepository.existsByDisplayOrder(bannerDto.getDisplayOrder());
				if (!existsByDisplayOrder) {
					bannerEntity.setDisplayOrder(bannerDto.getDisplayOrder());
				} else {
					throw new IllegalArgumentException("This display order is already assign to banner");
				}
			}
		} else {
			throw new IllegalArgumentException("Display order is greater than zero");
		}
		ContentEnum contentEnum = ContentEnum.valueOf(bannerDto.getShowContent().toString());
		bannerEntity.setShowContent(contentEnum.value);
		switch (contentEnum.value) {
		case 0:
			if (bannerDto.getIsFileUpdated() == true) {
				if (bannerDto.getShowContent().equals(ContentEnum.IMAGE) && file != null && !file.isEmpty()) {
					String originalFileName = file.getOriginalFilename();
					if (Validator.isValidforImageFile(originalFileName)) {

						FileUploadEntity fileId = bannerEntity.getFileId();
						FileUploadEntity fileEntity = this.fileUploadImpl.storeFile(file, request);
						bannerEntity.setFileId(fileEntity);
						bannerEntity.setUrl(null);
						if (fileId != null) {
							this.fileUploadInterface.delete(fileId.getId());
						}

					} else {
						throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
					}
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.FILE_REQUIRED);
				}
			}
			break;
		case 1:
			if (bannerDto.getShowContent().equals(ContentEnum.VIDEO) && bannerDto.getUrl() != null
					&& !bannerDto.getUrl().isEmpty() && !bannerDto.getUrl().isBlank()) {

				if (Validator.isValidForNameVideo(bannerDto.getUrl())) {
					bannerEntity.setUrl(bannerDto.getUrl());

					FileUploadEntity fileId = bannerEntity.getFileId();
					bannerEntity.setFileId(null);
					if (fileId != null) {
						this.fileUploadInterface.delete(fileId.getId());

					}

				} else {
					throw new IllegalArgumentException(ErrorMessageCode.INVALID_URI);
				}
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.URL_REQUIRED);
			}
			break;
		default:
			throw new IllegalArgumentException(ErrorMessageCode.FIELD_REQUIRED);
		}

		if (bannerDto.getIsVisible() == null) {
			bannerEntity.setIsVisible(false);
		} else {
			bannerEntity.setIsVisible(bannerDto.getIsVisible());
		}

		bannerEntity.setUpdatedBy(userId);

		bannerRepository.save(bannerEntity);

		return bannerDto;

	}

	@Override
	public void deleteBannerById(Long bannerId) {
		BannerEntity banner = bannerRepository.findById(bannerId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.BANNER_NOT_FOUND));
	
		banner.setFileId(null);
		banner.setIsActive(false);
		bannerRepository.save(banner);
//		this.bannerRepository.deleteById(banner.getId());
		
		List<BannerEntity> list = bannerRepository.findAllByOrderByDisplayOrderAsc();
		for (int i = 0; i < list.size(); i++) {
 			list.get(i).setDisplayOrder(i + 1);
		}
		this.bannerRepository.saveAll(list);
	}

	@Override
	public Page<IListBanner> getAllBanners(String search, String pageNo, String pageSize) throws Exception {

		Page<IListBanner> iListBanner;

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		if (search == "" || search == null || search.length() == 0) {

			iListBanner = this.bannerRepository.findByOrderByIdDesc(pageable, IListBanner.class);
		} else {

			iListBanner = this.bannerRepository.findAllBanners(search, pageable, IListBanner.class);
		}
		return iListBanner;

	}

	@Override
	public List<IListBanner> findAllList(String search, Class<IListBanner> class1) {
		List<IListBanner> bannerDto = this.bannerRepository.findAllByOrderByDisplayOrderAsc(IListBanner.class);
		return bannerDto;
	}

	@Override
	public void updateBannerIsVisible(Long id, IsVisibleDto dto) {

		SystemConfiguration findBykey = systemConfigurationRepository.findBykey(Constant.BANNER_LIMIT);
		Integer bannerLimit = Integer.parseInt(findBykey.getValue());

		BannerEntity entity = this.bannerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.BANNER_NOT_FOUND));

		long count = bannerRepository.countOfIsVisibleInBanner();

		if (dto.getIsVisible()) {
			if (count < bannerLimit) {
				entity.setIsVisible(dto.getIsVisible());
				this.bannerRepository.save(entity);
			} else {
				throw new IllegalArgumentException("Limit exceeded. Disable an existing banner first.");
			}
		} else {
			entity.setIsVisible(dto.getIsVisible());
			this.bannerRepository.save(entity);
		}

	}

	@Override
	public void deleteMultipleBannerByIds(DeleteId deleteIdsDto) {
		List<BannerEntity> bannerEntities = this.bannerRepository.findAllById(deleteIdsDto.getIds());
		if (bannerEntities.size() != deleteIdsDto.getIds().size()) {
			throw new IllegalArgumentException(ErrorMessageCode.BANNER_NOT_FOUND);
		}
		bannerRepository.deleteBannersById(deleteIdsDto.getIds());

	}

	@Override
	public void isVisibleMultiSelect(VisibleContentDto visibleContentDto) {
		List<BannerEntity> bannerEntities = this.bannerRepository.findAllById(visibleContentDto.getIds());
		if (bannerEntities.size() != visibleContentDto.getIds().size()) {
			throw new IllegalArgumentException(ErrorMessageCode.BANNER_NOT_FOUND);
		}
		
		
		long count = bannerRepository.countOfIsVisibleInBanner();
		if(visibleContentDto.getIsVisible()== true) {
		if(count>=6 || count + bannerEntities.size() >6) {
			throw new ResourceNotFoundException(ErrorMessageCode.BANNER_ISVISIBLE_LIMIT);
		}
		}
		
		this.bannerRepository.isVisibleBannerByIds(visibleContentDto.getIsVisible(), visibleContentDto.getIds());

	}

}
