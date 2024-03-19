package com.alchemy.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.AssignRole;
import com.alchemy.entities.BulkUploadInformation;
import com.alchemy.entities.EnrollNowTemp;
import com.alchemy.entities.EnrollStatus;
import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.LevelEntity;
import com.alchemy.entities.MailTemplate;
import com.alchemy.entities.ModuleMasterEntity;
import com.alchemy.entities.RoleEntity;
import com.alchemy.entities.Status;
import com.alchemy.entities.SubTrackEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.entities.UserTrackEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListUserTrack;
import com.alchemy.repositories.BulkUploadInformationRepository;
import com.alchemy.repositories.EnrollNowTempRepository;
import com.alchemy.repositories.GplFunctionRepository;
import com.alchemy.repositories.LearningTrackRepository;
import com.alchemy.repositories.LevelRepository;
import com.alchemy.repositories.LevelTrackReopsitory;
import com.alchemy.repositories.MailRepository;
import com.alchemy.repositories.ModuleMasterRepository;
import com.alchemy.repositories.RoleRepository;
import com.alchemy.repositories.SubTrackRepository;
import com.alchemy.repositories.TrackDepartmentRepository;
import com.alchemy.repositories.TrackGplFunctionRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.repositories.UserTrackRepository;
import com.alchemy.serviceInterface.AttendanceInterface;
import com.alchemy.serviceInterface.AuthInterface;
import com.alchemy.serviceInterface.EmailInterface;
import com.alchemy.serviceInterface.EnrollNowInterface;
import com.alchemy.serviceInterface.InviteServiceInterface;
import com.alchemy.serviceInterface.UserRoleInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Validator;
import com.opencsv.CSVReader;

@Service
public class EnrollNowServiceImpl implements EnrollNowInterface {

	@Autowired
	private ModuleMasterRepository moduleMasterRepository;

	@Autowired
	private BulkUploadInformationRepository bulkUploadInformationRepository;

	@Autowired
	private EnrollNowTempRepository enrollNowTempRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LearningTrackRepository learningTrackRepository;

	@Autowired
	private GplFunctionRepository functionRepository;

	@Autowired
	private LevelRepository levelRepository;

	@Autowired
	private UserTrackRepository userTrackRepository;

	@Autowired
	private SubTrackRepository subTrackRepository;

	@Autowired
	private TrackDepartmentRepository trackDepartmentRepository;

	@Autowired
	private TrackGplFunctionRepository trackGplFunctionRepository;

	@Autowired
	private MailRepository mailRepository;

	@Autowired
	private EmailInterface emailInterface;

	@Autowired
	private AttendanceInterface attendanceInterface;

	@Autowired
	private InviteServiceInterface inviteServiceInterface;

	@Value("${url}")
	private String frontendURL;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleInterface userRoleInterface;

	@Autowired
	private LevelTrackReopsitory levelTrackReopsitory;

	@Autowired
	private AuthInterface authInterface;

	@Override
	public Long saveExcelFile(MultipartFile multipartFile, Long userId, String moduleName)
			throws IOException, Exception {

		if (!multipartFile.getOriginalFilename().endsWith(".csv")) {
			throw new ResourceNotFoundException("File must be a CSV");
		}
		if (multipartFile.isEmpty()) {
			throw new IllegalArgumentException(ErrorMessageCode.PLEASE_UPLOAD_FILE);
		}

		List<EnrollNowTemp> list = new ArrayList<EnrollNowTemp>();
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

		List<String> names = Arrays.asList("employee_edp", "employee_name", "employee_email", "zone", "region",
				"project", "employee_level", "employee_grade", "position_title", "function_name", "track_name",
				"enroll_status");

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
				throw new ResourceNotFoundException("file is not valid at column name:" + arrayList.get(n));
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
//		for (int i = 1; i < records.size(); i++) {
		for (int i = records.size() - 1; i >= 1; i--) {

			String[] record = records.get(i);
			boolean isEmpty = true;
			for (String cell : record) {
				if (cell != null && !cell.trim().isEmpty()) {
					isEmpty = false;
					break;
				}
			}
			if (!isEmpty) {

				EnrollNowTemp enrollNowTemp = new EnrollNowTemp();
				enrollNowTemp.setBulkId(bulkUpload.getId());

				UserEntity user = new UserEntity();
				LearningTrackEntity learningTrack = new LearningTrackEntity();

				for (int j = 0; j < record.length; j++) {
					String cell = record[j];
					switch (j) {
					case 0:
						// if (cell != null && !cell.toString().isEmpty() &&
						// !"".equals(cell.toString())) {
						enrollNowTemp.setEmployeeEdp(cell);

//					} else {
//						throw new ResourceNotFoundException(
//								"Employee edp Cell at row " + i + " and column " + j + " is empty.");
//					}
						break;

					case 1:
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//						UserEntity name = this.userRepository.findByEmailIgnoreCase(cell);
//						if (name != null) {
							enrollNowTemp.setName(cell);
//						} else {
//							throw new ResourceNotFoundException("Name " + "'" + cell + "'" + " not found");
//						}

						} else {
							throw new ResourceNotFoundException(
									"Name Cell at row " + i + " and column " + Constant.columnNames[j] + " is empty.");
						}
						break;

					case 2:
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
							user = this.userRepository.findByEmailIgnoreCase(cell.trim());
							if (Validator.isValidforEmail(cell.toString())) {
								enrollNowTemp.setEmail(cell);
							} else {
								throw new ResourceNotFoundException("Email " + "'" + cell + "'" + "is not valid");
							}

						} else {
							throw new ResourceNotFoundException(
									"Email Cell at row " + i + " and column " + Constant.columnNames[j] + " is empty.");
						}
						break;

					case 3:
						// if (cell != null && !cell.toString().isEmpty() &&
						// !"".equals(cell.toString())) {
						enrollNowTemp.setZone(cell);

//					} else {
//						throw new ResourceNotFoundException(
//								"Zone Cell at row " + i + " and column " + j + " is empty.");
//					}
						break;

					case 4:
						// if (cell != null && !cell.toString().isEmpty() &&
						// !"".equals(cell.toString())) {
						enrollNowTemp.setRegion(cell);

//					} else {
//						throw new ResourceNotFoundException(
//								"Region Cell at row " + i + " and column " + j + " is empty.");
//					}
						break;

					case 5:
						// if (cell != null && !cell.toString().isEmpty() &&
						// !"".equals(cell.toString())) {
						enrollNowTemp.setProject(cell);

//					} else {
//						throw new ResourceNotFoundException(
//								"Project Cell at row " + i + " and column " + j + " is empty.");
//					}
						break;

					case 6:
						// if (cell != null && !cell.toString().isEmpty() &&
						// !"".equals(cell.toString())) {
						enrollNowTemp.setEmployeeLevel(cell);

//					} else {
//						throw new ResourceNotFoundException(
//								"Employee level Cell at row " + i + " and column " + j + " is empty.");
//					}
						break;

					case 7:
						// if (cell != null && !cell.toString().isEmpty() &&
						// !"".equals(cell.toString())) {
						enrollNowTemp.setEmployeeGrade(cell);

//					} else {
//						throw new ResourceNotFoundException(
//								"Employee grade Cell at row " + i + " and column " + j + " is empty.");
//					}
						break;

					case 8:
						// if (cell != null && !cell.toString().isEmpty() &&
						// !"".equals(cell.toString())) {
						enrollNowTemp.setPositionTitle(cell);

//					} else {
//						throw new ResourceNotFoundException(
//								"Position title Cell at row " + i + " and column " + j + " is empty.");
//					}
						break;
					case 9:
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
							GplFunctionEntity departmentEntity = this.functionRepository.findByNameIgnoreCase(cell);
							if (departmentEntity != null) {
								enrollNowTemp.setFunctionName(cell);
							} else {
								throw new ResourceNotFoundException("Function name " + "'" + cell + "'" + " not found");
							}
						} else {
							throw new ResourceNotFoundException("Function name Cell at row " + i + " and column "
									+ Constant.columnNames[j] + " is empty.");
						}
						break;

					case 10:
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
							learningTrack = this.learningTrackRepository.findByNameIgnoreCase(cell.trim());

							if (learningTrack != null) {
								if (learningTrack.getIsVisible() == true) {
									enrollNowTemp.setTrackName(cell);
								} else {
									throw new ResourceNotFoundException(
											"Track Name " + "'" + cell + "'" + " is not enabled");
								}
							} else {
								throw new ResourceNotFoundException("Track Name " + "'" + cell + "'" + " not found");
							}
						} else {
							throw new ResourceNotFoundException("Track name Cell at row " + i + " and column "
									+ Constant.columnNames[j] + " is empty.");
						}
						break;

					case 11:
						if (!cell.isEmpty()) {
							EnrollStatus status = EnrollStatus.valueOf(cell.toString().toUpperCase());
							enrollNowTemp.setEnrollStatus(status.value);
						} else {
							enrollNowTemp.setEnrollStatus(EnrollStatus.SUBMITTED.value);
						}
						break;

					}
				}
//				if (TrackStatusEnum.valueOf(learningTrack.getStatus()) != TrackStatusEnum.LAUNCHED) {
//					throw new ResourceNotFoundException("'" + record[10] + "' learning track has not launched.");
//				}
				if (learningTrack.getEnrollCloseDate() != null) {
					LocalDate enrollClose = learningTrack.getEnrollCloseDate().toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate today = LocalDate.now();
					if (today.compareTo(enrollClose) > 0) {
						throw new ResourceNotFoundException(
								"At row " + i + " enrollment closed for the learning track '" + record[10]
										+ "'. Please choose a different learning track.");
					}
				}

				if (learningTrack.getEnrollStartDate() != null) {
					LocalDate enrollClose = learningTrack.getEnrollStartDate().toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate today = LocalDate.now();
					if (today.compareTo(enrollClose) < 0) {
						throw new ResourceNotFoundException(
								"At row " + i + " enrollment for learning track is not started '" + record[10]
										+ "'. Please choose a different learning track.");
					}
				}

				list.add(enrollNowTemp);
			}
			this.enrollNowTempRepository.saveAll(list);

		}
		return bulkUpload.getId();
	}

	@Override
	public void saveToEnrollNowTable(Long bulkUploadId, Long userId) throws Exception {

		List<EnrollNowTemp> templist = this.enrollNowTempRepository.findByBulkId(bulkUploadId);

		ArrayList<UserTrackEntity> list = new ArrayList<UserTrackEntity>();

		for (int i = 0; i < templist.size(); i++) {

			EnrollNowTemp enrollNowTemp = templist.get(i);
			LearningTrackEntity learningTrackEntity = this.learningTrackRepository
					.findByNameIgnoreCase(templist.get(i).getTrackName().trim());

			ArrayList<SubTrackEntity> subTrackEntity = this.subTrackRepository
					.findByLearningTrackEntityId(learningTrackEntity.getId());

			UserEntity userEntity = this.userRepository.findByEmailIgnoreCase(templist.get(i).getEmail());
			System.err.println("userEntity    " + userEntity);
			if (userEntity == null) {

//				UserEntity entity = new UserEntity();
//				entity.setEmail(templist.get(i).getEmail());
//				userRepository.save(entity);

				UserEntity entity = authInterface.saveNewEnrollEmployee(templist.get(i).getEmail());

				RoleEntity role = roleRepository.findByRoleNameIgnoreCaseAndIsActiveTrue("USER");

				ArrayList<Long> array = new ArrayList<>();
				array.add(role.getId());

				AssignRole addRole = new AssignRole(entity.getId(), array);

				userRoleInterface.add(addRole);

				UUID uuid = UUID.randomUUID();

				inviteServiceInterface.add(uuid, entity.getId());

				String url = frontendURL + uuid;

				MailTemplate mailtemplate = mailRepository.findBytemplatename(Constant.ONBOARD_TEMPLATE_NAME);
				if (null != mailtemplate) {
					String template = mailtemplate.getMailtemp();
					String replaceString = template.replace("USER_NAME", enrollNowTemp.getName())
							.replace("ONBOARDING_URL", url);

					emailInterface.sendSimpleMessage(entity.getEmail(), "Onboarding - Welcome to GPL !!",
							replaceString);

				} else {
					throw new ResourceNotFoundException(ErrorMessageCode.MAILTEMPLATE_NOT_FOUND);
				}

				List<UserTrackEntity> trackEntity = this.userTrackRepository
						.findByUserEntityIdAndTrackId(entity.getId(), learningTrackEntity);
				if (!trackEntity.isEmpty()) {
					throw new ResourceNotFoundException("'" + entity.getEmail() + "'" + "has already enrolled'"
							+ learningTrackEntity.getName() + "'learning track.");
				}

				GplFunctionEntity functionEntity = this.functionRepository
						.findByNameIgnoreCase(templist.get(i).getFunctionName());

				List<Long> trackList = this.trackGplFunctionRepository
						.findDepartmentIdByTrackId(learningTrackEntity.getId());

				GplFunctionEntity dept = entity.getFunctionId();

				if (!trackList.contains(dept.getId())) {

					throw new ResourceNotFoundException("User department does not match with track department at row '"
							+ (i + 1) + "' and column J");

				}

				List<Long> levelList = this.levelTrackReopsitory.findLevelIdByTrackId(learningTrackEntity.getId());
				LevelEntity userLevel = entity.getLevelId();
				if (!levelList.contains(userLevel.getId())) {
					throw new ResourceNotFoundException(
							"User level does not match with track level at row '" + (i + 1) + "' and column G");
				}

				UserTrackEntity userTrackEntity = new UserTrackEntity();
				userTrackEntity.setUserEntity(entity);
				userTrackEntity.setCreatedBy(userId);
				userTrackEntity.setTrackId(learningTrackEntity);
				userTrackEntity.setEnrollStatus(templist.get(i).getEnrollStatus());
				Status enrollStatus = Status.valueOf("TO_DO");

				userTrackEntity.setStatus(enrollStatus.value);
				userTrackEntity.setFunctionName(functionEntity.getName());

				list.add(userTrackEntity);
				this.userTrackRepository.saveAll(list);

				for (int j = 0; j < subTrackEntity.size(); j++) {
					UserTrackEntity userTrack = new UserTrackEntity();
					userTrack.setUserEntity(entity);
					userTrack.setCreatedBy(userId);
					userTrack.setTrackId(learningTrackEntity);
					userTrack.setSubtrackId(subTrackEntity.get(j));
					userTrack.setEnrollStatus(templist.get(i).getEnrollStatus());
					userTrack.setStatus(enrollStatus.value);
					userTrack.setFunctionName(functionEntity.getName());

					this.userTrackRepository.save(userTrack);

				}

				EnrollStatus status = EnrollStatus.valueOf(templist.get(i).getEnrollStatus());
				emailInterface.sendEnrollmentMain(status.toString(), userTrackEntity);

			} else {

				List<UserTrackEntity> trackEntity = this.userTrackRepository
						.findByUserEntityIdAndTrackId(userEntity.getId(), learningTrackEntity);
				if (!trackEntity.isEmpty()) {
					throw new ResourceNotFoundException("'" + userEntity.getEmail() + "'" + "has already enrolled'"
							+ learningTrackEntity.getName() + "'learning track.");
				}

				GplFunctionEntity gplFunctionEntity = this.functionRepository
						.findByNameIgnoreCase(templist.get(i).getFunctionName());
				userEntity.setFunctionId(gplFunctionEntity);

				List<Long> trackList = this.trackGplFunctionRepository
						.findDepartmentIdByTrackId(learningTrackEntity.getId());
				GplFunctionEntity dept = userEntity.getFunctionId();
				if (!trackList.contains(dept.getId())) {
					System.err.println(i);
					throw new ResourceNotFoundException("User department does not match with track department at row '"
							+ (i + 1) + "' and column J");

				}

				List<Long> levelList = this.levelTrackReopsitory.findLevelIdByTrackId(learningTrackEntity.getId());
				LevelEntity userLevel = userEntity.getLevelId();
				if (!levelList.contains(userLevel.getId())) {
					throw new ResourceNotFoundException(
							"User level does not match with track level at row '" + (i + 1) + "' and column G");
				}

				UserTrackEntity userTrackEntity = new UserTrackEntity();
				userTrackEntity.setUserEntity(userEntity);
				userTrackEntity.setCreatedBy(userId);
				userTrackEntity.setTrackId(learningTrackEntity);
				userTrackEntity.setEnrollStatus(templist.get(i).getEnrollStatus());
				userTrackEntity.setFunctionName(gplFunctionEntity.getName());

				list.add(userTrackEntity);
				this.userTrackRepository.saveAll(list);

				for (int j = 0; j < subTrackEntity.size(); j++) {
					UserTrackEntity userTrack = new UserTrackEntity();
					userTrack.setUserEntity(userEntity);
					userTrack.setCreatedBy(userId);
					userTrack.setTrackId(learningTrackEntity);
					userTrack.setSubtrackId(subTrackEntity.get(j));
					userTrack.setEnrollStatus(templist.get(i).getEnrollStatus());
					userTrack.setFunctionName(gplFunctionEntity.getName());

					this.userTrackRepository.save(userTrack);
				}

				EnrollStatus status = EnrollStatus.valueOf(templist.get(i).getEnrollStatus());
				emailInterface.sendEnrollmentMain(status.toString(), userTrackEntity);
			}

		}

	}

	@Override
	public Page<IListUserTrack> exportEnrollNowToExcel(HttpServletResponse response, Page<IListUserTrack> listUsers)
			throws IOException {

		StringBuilder builder = new StringBuilder();
		builder.append("employee_edp").append(",").append("employee_name").append(",").append("employee_email")
				.append(",").append("zone").append(",").append("region").append(",").append("project").append(",")
				.append("employee_level").append(",").append("employee_grade").append(",").append("position_title")
				.append(",").append("function_name").append(",").append("track_name").append(",")
				.append("enroll_status").append(",");
		builder.append('\n');

		for (IListUserTrack user : listUsers) {
			builder.append(user.getEmployeeEdp() != null ? user.getEmployeeEdp() : "").append(",")
					.append(user.getUserName() != null ? user.getUserName() : "").append(",")
					.append(user.getEmail() != null ? user.getEmail() : "").append(",")
					.append(user.getZone() != null ? user.getZone() : "").append(",")
					.append(user.getRegion() != null ? user.getRegion() : "").append(",")
					.append(user.getProject() != null ? user.getProject() : "").append(",")
					.append(user.getEmployeeLevel() != null ? user.getEmployeeLevel() : "").append(",")
					.append(user.getEmployeeGrade() != null ? user.getEmployeeGrade() : "").append(",")
					.append(user.getPositionTitle() != null ? user.getPositionTitle() : "").append(",")
					.append(user.getFunctionName() != null ? user.getFunctionName() : "").append(",")
					.append(user.getTrackName() != null ? user.getTrackName() : "").append(",")
					.append(user.getEnrollStatus() != null ? user.getEnrollStatus() : "").append(",");
			builder.append('\n');

		}
		PrintWriter writer = response.getWriter();
		writer.print(builder.toString());
		writer.flush();
		writer.close();
		return listUsers;
	}

}

//	@Override
//	public Page<IListEnroll> getAllUserEnroll(String search, String pageNo, String pageSize) {
//		Page<IListEnroll> iListEnroll;
//
//		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
//
//		iListEnroll = this.enrollNowRepository.findByOrderByIdDesc(pageable, IListEnroll.class);
//
//		return iListEnroll;
//	}
