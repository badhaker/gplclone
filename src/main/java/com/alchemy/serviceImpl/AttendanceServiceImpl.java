package com.alchemy.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.AttendanceDto;
import com.alchemy.dto.AttendanceFilterDto;
import com.alchemy.dto.AttendanceStatusDto;
import com.alchemy.entities.Attendance;
import com.alchemy.entities.AttendanceEntity;
import com.alchemy.entities.AttendanceStatus;
import com.alchemy.entities.BulkUploadInformation;
import com.alchemy.dto.DeleteId;
import com.alchemy.dto.IsVisibleDto;
import com.alchemy.dto.MarkAttendanceRequestDto;
import com.alchemy.dto.MultiLockRequestDto;
import com.alchemy.dto.StarperformerDto;
import com.alchemy.entities.DepartmentEntity;
import com.alchemy.entities.EnrollStatus;
import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.LockAttendance;
import com.alchemy.entities.ModuleMasterEntity;
import com.alchemy.entities.SubTrackEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.entities.UserTrackEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListAttendance;
import com.alchemy.repositories.AttendanceMainRepository;
import com.alchemy.repositories.AttendanceRepository;
import com.alchemy.repositories.BulkUploadInformationRepository;
import com.alchemy.repositories.DepartmentRepository;
import com.alchemy.repositories.GplFunctionRepository;
import com.alchemy.repositories.LearningTrackRepository;
import com.alchemy.repositories.ModuleMasterRepository;
import com.alchemy.repositories.SubTrackRepository;
import com.alchemy.repositories.TrackDepartmentRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.repositories.UserTrackRepository;
import com.alchemy.serviceInterface.AttendanceInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.opencsv.CSVReader;

@Service
public class AttendanceServiceImpl implements AttendanceInterface {

	@Autowired
	private GplFunctionRepository functionRepo;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private ModuleMasterRepository moduleMasterRepository;

	@Autowired
	private BulkUploadInformationRepository bulkUploadInformationRepository;

	@Autowired
	private AttendanceMainRepository attendanceMainRepository;

	@Autowired
	private LearningTrackRepository learningTrackRepository;

	@Autowired
	private SubTrackRepository subTrackRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTrackRepository userTrackRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private TrackDepartmentRepository trackDepartmentRepository;

	@Override
	public void lockMultipleAttendanceById(MultiLockRequestDto id, Long userId) {
		
		attendanceMainRepository.lockAttendanceByIdIn(id.getIds(), id.getLock(), userId);

	}

	@Override
	public int markMultipleAttendanceById(MarkAttendanceRequestDto id, Long userId) {
		LocalDateTime currentDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of("UTC");
        Date date = Date.from(currentDateTime.atZone(zoneId).toInstant());
		AttendanceStatus status = AttendanceStatus.valueOf(id.getAttendance().toString());
		
		attendanceMainRepository.markAttendance(id.getIds(), status.value, userId);
		int count= 0;   
		for(int i=0; i<id.getIds().size(); i++) {
			
		Attendance attendance=	attendanceMainRepository.findById(id.getIds().get(i))
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ATTENDANCE_NOT_FOUND));
		attendance.setUpdatedAt(date);
		this.attendanceMainRepository.save(attendance);
		if(attendance.getLockAttendance() == false) {
			count++;
		}
		if(attendance.getCompleteDateOfAttendance()==null) {
		
			attendance.setCompleteDateOfAttendance(attendance.getSubTrackId().getEndDate());
			
		UserTrackEntity entity= userTrackRepository.findByTrackIdAndUserEntityAndSubtrackId
				(attendance.getLearningTrackId(), attendance.getUserId(), attendance.getSubTrackId());
		entity.setCompleteDate(attendance.getSubTrackId().getEndDate());
		userTrackRepository.save(entity);
		
		addCompleteDateAfterMarkAttendance(attendance);
		}
		}
		return count;

	}

	@SuppressWarnings("resource")
	@Override
	public Long saveExcelFile(MultipartFile multipartFile, Long userId, String moduleName)
			throws IOException, Exception {

		if (!multipartFile.getOriginalFilename().endsWith(".csv")) {
			throw new ResourceNotFoundException("File must be a CSV");
		}
		if (multipartFile.isEmpty()) {
			throw new IllegalArgumentException(ErrorMessageCode.PLEASE_UPLOAD_FILE);
		}
		List<AttendanceEntity> attendanceEntities = new ArrayList<AttendanceEntity>();
		BulkUploadInformation bulkUpload = new BulkUploadInformation();
		bulkUpload.setFileName(multipartFile.getOriginalFilename());
		bulkUpload.setUserId(userId);

		ModuleMasterEntity module = this.moduleMasterRepository.findByModuleNameIgnoreCaseAndIsActiveTrue(moduleName);

		if (module == null) {
			throw new ResourceNotFoundException(ErrorMessageCode.MODULE_NOT_FOUND);
		}
		if (moduleName.isBlank() || moduleName.isEmpty()) {
			throw new ResourceNotFoundException(ErrorMessageCode.MODULE_REQUIRED);
		}
		bulkUpload.setModuleId(module.getId());
		this.bulkUploadInformationRepository.saveAll(Arrays.asList(bulkUpload));

		CSVReader csvReader1 = new CSVReader(new InputStreamReader(multipartFile.getInputStream()));
		String[] headers = csvReader1.readNext();
		ArrayList<String> arrayList = new ArrayList<>();

		List<String> names = Arrays.asList("date", "email", "employee_edp", "employee_name",
				"complete_date_of_attendance", "function_name", "zone", "track_name", "subtrack_name", 
				"lock_attendance", "attendance_status", "pre_assesment_score", "post_assesment_score");

		if (headers.length != names.size()) {
			throw new ResourceNotFoundException("File does not contain valid columns");
		}

		for (int s = 0; s < headers.length; s++) {

			if (headers[s] == null || headers[s].toString().isEmpty()) {
				throw new ResourceNotFoundException("Column '" + names + "' is empty at " + (s + 1));
			}

			arrayList.add(headers[s].toString().trim().replaceAll("\\s+", ""));
		}

		for (int n = 0; n < names.size(); n++) {

			if (!names.get(n).equalsIgnoreCase(arrayList.get(n))) {
				throw new ResourceNotFoundException("File is not valid at column name:" + arrayList.get(n));
			}
		}

		String[] data = csvReader1.readNext();
		if (data == null || data.length == 0) {
			throw new ResourceNotFoundException("CSV file does not contain any data");
		}

		InputStreamReader reader = new InputStreamReader(multipartFile.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(reader);
		CSVReader csvReader = new CSVReader(bufferedReader);

		List<String[]> records = csvReader.readAll();
		for (int i = 1; i < records.size(); i++) {

			String[] record = records.get(i);
			boolean isEmpty = true;
			for (String cell : record) {
				if (cell != null && !cell.trim().isEmpty()) {
					isEmpty = false;
					break;

				}
			}
			if (!isEmpty) {

				AttendanceEntity attendanceEntity = new AttendanceEntity();
				attendanceEntity.setBulkId(bulkUpload.getId());

				UserEntity user = new UserEntity();
				LearningTrackEntity learningTrack = new LearningTrackEntity();
				SubTrackEntity subTrack = new SubTrackEntity();

				for (int j = 0; j < record.length; j++) {
					String cell = record[j];
					switch (j) {

					case 0:
						if (cell != null && !cell.toString().isEmpty()) {
							try {
								DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
										.appendPattern("dd-MM-yyyy").toFormatter(Locale.ENGLISH);
								LocalDate localDate = LocalDate.parse(cell.toString(), formatter);
								Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); // convert
																													// to
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								calendar.set(Calendar.HOUR_OF_DAY, 13); // 1 PM
								calendar.set(Calendar.MINUTE, 0);
								calendar.set(Calendar.SECOND, 0);
								calendar.set(Calendar.MILLISECOND, 0);

								date = calendar.getTime();
								attendanceEntity.setDate(date);
							} catch (DateTimeParseException e) {

								throw new IllegalArgumentException(
										"Invalid date format at row " + i + " and column " + Constant.columnNames[j]
												+ ",Please provide a valid date in the format of 'dd-MM-yyyy'",
										e);
							}

						} 
//						else {
//							throw new ResourceNotFoundException(
//									"Date Cell at row " + i + " and column " + Constant.columnNames[j] + " is empty.");
//						}

						break;

					case 1:
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
							user = this.userRepository.findByEmailIgnoreCase(cell.trim());
							if (user != null) {
								attendanceEntity.setEmail(cell);
							} else {
								throw new ResourceNotFoundException(
										"Employee email " + "'" + cell + "'" + " not found");
							}
						} else {
							throw new ResourceNotFoundException(
									"Email Cell at row " + i + " and column " + Constant.columnNames[j] + " is empty.");
						}
						break;

					case 2:
//							if (!cell.isEmpty()) {
						attendanceEntity.setEmployeeEdp(cell);
//							} else {
//								throw new ResourceNotFoundException("Employee edp Cell at row " + i + " and column "
//										+ Constant.columnNames[j] + " is empty.");
//							}
						break;

					case 3:
//							if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {

						attendanceEntity.setName(cell);

//							} else {
//								throw new ResourceNotFoundException("Employee name Cell at row " + i + " is empty.");
//							}
						break;

					case 4:

						if (!cell.toString().isEmpty() && cell.toString() != "" && cell != null) {
							try {
								DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
										.appendPattern("dd-MM-yyyy").toFormatter(Locale.ENGLISH);
								LocalDate localDate = LocalDate.parse(cell.toString(), formatter);
								Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); // convert
																													// to
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								calendar.set(Calendar.HOUR_OF_DAY, 13); // 1 PM
								calendar.set(Calendar.MINUTE, 0);
								calendar.set(Calendar.SECOND, 0);
								calendar.set(Calendar.MILLISECOND, 0);

								date = calendar.getTime();

								attendanceEntity.setCompleteDateOfAttendance(date);
							} catch (DateTimeParseException e) {

								throw new IllegalArgumentException(
										"Invalid date format at row " + i + " and column " + Constant.columnNames[j]
												+ ",Please provide a valid date in the format of 'dd-MM-yyyy'",
										e);
							}
						} else {
							throw new ResourceNotFoundException(
									"Complete date of attendance Cell at row " + i + " is empty.");
						}

						break;

					case 5:
//							if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//								GplFunctionEntity departmentEntity = this.functionRepo
//										.findByNameIgnoreCaseAndIsActiveTrue(cell.trim());

//								if (departmentEntity != null) {
						attendanceEntity.setFunctionName(cell);
//								} else {
//									throw new ResourceNotFoundException(
//											"Function name " + "'" + cell + "'" + " not found");
//								}
//							} else {
//								throw new ResourceNotFoundException("Function name Cell at row " + i + " is empty.");
//							}
						break;

					case 6:
//							if (!cell.isEmpty()) {
						attendanceEntity.setZone(cell);
//							} else {
//								throw new ResourceNotFoundException("Zone Cell at row " + i + " is empty.");
//							}
						break;

					case 7:
						if (!cell.isEmpty()) {
							learningTrack = this.learningTrackRepository.findByNameIgnoreCase(cell.trim());
							if (learningTrack != null) {
								attendanceEntity.setTrackName(cell);
							} else {
								throw new ResourceNotFoundException("Track Name " + "'" + cell + "'" + " not found");
							}
						} else {
							throw new ResourceNotFoundException("Track Name Cell at row " + i + " is empty.");
						}
						break;

					case 8:
						if (!cell.isEmpty()) {
							subTrack = this.subTrackRepository.findByLearningTrackEntityAndNameIgnoreCase(learningTrack,
									cell.trim());
							if (subTrack != null) {
								attendanceEntity.setSubTrackName(cell.trim());
							} else {
								throw new ResourceNotFoundException("Learning track " + "'" + learningTrack.getName()
										+ "'" + " does not have Sub track Name " + "'" + cell + "'");
							}
						} else {
							throw new ResourceNotFoundException("Sub track Name Cell at row " + i + " is empty.");
						}
						break;

					case 9:
						
							if (cell != null && cell.equals("LOCKED")) {

								attendanceEntity.setLockAttendance(true);
//							} else {
//								throw new ResourceNotFoundException("Lock Attendance Cell at row " + i + " is empty.");
							}

						
						break;

					case 10:
						if (!cell.isEmpty()) {
							AttendanceStatus status = AttendanceStatus.valueOf(cell.toString().toUpperCase());
							attendanceEntity.setAttendance(status.value);
						} else {
							throw new ResourceNotFoundException("Attendance Status Cell at row " + i + " is empty.");
						}

						break;
						
					case 11:
						if (!cell.isEmpty()) {
							float floatValue = Float.parseFloat(cell);
							if(floatValue>=0 && floatValue<=10) {
							attendanceEntity.setPreAssesment(floatValue);
							}else {
								throw new IllegalArgumentException("Pre Assesment at row " + i + " should be between 0 to 10 ");
							}
						} else {
							attendanceEntity.setPreAssesment(null);
						}

						break;
						
					case 12:
						if (!cell.isEmpty()) {
							float floatValue = Float.parseFloat(cell);
							if(floatValue>=0 && floatValue<=10) {
							attendanceEntity.setPostAssesment(floatValue);
							}else {
								throw new IllegalArgumentException("Post Assesment at row " + i + " should be between 0 to 10 ");
							}
						} else {
							attendanceEntity.setPostAssesment(null);
						}

						break;
					}

				}
				UserTrackEntity userTrackEntity = this.userTrackRepository
						.findByTrackIdAndUserEntityAndSubtrackId(learningTrack, user, subTrack);

				if (userTrackEntity == null) {
					throw new ResourceNotFoundException("`" + record[3].toUpperCase() + "` at row " + i
							+ " has not enrolled in the `" + record[7].toUpperCase() + "` learning track.");
				}

				if (EnrollStatus.valueOf(userTrackEntity.getEnrollStatus()) != EnrollStatus.ACCEPT) {
					throw new ResourceNotFoundException("`" + record[3] + "` at row " + i
							+ " has not enrolled in the learning track `" + record[7].toUpperCase());
				}

				attendanceEntities.add(attendanceEntity); /// temp add
			}

			this.attendanceRepository.saveAll(attendanceEntities); // save temp

		}
		return bulkUpload.getId();

	}

	@Override
	public void saveToAttendanceTable(Long bulkUploadId, Long userId) throws Exception {

		List<AttendanceEntity> list = this.attendanceRepository.findByBulkId(bulkUploadId);

		ArrayList<Attendance> fromTempToMain = new ArrayList<>();
		ArrayList<UserTrackEntity> userTrackList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			UserEntity userEntity = this.userRepository.findByEmailIgnoreCase(list.get(i).getEmail());

			LearningTrackEntity entity = this.learningTrackRepository
					.findByNameIgnoreCase(list.get(i).getTrackName().trim());
			SubTrackEntity subTrackEntity = this.subTrackRepository.findByLearningTrackEntityAndNameIgnoreCase(entity,
					list.get(i).getSubTrackName());
			UserTrackEntity userTrackEntity = this.userTrackRepository.findByTrackIdAndUserEntityAndSubtrackId(entity,
					userEntity, subTrackEntity);

//			List<Long> trackList = this.trackDepartmentRepository.findDepartmentIdByTrackId(entity.getId());

			GplFunctionEntity function = this.functionRepo
					.findByNameIgnoreCaseAndIsActiveTrue(list.get(i).getFunctionName());

			AttendanceStatus status = AttendanceStatus.valueOf(list.get(i).getAttendance());
			Attendance attendance1 = this.attendanceMainRepository.findByLearningTrackIdAndUserIdAndSubTrackId(entity,
					userEntity, subTrackEntity);

			if (attendance1 != null) {
				if (attendance1.getLockAttendance() == false) {

					attendance1.setDate(list.get(i).getDate());
					attendance1.setFunctionId(function);
					attendance1.setZone(list.get(i).getZone());
					attendance1.setCompleteDateOfAttendance(list.get(i).getCompleteDateOfAttendance());
					attendance1.setStarPerformer(list.get(i).getStarPerformer());
					attendance1.setCreatedBy(userId);
					attendance1.setLearningTrackId(entity);
					attendance1.setUserId(userEntity);
					attendance1.setSubTrackId(subTrackEntity);
					attendance1.setLockAttendance(list.get(i).getLockAttendance());
					attendance1.setAttendance(status.value);
					attendance1.setPreAssesment(list.get(i).getPreAssesment());
					attendance1.setPostAssesment(list.get(i).getPostAssesment());
					this.attendanceMainRepository.save(attendance1);
					// fromTempToMain.add(attendance1);

				}
			} else {
				Attendance attendance = new Attendance();

				attendance.setDate(list.get(i).getDate());
				attendance.setFunctionId(function);
				attendance.setZone(list.get(i).getZone());
				attendance.setCompleteDateOfAttendance(list.get(i).getCompleteDateOfAttendance());
				attendance.setStarPerformer(list.get(i).getStarPerformer());
				attendance.setCreatedBy(userId);
				attendance.setLearningTrackId(entity);
				attendance.setUserId(userEntity);
				attendance.setSubTrackId(subTrackEntity);
				attendance.setLockAttendance(list.get(i).getLockAttendance());
				attendance.setAttendance(status.value);
				attendance.setPreAssesment(list.get(i).getPreAssesment());
				attendance.setPostAssesment(list.get(i).getPostAssesment());
				this.attendanceMainRepository.save(attendance);

				// fromTempToMain.add(attendance);
			}

			userTrackEntity.setCompleteDate(list.get(i).getCompleteDateOfAttendance());
			userTrackEntity.setIsStarPerformer(list.get(i).getStarPerformer());
			userTrackEntity.setStatus(2);
			userTrackEntity.setPostAssesment(list.get(i).getPostAssesment());
			userTrackEntity.setPreAssesment(list.get(i).getPreAssesment());
			userTrackList.add(userTrackEntity);
		}

		this.attendanceMainRepository.saveAll(fromTempToMain);

		List<UserTrackEntity> userTrackEntity = this.userTrackRepository.saveAll(userTrackList);

		addCompleteDate(userTrackEntity);

	}

	private void addCompleteDate(List<UserTrackEntity> userTrackEntity) {

		for (int i = 0; i < userTrackEntity.size(); i++) {
			boolean flag = true;
			Date dummyDate = null;

			List<UserTrackEntity> userTrack = userTrackRepository.findByTrackIdAndUserEntity(
					userTrackEntity.get(i).getTrackId(), userTrackEntity.get(i).getUserEntity());
			List<UserTrackEntity> list = userTrackRepository.findByTrackIdAndUserId(userTrackEntity.get(i).getTrackId(),
					userTrackEntity.get(i).getUserEntity());
			for (int j = 0; j < userTrack.size(); j++) {

				if (userTrack.get(j).getSubtrackId() != null && userTrack.get(j).getCompleteDate() == null) {
					flag = false;
				}

			}
//				else if (userTrack.get(j).getSubtrackId() != null && userTrack.get(j).getCompleteDate() != null) {
//			}
			if (flag) {
				Collections.sort(list, Comparator.comparing(UserTrackEntity::getCompleteDate).reversed());
				dummyDate = list.get(0).getCompleteDate();
//						Date lastDate = userTrack.get(i).getCompleteDate();
//						 if (lastDate.after(dummyDate)) {
//							 dummyDate = lastDate;
//			                }
				UserTrackEntity subTrack = userTrackRepository.findByUserIdAndTrackId(
						userTrackEntity.get(i).getUserEntity(), userTrackEntity.get(i).getTrackId());
				subTrack.setCompleteDate(dummyDate);
				subTrack.setStatus(2);
				userTrackRepository.save(subTrack);

			}
		}
	}

	@Override
	public Page<IListAttendance> getAllAttendance(String search, AttendanceFilterDto requestDto,
			HttpServletResponse response, Pageable pageable) throws IOException {
		Page<IListAttendance> iListAttendance;
		
		iListAttendance = this.attendanceMainRepository.findByName(search,requestDto.getUserId() != null ? requestDto.getUserId() : "",
			    requestDto.getEdp() != null ? requestDto.getEdp() : "",
			    	    requestDto.getEmail() != null ? requestDto.getEmail() : "",
			    	    requestDto.getFunction() != null ? requestDto.getFunction() : "",
			    	    requestDto.getZone() != null ? requestDto.getZone() : "",
			    	    requestDto.getRegion() != null ? requestDto.getRegion() : "",
			    	    requestDto.getLevel() != null ? requestDto.getLevel() : "",
			    	    requestDto.getGrade() != null ? requestDto.getGrade() : "",
			    	    requestDto.getPosition() != null ? requestDto.getPosition() : "",
			    	    requestDto.getTrack() != null ? requestDto.getTrack() : "",
			    	    requestDto.getSubtrack() != null ? requestDto.getSubtrack() : "",
			    	    requestDto.getSubtrackCompleteDate() != null ? requestDto.getSubtrackCompleteDate() : "",
			    	    requestDto.getStarPerformer() != null ? requestDto.getStarPerformer() : "",
			    	    requestDto.getStatus() != null ? requestDto.getStatus() : "",
				pageable, IListAttendance.class);
                    
		return iListAttendance;
	}
	
	@Override
	public AttendanceDto updateAttendance(AttendanceDto attendanceDto, Long id, Long userId) throws Exception {

		Attendance attendance = this.attendanceMainRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ATTENDANCE_NOT_FOUND));

		LearningTrackEntity learningTrackEntity = this.learningTrackRepository
				.findByNameContainingIgnoreCase(attendanceDto.getTrackName());

		SubTrackEntity subTrackEntity = this.subTrackRepository
				.findByLearningTrackEntityAndNameIgnoreCase(learningTrackEntity, attendanceDto.getSubTrackName());

		UserEntity userEntity = this.userRepository.findById(attendanceDto.getUserId()).get();

		GplFunctionEntity function = this.functionRepo
				.findByNameIgnoreCaseAndIsActiveTrue(attendanceDto.getFunctionName());

		attendance.setDate(attendanceDto.getDate());
		attendance.setFunctionId(function);
		attendance.setLearningTrackId(learningTrackEntity);
		attendance.setSubTrackId(subTrackEntity);
		attendance.setCompleteDateOfAttendance(attendanceDto.getCompleteDateOfAttendance());
		attendance.setZone(attendanceDto.getZone());
		attendance.setStarPerformer(attendanceDto.getStarPerformer());
		attendance.setUpdatedBy(userId);
		attendance.setUserId(userEntity);

		attendanceMainRepository.save(attendance);
		return attendanceDto;

	}

	@Override
	public Page<IListAttendance> exportAttendance(HttpServletResponse response, Page<IListAttendance> responseList)
			throws IOException {

		StringBuilder builder = new StringBuilder();
		builder.append("date").append(",").append("email").append(",").append("employee_edp").append(",")
				.append("employee_name").append(",").append("complete_date_of_attendance").append(",")
				.append("function_name").append(",").append("zone").append(",").append("track_name").append(",")
				.append("subtrack_name").append(",").append("lock_attendance").append(",").append("attendance_status").append(",")
				.append("pre_assesment_score").append(",").append("post_assesment_score").append(",");
		builder.append('\n');

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		for (IListAttendance attendance : responseList) {

			builder.append(attendance.getDate() != null ? sdf.format(attendance.getDate()) : "").append(",")
					.append(attendance.getEmail() != null ? attendance.getEmail() : "").append(",")
					.append(attendance.getEmployeeEdp() != null ? attendance.getEmployeeEdp() : "").append(",")
					.append(attendance.getName() != null ? attendance.getName() : "").append(",")
					.append(attendance.getCompleteDateOfAttendance() != null
							? sdf.format(attendance.getCompleteDateOfAttendance())
							: "")
					.append(",").append(attendance.getFunctionName() != null ? attendance.getFunctionName() : "")
					.append(",").append(attendance.getZone() != null ? attendance.getZone() : "").append(",")
					.append(attendance.getLearningTrackName() != null ? attendance.getLearningTrackName() : "")
					.append(",").append(attendance.getSubTrackName() != null ? attendance.getSubTrackName() : "")
					.append(",").append(attendance.getLockAttendance()==true ? "LOCKED": "UNLOCKED"  )
					.append(",").append(attendance.getAttendance() != null ? attendance.getAttendance() : "")
					.append(",").append(attendance.getPreAssesmentScore() != null ? attendance.getPreAssesmentScore() : "")
					.append(",").append(attendance.getPostAssesmentScore() != null ? attendance.getPostAssesmentScore() : "")
					.append(",");
			builder.append('\n');

		}
		PrintWriter writer = response.getWriter();
		writer.print(builder.toString());
		writer.flush();
		writer.close();
		return responseList;

	}

	@Override
	public void saveAcceptedUserToAttendance(UserTrackEntity userTrack) {
		
		ArrayList<Attendance> saveList= new ArrayList<>();
		
		UserEntity userEntity = this.userRepository.findByEmailIgnoreCase(userTrack.getUserEntity().getEmail());
		
		List<UserTrackEntity> list = userTrackRepository.
				findByTrackIdAndUserId( userTrack.getTrackId(),userEntity);
		
		ArrayList<SubTrackEntity> list1= subTrackRepository.findByLearningTrackEntityId(userTrack.getTrackId().getId());
		ArrayList<Attendance> list3 = attendanceMainRepository.findByLearningTrackIdAndUserId(userTrack.getTrackId(), userEntity);
		
		if(list3.isEmpty()) {
		for(int i=0; i<list1.size();i++) {

		Attendance attendance = new Attendance();
		attendance.setUserId(userTrack.getUserEntity());
		attendance.setFunctionId(userEntity.getFunctionId());
		attendance.setLearningTrackId(userTrack.getTrackId());
		attendance.setSubTrackId(list.get(i).getSubtrackId());
		attendance.setZone(userEntity.getZone());
		attendance.setStarPerformer(false);
		saveList.add(attendance);
		attendanceMainRepository.saveAll(saveList);
		}
	}
	}
	@Override
	public void updateAttendanceStatus(Long id, AttendanceStatusDto statusDto) {
      
		AttendanceStatus status = AttendanceStatus.valueOf(statusDto.getStatus().toString());
		
		Attendance attendance = this.attendanceMainRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ATTENDANCE_NOT_FOUND));
		LocalDateTime currentDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of("UTC");
        Date date = Date.from(currentDateTime.atZone(zoneId).toInstant());

		if(attendance.getLockAttendance()==false) {
		        attendance.setAttendance(status.value);
		        attendance.setUpdatedAt(date);
		
		if(attendance.getCompleteDateOfAttendance()==null) {
		        attendance.setCompleteDateOfAttendance(attendance.getSubTrackId().getEndDate());
		        
		        UserTrackEntity entity= userTrackRepository.findByTrackIdAndUserEntityAndSubtrackId
						(attendance.getLearningTrackId(), attendance.getUserId(), attendance.getSubTrackId());
				entity.setCompleteDate(attendance.getSubTrackId().getEndDate());
				userTrackRepository.save(entity);
				
				addCompleteDateAfterMarkAttendance(attendance);
		}
		attendanceMainRepository.save(attendance);
		
		
		
		}else {
			throw new ResourceNotFoundException(ErrorMessageCode.ATTENDANCE_LOCKED);
		}
		
	}

	@Override
	public void deleteMultipleAttendance(DeleteId id, Long userId) {
		List<Attendance> entity = attendanceMainRepository.findAllById(id.getIds());
		if(entity.size() != id.getIds().size()) {
			throw new ResourceNotFoundException(ErrorMessageCode.ATTENDANCE_NOT_FOUND);
		}

		attendanceMainRepository.deleteAllByIdIn(id.getIds(),userId);

		
	}

	private void addCompleteDateAfterMarkAttendance(Attendance attendance) {
		
		List<UserTrackEntity> userTrackEntity= userTrackRepository.findByTrackIdAndUserEntity
				(attendance.getLearningTrackId(), attendance.getUserId());
		
		for (int i = 0; i < userTrackEntity.size(); i++) {
			boolean flag = true;
			Date dummyDate = null;

			List<UserTrackEntity> userTrack = userTrackRepository.findByTrackIdAndUserEntity(
					userTrackEntity.get(i).getTrackId(), userTrackEntity.get(i).getUserEntity());
			List<UserTrackEntity> list = userTrackRepository.findByTrackIdAndUserId(userTrackEntity.get(i).getTrackId(),
					userTrackEntity.get(i).getUserEntity());
			for (int j = 0; j < userTrack.size(); j++) {

				if (userTrack.get(j).getSubtrackId() != null && userTrack.get(j).getCompleteDate() == null) {
					flag = false;
				}

			}
			if (flag) {
				Collections.sort(list, Comparator.comparing(UserTrackEntity::getCompleteDate).reversed());
				dummyDate = list.get(0).getCompleteDate();

				UserTrackEntity subTrack = userTrackRepository.findByUserIdAndTrackId(
						userTrackEntity.get(i).getUserEntity(), userTrackEntity.get(i).getTrackId());
				subTrack.setCompleteDate(dummyDate);
				subTrack.setStatus(2);
				userTrackRepository.save(subTrack);

			}
		}
	}

	@Override
	public void markMultipleStarperformer(StarperformerDto id, Long userId) {
		List<Attendance> entity = attendanceMainRepository.findAllById(id.getIds());
		if(entity.size() != id.getIds().size()) {
			throw new ResourceNotFoundException(ErrorMessageCode.ATTENDANCE_NOT_FOUND);
		}

		attendanceMainRepository.markAllByIdIn(id.getIds(),id.getStarperformer(),userId);
		
	}
	
}
