package com.alchemy.serviceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.EnrollFilterDto;
import com.alchemy.dto.MultipleEnrollStatusDto;
import com.alchemy.dto.UserTrackDto;
import com.alchemy.dto.UserTrackStatusDto;
import com.alchemy.entities.Attendance;
import com.alchemy.entities.AttendanceStatus;
import com.alchemy.entities.DepartmentEntity;
import com.alchemy.entities.EnrollStatus;
import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.LevelEntity;
import com.alchemy.entities.MailTemplate;
import com.alchemy.entities.SubTrackEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.entities.UserTrackEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListGplFunctionDto;
import com.alchemy.iListDto.IListUserTrack;
import com.alchemy.repositories.DepartmentRepository;
import com.alchemy.repositories.GplFunctionRepository;
import com.alchemy.repositories.LearningTrackRepository;
import com.alchemy.repositories.LevelRepository;
import com.alchemy.repositories.LevelTrackReopsitory;
import com.alchemy.repositories.MailRepository;
import com.alchemy.repositories.SubTrackRepository;
import com.alchemy.repositories.TrackGplFunctionRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.repositories.UserTrackRepository;
import com.alchemy.serviceInterface.AttendanceInterface;
import com.alchemy.serviceInterface.EmailInterface;
import com.alchemy.serviceInterface.UserTrackInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class UserTrackServiceImpl implements UserTrackInterface {

	@Autowired
	private LearningTrackRepository trackRepo;

	@Autowired
	private MailRepository mailRepository;

	@Autowired
	private UserTrackRepository userTrackRepository;

	@Autowired
	private EmailInterface emailInterface;
	
	@Autowired
	private AttendanceInterface attendanceInterface;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private SubTrackRepository subTrackRepository;
	
	@Autowired
	private GplFunctionRepository functionRepository;
	
	@Autowired
	private TrackGplFunctionRepository trackFunction;
	
	@Autowired
	private LevelRepository levelRepo;

	@Autowired
	private LevelTrackReopsitory trackLevel;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserTrackServiceImpl.class);

//	@SuppressWarnings("unlikely-arg-type")
	@Override
	public UserTrackDto addUserTrack(UserTrackDto userTrackDto, Long userId, String token) throws Exception {

		ArrayList<UserTrackEntity> trackList = new ArrayList<UserTrackEntity>();
		
		LearningTrackEntity track = trackRepo.findById(userTrackDto.getTrackId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND));
		ArrayList<SubTrackEntity> subTrackEntity = subTrackRepository.findAllByLearningTrackEntityId(track.getId());
		UserEntity userEntity = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USER_NOT_FOUND));
		List<UserTrackEntity> userTrack = userTrackRepository.findByUserEntityIdAndTrackId(userId, track);

		for (int i = 0; i < userTrack.size(); i++) {
			if (userTrack != null) {
				throw new ResourceNotFoundException(ErrorMessageCode.USERTRACK_ALREADY_ASSIGNED);
			}
		}
		if (track.getEnrollCloseDate() != null) {
			LocalDate enrollClose = track.getEnrollCloseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate today = LocalDate.now();
			if (today.compareTo(enrollClose) > 0) {
				throw new ResourceNotFoundException(ErrorMessageCode.ENROLLMENT_FAILED);
			}
		}
		GplFunctionEntity function= functionRepository.findById(userEntity.getFunctionId().getId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND));
		LevelEntity level = levelRepo.findById(userEntity.getLevelId().getId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEVEL_NOT_FOUND));;
		List<Long> functions = trackFunction.findDepartmentIdByTrackId(track.getId());
		List<Long> levels = trackLevel.findLevelIdByTrackId(track.getId());
		
		if(functions.contains(function.getId())&& levels.contains(level.getId())) {
			UserTrackEntity user = new UserTrackEntity();
			user.setUserEntity(userEntity);
			user.setTrackId(track);
			user.setEmployeeLevel(userTrackDto.getEmployeeLevel());
			user.setEnrollStatus(3);
			user.setEmployeeLevel(level.getLevelName());
			user.setDepartmentId(function);

			user.setCreatedBy(userId);

			trackList.add(user);

			for (int i = 0; i < subTrackEntity.size(); i++) {

				UserTrackEntity userTrackEntity = new UserTrackEntity();
				userTrackEntity.setUserEntity(userEntity);
				userTrackEntity.setTrackId(track);
				userTrackEntity.setSubtrackId(subTrackEntity.get(i));
				userTrackEntity.setEmployeeLevel(userTrackDto.getEmployeeLevel());
				user.setEmployeeLevel(level.getLevelName());
				userTrackEntity.setEnrollStatus(3);
				userTrackEntity.setDepartmentId(function);

				trackList.add(userTrackEntity);
			}
			userTrackRepository.saveAll(trackList);
			MailTemplate mailtemplate = mailRepository.findBytemplatename("NominationReceived");
			if (null != mailtemplate) {
				String template = mailtemplate.getMailtemp();
				String replaceString = template.replace("< Employee Name >", userEntity.getName()).replace("< Program Name >",
						track.getName());

				emailInterface.sendSimpleMessage(userEntity.getEmail(), "GPL Alchemy | Nomination Received", replaceString);
				LOG.info("UserTrackServiceImpl >> addUserTrack() >>  EnrollTrack >>  " + userEntity.getEmail());
			}

			return userTrackDto;
		}
		else {
			throw new ResourceNotFoundException(ErrorMessageCode.CANNOT_ENROLL);
		}
		
	}

	@Transactional
	@Override
	public void updateUserTrackStatus(Long id, UserTrackStatusDto userTopicDto)
			throws MessagingException, FileNotFoundException, IOException {
		UserTrackEntity userTrack = this.userTrackRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USERTRACK_NOT_FOUND));
		EnrollStatus status = EnrollStatus.valueOf(userTopicDto.getEnrollStatus().toString());

		List<UserTrackEntity> track = this.userTrackRepository
				.findByUserEntityIdAndTrackId(userTrack.getUserEntity().getId(), userTrack.getTrackId());

		for (int i = 0; i < track.size(); i++) {
			track.get(i).setEnrollStatus(status.value);
		}

		userTrack.setEnrollStatus(status.value);
		
		emailInterface.sendEnrollmentMain(userTopicDto.getEnrollStatus().toString(), userTrack);
		
		this.userTrackRepository.save(userTrack);

	}

	@Override
	public List<IListUserTrack> getAllUserTracks(Class<IListUserTrack> class1) {
		List<IListUserTrack> dto = this.userTrackRepository.findByOrderByIdDesc(IListUserTrack.class);
		return dto;
	}

	@Override
	public Page<IListUserTrack> findAllUserTracks(String search,EnrollFilterDto dto, 
			HttpServletResponse response,
			Pageable pageable) {

		Page<IListUserTrack> iListUserTrack;
		
		iListUserTrack = this.userTrackRepository.findAllList(search,
				dto.getTrack() != null ? dto.getTrack() : "",
				dto.getName() != null ? dto.getName() : "",
				dto.getStatus() != null ? dto.getStatus() : "",
				dto.getEmail() != null ? dto.getEmail() : "", 
				pageable, IListUserTrack.class);

		return iListUserTrack;

	}

	@Transactional
	@Override
	public int markMultipleEnrollById(MultipleEnrollStatusDto id, Long userId) throws MessagingException {
		
        EnrollStatus status = EnrollStatus.valueOf(id.getStatus().toString());
		 int count= 0;       
        for(int i=0; i<id.getIds().size(); i++) {
        	String name= userTrackRepository.findTrackNameById(id.getIds().get(i));
        UserTrackEntity userTrack=	userTrackRepository.findById(id.getIds().get(i))
        		.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USERTRACK_NOT_FOUND + name ) );
        if(userTrack.getEnrollStatus()==2 || userTrack.getEnrollStatus()==3) {
        	count++;
        }
        List<Long> list= userTrackRepository.findByTrackEnityAndUserEntity(userTrack.getTrackId().getId(), userTrack.getUserEntity().getId());
       
        userTrackRepository.markEnrollStatusForSubtrack(list, status.value, userId);

        if (userTrack.getEnrollStatus()==2 || userTrack.getEnrollStatus()==3) {
		emailInterface.sendEnrollmentMain(id.getStatus().toString(), userTrack);
        }
      }	
        return count;
       
	}
}
