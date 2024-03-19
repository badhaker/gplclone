package com.alchemy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.DesignationDto;
import com.alchemy.entities.DesignationEntity;
import com.alchemy.entities.SponsorMaster;
import com.alchemy.entities.TestimonialEntity;
import com.alchemy.entities.TrainersMasterEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListDesignation;
import com.alchemy.repositories.DesignationRepository;
import com.alchemy.repositories.SponserRepository;
import com.alchemy.repositories.TestimonialRepository;
import com.alchemy.repositories.TrainersMasterRepository;
import com.alchemy.serviceInterface.DesignationInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class DesignationServiceImpl implements DesignationInterface {

	@Autowired
	private DesignationRepository designationRepository;

	@Autowired
	private TestimonialRepository testimonialRepository;

	@Override
	public DesignationDto addDesignation(DesignationDto designationDto) {

		DesignationEntity designation = designationRepository.findByNameIgnoreCase(designationDto.getName());
		if (designation != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.DESIGNATION_ALREADY_EXISTS);
		}

		DesignationEntity designationEntity = new DesignationEntity();
		designationEntity.setName(designationDto.getName());
		designationEntity.setDescription(designationDto.getDescription());
		designationRepository.save(designationEntity);
		return designationDto;
	}

	@Override
	public DesignationDto editDesignation(Long designationId, DesignationDto designationDto) {
		DesignationEntity designationEntity = this.designationRepository.findById(designationId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DESIGNATION_NOT_FOUND));

		DesignationEntity designation = designationRepository.findByNameIgnoreCase(designationDto.getName());
		if (designation != null) {
			if (designation.getId() != designationEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.DESIGNATION_ALREADY_EXISTS);
			}
		}
		designationEntity.setName(designationDto.getName());
		designationEntity.setDescription(designationDto.getDescription());
		this.designationRepository.save(designationEntity);
		return designationDto;
	}

	@Autowired
	private TrainersMasterRepository trainersMasterRepository;
	@Autowired
	private SponserRepository sponserRepository;

	@Override
	public void deleteDesignation(Long designationId) {

		DesignationEntity designationEntity = this.designationRepository.findById(designationId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DESIGNATION_NOT_FOUND));
		designationEntity.setIsActive(false);

		List<TestimonialEntity> testimonialList = this.testimonialRepository.findByDesignationId(designationId);
		for (int i = 0; i < testimonialList.size(); i++) {

			testimonialList.get(i).setDesignation(null);
		}

		List<TrainersMasterEntity> trainersMasterEntity = trainersMasterRepository.findByDesignationId(designationId);

		for (int i = 0; i < trainersMasterEntity.size(); i++) {

			trainersMasterEntity.get(i).setDesignation(null);
		}

		List<SponsorMaster> sponsorMaster = sponserRepository.findBydesignationIdId(designationId);

		for (int i = 0; i < sponsorMaster.size(); i++) {
			sponsorMaster.get(i).setDesignationId(null);

		}
		this.testimonialRepository.saveAll(testimonialList);
		trainersMasterRepository.saveAll(trainersMasterEntity);
		sponserRepository.saveAll(sponsorMaster);
		this.designationRepository.save(designationEntity);
	}

	@Override
	public Page<IListDesignation> getAllDesignations(String search, String pageNumber, String pageSize)
			throws Exception {

		Page<IListDesignation> iListDesignation;

		Pageable pageable = new Pagination().getPagination(pageNumber, pageSize);
		if (search == "" || search == null || search.length() == 0) {

			iListDesignation = this.designationRepository.findByOrderByIdDesc(pageable, IListDesignation.class);
		} else {

			iListDesignation = this.designationRepository.findByName(search, pageable, IListDesignation.class);

		}
		return iListDesignation;
	}

	@Override
	public List<IListDesignation> getAllDesignations(Class<IListDesignation> class1) {

		List<IListDesignation> iListDesignation = designationRepository.findByOrderByIdDesc(IListDesignation.class);
		return iListDesignation;
	}

	@Override
	public void deleteMultipleDesignationById(DeleteId ids, Long userId) {

		List<TestimonialEntity> testimonial = null;
		List<TrainersMasterEntity> trainersMasterEntity = null;
		List<SponsorMaster> sponsor = null;

		for (int i = 0; i < ids.getIds().size(); i++) {
			testimonial = this.testimonialRepository.findByDesignationId(ids.getIds().get(i));
			trainersMasterEntity = trainersMasterRepository.findByDesignationId(ids.getIds().get(i));
			sponsor = sponserRepository.findBydesignationIdId(ids.getIds().get(i));
			for (int j = 0; j < testimonial.size(); j++) {
				testimonial.get(j).setDesignation(null);
			}

			for (int j = 0; j < trainersMasterEntity.size(); j++) {
				trainersMasterEntity.get(j).setDesignation(null);
			}

			for (int j = 0; j < sponsor.size(); j++) {
				sponsor.get(j).setDesignationId(null);

			}
		}

		designationRepository.deleteAllByIdIn(userId, ids.getIds());

		this.testimonialRepository.saveAll(testimonial);
		trainersMasterRepository.saveAll(trainersMasterEntity);
		sponserRepository.saveAll(sponsor);

	}
}
