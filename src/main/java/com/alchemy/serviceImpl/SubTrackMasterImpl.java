package com.alchemy.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.SubTrackDto;
import com.alchemy.dto.SubTrackUpdateDto;
import com.alchemy.entities.Attendance;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.SubTrackEntity;
import com.alchemy.entities.UserTrackEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListSubTrack;
import com.alchemy.repositories.AttendanceMainRepository;
import com.alchemy.repositories.FileUploadRepository;
import com.alchemy.repositories.LearningTrackRepository;
import com.alchemy.repositories.SubTrackRepository;
import com.alchemy.repositories.UserTrackRepository;
import com.alchemy.serviceInterface.SubTrackInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.Pagination;

@Service
public class SubTrackMasterImpl implements SubTrackInterface {
	@Autowired
	private SubTrackRepository subTrackRepository;

	@Autowired
	private LearningTrackRepository learningTrackRepository;

	@Autowired
	private FileUploadRepository fileUploadRepository;

	@Autowired
	private UserTrackRepository userTrackRepository;

	@Autowired
	private AttendanceMainRepository attendanceMainRepository;

	@Override
	public SubTrackDto addSubTrack(SubTrackDto subTrackDto, Long userId) {

		LearningTrackEntity learningTrackEntity = this.learningTrackRepository
				.findById(subTrackDto.getLearningTrackId())

				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND));

		IListSubTrack subTrack = this.subTrackRepository.findByNameAndlearning_track_entity(subTrackDto.getName(),
				subTrackDto.getLearningTrackId());

		if (subTrack != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_ALREADY_ASSIGN);
		} else {

			SubTrackEntity subTrackEntity = new SubTrackEntity();
			subTrackEntity.setName(subTrackDto.getName().trim());
			subTrackEntity.setLearningTrackEntity(learningTrackEntity);
			subTrackEntity.setCreatedBy(userId);
			subTrackEntity.setStartDate(GlobalFunctions.dateConvertIntoTimestamp(subTrackDto.getStartDate(), 0, 0, 1));
			Date startDate = subTrackDto.getStartDate();
			Date endDate = subTrackDto.getEndDate();
			if (endDate.compareTo(startDate) >= 0) {
				subTrackEntity
						.setEndDate(GlobalFunctions.dateConvertIntoTimestamp(subTrackDto.getEndDate(), 23, 59, 59));
			} else {
				throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_TRACK_DATE_VALIDATION);
			}

//		FileUploadEntity file = fileUploadRepository.findById(subTrackDto.getUploadBrochure())
//				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.FILE_NOT_FOUND));
			// subTrackEntity.setUploadBrochure(file);

			SubTrackEntity subId = subTrackRepository.save(subTrackEntity);

			ArrayList<UserTrackEntity> userTrackEntity = userTrackRepository
					.findBytrackIdId(subTrackDto.getLearningTrackId());

			if (!userTrackEntity.isEmpty()) {

				ArrayList<UserTrackEntity> userTrackList = new ArrayList<UserTrackEntity>();

				for (int i = 0; i < userTrackEntity.size(); i++) {

					if (userTrackEntity.get(i).getSubtrackId() == null) {

						UserTrackEntity user = new UserTrackEntity();
						user.setUserEntity(userTrackEntity.get(i).getUserEntity());
						user.setTrackId(userTrackEntity.get(i).getTrackId());
						user.setEmployeeLevel(userTrackEntity.get(i).getEmployeeLevel());

						user.setEnrollStatus(userTrackEntity.get(i).getEnrollStatus());

						user.setEmployeeId(userTrackEntity.get(i).getEmployeeId());
						user.setDepartmentId(userTrackEntity.get(i).getDepartmentId());
						user.setCreatedBy(userId);
						user.setSubtrackId(subId);
						userTrackList.add(user);
					}
				}
				userTrackRepository.saveAll(userTrackList);
			}

		}
		return subTrackDto;
	}

	@Override
	public SubTrackUpdateDto editSubTrack(@Valid SubTrackUpdateDto subTrackDto, Long userId, Long id) throws Exception {

		SubTrackEntity subTrackEntity = this.subTrackRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NOT_PRESENT));

		subTrackEntity.setName(subTrackDto.getName());

		subTrackEntity.setStartDate(GlobalFunctions.dateConvertIntoTimestamp(subTrackDto.getStartDate(), 0, 0, 1));
		subTrackEntity.setUpdatedBy(userId);
		Date startDate = subTrackDto.getStartDate();
		Date endDate = subTrackDto.getEndDate();
		if (endDate.compareTo(startDate) >= 0) {
			subTrackEntity.setEndDate(GlobalFunctions.dateConvertIntoTimestamp(subTrackDto.getEndDate(), 23, 59, 59));
		} else {
			throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_TRACK_DATE_VALIDATION);
		}

		// FileUploadEntity file =
		// fileUploadRepository.findById(subTrackDto.getUploadBrochure())
//				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.FILE_NOT_FOUND));
//		subTrackEntity.setUploadBrochure(file);

		subTrackRepository.save(subTrackEntity);

		return subTrackDto;
	}

	@Override
	public Page<IListSubTrack> getAllSubTrack(String search, String learningTrackId, String pageNo, String pageSize)
			throws Exception {
		Page<IListSubTrack> iListSubTrack;

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		iListSubTrack = this.subTrackRepository.findAllSubTrack(search, learningTrackId, pageable, IListSubTrack.class);

		return iListSubTrack;
	}

	@Override
	public void deleteSubTrack(Long subTrackId) {
		SubTrackEntity subTrackEntity = this.subTrackRepository.findById(subTrackId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NOT_PRESENT));
		subTrackEntity.setIsActive(false);
		this.subTrackRepository.save(subTrackEntity);
		ArrayList<UserTrackEntity> userTrackEntity = userTrackRepository.findBysubtrackIdId(subTrackId);
//		ArrayList<Attendance> attendanceEntity = attendanceMainRepository.findBySubTrackId(subTrackEntity);

		ArrayList<UserTrackEntity> userTrackList = new ArrayList<UserTrackEntity>();
//		ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();

		for (int i = 0; i < userTrackEntity.size(); i++) {
			userTrackEntity.get(i).setIsActive(false);
			userTrackList.addAll(userTrackEntity);
		}
		userTrackRepository.saveAll(userTrackList);

//		for (int i = 0; i < attendanceEntity.size(); i++) {
//			attendanceEntity.get(i).setIsActive(false);
//			attendanceList.addAll(attendanceEntity);
//		}
//		attendanceMainRepository.saveAll(attendanceList);

	}

	@Override
	public List<IListSubTrack> getAllSubTracks(String learningTrackId, Class<IListSubTrack> class1) {
		List<IListSubTrack> subTrackEntity = this.subTrackRepository.findByOrderByIdDesc(learningTrackId,
				IListSubTrack.class);
		return subTrackEntity;
	}

	@Override
	public void multiDeleteSubTrack(DeleteId ids, long id) {

		List<SubTrackEntity> list = this.subTrackRepository.findAllById(ids.getIds());
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setUpdatedBy(id);
		}
		this.subTrackRepository.multiDeleteById(ids.getIds());
	}

}
