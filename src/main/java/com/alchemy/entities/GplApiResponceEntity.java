package com.alchemy.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GplApiResponceEntity {

	private String employeeId;

	@JsonProperty("username")
	private String username;

	private String employeeStatus;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("middleName")
	private String middleName;

	private String businessEmailInformationEmailAddress;

	private String cellPhoneInformationPhoneNumber;

	private String jobTitle;

	private String supervisor;

	private String event;

//	private Date eventDate;

	private String eventDate;

	private String eventReason;

	private String companyCompanyName;

	private String divisionDivisionName;

	private String locationLocationName;

	private String departmentDepartmentName;

	private String salaryGradeSalaryGradeName;

	private String gender;

	private String bloodGroup;

	// private Date dateOfBirth;

	private String dateOfBirth;

	private String indiaDrivingLicenseNationalIdInformation;

	private String managerUserSysId;

//	private Date employmentDetailsDateOfJoiningTheCurrentCompany;
	private String employmentDetailsDateOfJoiningTheCurrentCompany;

	private String companyCompanyId;

	private String divisionDivisionCode;

	private String locationLocationCode;

	private String departmentDepartmentCode;

	private String indiaPermanentAccountNumberNationalIdInformation;

	private String maritalStatus;

	private String indiaGlobalInformationFatherName;

	private String permanentAddress1;

	private String costCenterCostCenterCode;

	// private Date cellPhoneInformationLastModifiedOn;

	private String cellPhoneInformationLastModifiedOn;

	// private Date biographicalInformationLastModifiedOn;

	private String biographicalInformationLastModifiedOn;

	private String businessUnitBusinessUnitCode;

	private String businessUnitBusinessUnitName;

	private String level;

	private String costCenter;

	private String jobClassificationJobCode;

	private String jobClassificationJobTitle;

	private String salaryGradeSalaryGradeId;

	private String salaryGradeSalaryGradeLevel;

	private String functionName;

	private String functionJobFunctionId;

	// private Date personalInformationLastModifiedOn;

	private String personalInformationLastModifiedOn;

	// private Date lastModifiedOn;

	private String lastModifiedOn;

	// private Date employmentDetailsDateOfJoiningGilac;

	private String employmentDetailsDateOfJoiningGilac;

	private String empJobInfoTCustomVchar13;

	private String empJobInfoTCustomVchar14;

	private String empJobInfoTCustomVchar15;

	private String subBusinessUnitId;

	private String subBusinessUnitName;

	// private Date separationDate;

	private String separationDate;

	private String add1;

	private String add2;

	private String add3;

	private String add4;

	private String pinCode;

	public GplApiResponceEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GplApiResponceEntity(String employeeId, String username, String employeeStatus, String firstName,
			String lastName, String middleName, String businessEmailInformationEmailAddress,
			String cellPhoneInformationPhoneNumber, String jobTitle, String supervisor, String event, String eventDate,
			String eventReason, String companyCompanyName, String divisionDivisionName, String locationLocationName,
			String departmentDepartmentName, String salaryGradeSalaryGradeName, String gender, String bloodGroup,
			String dateOfBirth, String indiaDrivingLicenseNationalIdInformation, String managerUserSysId,
			String employmentDetailsDateOfJoiningTheCurrentCompany, String companyCompanyId,
			String divisionDivisionCode, String locationLocationCode, String departmentDepartmentCode,
			String indiaPermanentAccountNumberNationalIdInformation, String maritalStatus,
			String indiaGlobalInformationFatherName, String permanentAddress1, String costCenterCostCenterCode,
			String cellPhoneInformationLastModifiedOn, String biographicalInformationLastModifiedOn,
			String businessUnitBusinessUnitCode, String businessUnitBusinessUnitName, String level, String costCenter,
			String jobClassificationJobCode, String jobClassificationJobTitle, String salaryGradeSalaryGradeId,
			String salaryGradeSalaryGradeLevel, String functionName, String functionJobFunctionId,
			String personalInformationLastModifiedOn, String lastModifiedOn, String employmentDetailsDateOfJoiningGilac,
			String empJobInfoTCustomVchar13, String empJobInfoTCustomVchar14, String empJobInfoTCustomVchar15,
			String subBusinessUnitId, String subBusinessUnitName, String separationDate, String add1, String add2,
			String add3, String add4, String pinCode) {
		super();
		this.employeeId = employeeId;
		this.username = username;
		this.employeeStatus = employeeStatus;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.businessEmailInformationEmailAddress = businessEmailInformationEmailAddress;
		this.cellPhoneInformationPhoneNumber = cellPhoneInformationPhoneNumber;
		this.jobTitle = jobTitle;
		this.supervisor = supervisor;
		this.event = event;
		this.eventDate = eventDate;
		this.eventReason = eventReason;
		this.companyCompanyName = companyCompanyName;
		this.divisionDivisionName = divisionDivisionName;
		this.locationLocationName = locationLocationName;
		this.departmentDepartmentName = departmentDepartmentName;
		this.salaryGradeSalaryGradeName = salaryGradeSalaryGradeName;
		this.gender = gender;
		this.bloodGroup = bloodGroup;
		this.dateOfBirth = dateOfBirth;
		this.indiaDrivingLicenseNationalIdInformation = indiaDrivingLicenseNationalIdInformation;
		this.managerUserSysId = managerUserSysId;
		this.employmentDetailsDateOfJoiningTheCurrentCompany = employmentDetailsDateOfJoiningTheCurrentCompany;
		this.companyCompanyId = companyCompanyId;
		this.divisionDivisionCode = divisionDivisionCode;
		this.locationLocationCode = locationLocationCode;
		this.departmentDepartmentCode = departmentDepartmentCode;
		this.indiaPermanentAccountNumberNationalIdInformation = indiaPermanentAccountNumberNationalIdInformation;
		this.maritalStatus = maritalStatus;
		this.indiaGlobalInformationFatherName = indiaGlobalInformationFatherName;
		this.permanentAddress1 = permanentAddress1;
		this.costCenterCostCenterCode = costCenterCostCenterCode;
		this.cellPhoneInformationLastModifiedOn = cellPhoneInformationLastModifiedOn;
		this.biographicalInformationLastModifiedOn = biographicalInformationLastModifiedOn;
		this.businessUnitBusinessUnitCode = businessUnitBusinessUnitCode;
		this.businessUnitBusinessUnitName = businessUnitBusinessUnitName;
		this.level = level;
		this.costCenter = costCenter;
		this.jobClassificationJobCode = jobClassificationJobCode;
		this.jobClassificationJobTitle = jobClassificationJobTitle;
		this.salaryGradeSalaryGradeId = salaryGradeSalaryGradeId;
		this.salaryGradeSalaryGradeLevel = salaryGradeSalaryGradeLevel;
		this.functionName = functionName;
		this.functionJobFunctionId = functionJobFunctionId;
		this.personalInformationLastModifiedOn = personalInformationLastModifiedOn;
		this.lastModifiedOn = lastModifiedOn;
		this.employmentDetailsDateOfJoiningGilac = employmentDetailsDateOfJoiningGilac;
		this.empJobInfoTCustomVchar13 = empJobInfoTCustomVchar13;
		this.empJobInfoTCustomVchar14 = empJobInfoTCustomVchar14;
		this.empJobInfoTCustomVchar15 = empJobInfoTCustomVchar15;
		this.subBusinessUnitId = subBusinessUnitId;
		this.subBusinessUnitName = subBusinessUnitName;
		this.separationDate = separationDate;
		this.add1 = add1;
		this.add2 = add2;
		this.add3 = add3;
		this.add4 = add4;
		this.pinCode = pinCode;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getBusinessEmailInformationEmailAddress() {
		return businessEmailInformationEmailAddress;
	}

	public void setBusinessEmailInformationEmailAddress(String businessEmailInformationEmailAddress) {
		this.businessEmailInformationEmailAddress = businessEmailInformationEmailAddress;
	}

	public String getCellPhoneInformationPhoneNumber() {
		return cellPhoneInformationPhoneNumber;
	}

	public void setCellPhoneInformationPhoneNumber(String cellPhoneInformationPhoneNumber) {
		this.cellPhoneInformationPhoneNumber = cellPhoneInformationPhoneNumber;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventReason() {
		return eventReason;
	}

	public void setEventReason(String eventReason) {
		this.eventReason = eventReason;
	}

	public String getCompanyCompanyName() {
		return companyCompanyName;
	}

	public void setCompanyCompanyName(String companyCompanyName) {
		this.companyCompanyName = companyCompanyName;
	}

	public String getDivisionDivisionName() {
		return divisionDivisionName;
	}

	public void setDivisionDivisionName(String divisionDivisionName) {
		this.divisionDivisionName = divisionDivisionName;
	}

	public String getLocationLocationName() {
		return locationLocationName;
	}

	public void setLocationLocationName(String locationLocationName) {
		this.locationLocationName = locationLocationName;
	}

	public String getDepartmentDepartmentName() {
		return departmentDepartmentName;
	}

	public void setDepartmentDepartmentName(String departmentDepartmentName) {
		this.departmentDepartmentName = departmentDepartmentName;
	}

	public String getSalaryGradeSalaryGradeName() {
		return salaryGradeSalaryGradeName;
	}

	public void setSalaryGradeSalaryGradeName(String salaryGradeSalaryGradeName) {
		this.salaryGradeSalaryGradeName = salaryGradeSalaryGradeName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIndiaDrivingLicenseNationalIdInformation() {
		return indiaDrivingLicenseNationalIdInformation;
	}

	public void setIndiaDrivingLicenseNationalIdInformation(String indiaDrivingLicenseNationalIdInformation) {
		this.indiaDrivingLicenseNationalIdInformation = indiaDrivingLicenseNationalIdInformation;
	}

	public String getManagerUserSysId() {
		return managerUserSysId;
	}

	public void setManagerUserSysId(String managerUserSysId) {
		this.managerUserSysId = managerUserSysId;
	}

	public String getEmploymentDetailsDateOfJoiningTheCurrentCompany() {
		return employmentDetailsDateOfJoiningTheCurrentCompany;
	}

	public void setEmploymentDetailsDateOfJoiningTheCurrentCompany(
			String employmentDetailsDateOfJoiningTheCurrentCompany) {
		this.employmentDetailsDateOfJoiningTheCurrentCompany = employmentDetailsDateOfJoiningTheCurrentCompany;
	}

	public String getCompanyCompanyId() {
		return companyCompanyId;
	}

	public void setCompanyCompanyId(String companyCompanyId) {
		this.companyCompanyId = companyCompanyId;
	}

	public String getDivisionDivisionCode() {
		return divisionDivisionCode;
	}

	public void setDivisionDivisionCode(String divisionDivisionCode) {
		this.divisionDivisionCode = divisionDivisionCode;
	}

	public String getLocationLocationCode() {
		return locationLocationCode;
	}

	public void setLocationLocationCode(String locationLocationCode) {
		this.locationLocationCode = locationLocationCode;
	}

	public String getDepartmentDepartmentCode() {
		return departmentDepartmentCode;
	}

	public void setDepartmentDepartmentCode(String departmentDepartmentCode) {
		this.departmentDepartmentCode = departmentDepartmentCode;
	}

	public String getIndiaPermanentAccountNumberNationalIdInformation() {
		return indiaPermanentAccountNumberNationalIdInformation;
	}

	public void setIndiaPermanentAccountNumberNationalIdInformation(
			String indiaPermanentAccountNumberNationalIdInformation) {
		this.indiaPermanentAccountNumberNationalIdInformation = indiaPermanentAccountNumberNationalIdInformation;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getIndiaGlobalInformationFatherName() {
		return indiaGlobalInformationFatherName;
	}

	public void setIndiaGlobalInformationFatherName(String indiaGlobalInformationFatherName) {
		this.indiaGlobalInformationFatherName = indiaGlobalInformationFatherName;
	}

	public String getPermanentAddress1() {
		return permanentAddress1;
	}

	public void setPermanentAddress1(String permanentAddress1) {
		this.permanentAddress1 = permanentAddress1;
	}

	public String getCostCenterCostCenterCode() {
		return costCenterCostCenterCode;
	}

	public void setCostCenterCostCenterCode(String costCenterCostCenterCode) {
		this.costCenterCostCenterCode = costCenterCostCenterCode;
	}

	public String getCellPhoneInformationLastModifiedOn() {
		return cellPhoneInformationLastModifiedOn;
	}

	public void setCellPhoneInformationLastModifiedOn(String cellPhoneInformationLastModifiedOn) {
		this.cellPhoneInformationLastModifiedOn = cellPhoneInformationLastModifiedOn;
	}

	public String getBiographicalInformationLastModifiedOn() {
		return biographicalInformationLastModifiedOn;
	}

	public void setBiographicalInformationLastModifiedOn(String biographicalInformationLastModifiedOn) {
		this.biographicalInformationLastModifiedOn = biographicalInformationLastModifiedOn;
	}

	public String getBusinessUnitBusinessUnitCode() {
		return businessUnitBusinessUnitCode;
	}

	public void setBusinessUnitBusinessUnitCode(String businessUnitBusinessUnitCode) {
		this.businessUnitBusinessUnitCode = businessUnitBusinessUnitCode;
	}

	public String getBusinessUnitBusinessUnitName() {
		return businessUnitBusinessUnitName;
	}

	public void setBusinessUnitBusinessUnitName(String businessUnitBusinessUnitName) {
		this.businessUnitBusinessUnitName = businessUnitBusinessUnitName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getJobClassificationJobCode() {
		return jobClassificationJobCode;
	}

	public void setJobClassificationJobCode(String jobClassificationJobCode) {
		this.jobClassificationJobCode = jobClassificationJobCode;
	}

	public String getJobClassificationJobTitle() {
		return jobClassificationJobTitle;
	}

	public void setJobClassificationJobTitle(String jobClassificationJobTitle) {
		this.jobClassificationJobTitle = jobClassificationJobTitle;
	}

	public String getSalaryGradeSalaryGradeId() {
		return salaryGradeSalaryGradeId;
	}

	public void setSalaryGradeSalaryGradeId(String salaryGradeSalaryGradeId) {
		this.salaryGradeSalaryGradeId = salaryGradeSalaryGradeId;
	}

	public String getSalaryGradeSalaryGradeLevel() {
		return salaryGradeSalaryGradeLevel;
	}

	public void setSalaryGradeSalaryGradeLevel(String salaryGradeSalaryGradeLevel) {
		this.salaryGradeSalaryGradeLevel = salaryGradeSalaryGradeLevel;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionJobFunctionId() {
		return functionJobFunctionId;
	}

	public void setFunctionJobFunctionId(String functionJobFunctionId) {
		this.functionJobFunctionId = functionJobFunctionId;
	}

	public String getPersonalInformationLastModifiedOn() {
		return personalInformationLastModifiedOn;
	}

	public void setPersonalInformationLastModifiedOn(String personalInformationLastModifiedOn) {
		this.personalInformationLastModifiedOn = personalInformationLastModifiedOn;
	}

	public String getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public String getEmploymentDetailsDateOfJoiningGilac() {
		return employmentDetailsDateOfJoiningGilac;
	}

	public void setEmploymentDetailsDateOfJoiningGilac(String employmentDetailsDateOfJoiningGilac) {
		this.employmentDetailsDateOfJoiningGilac = employmentDetailsDateOfJoiningGilac;
	}

	public String getEmpJobInfoTCustomVchar13() {
		return empJobInfoTCustomVchar13;
	}

	public void setEmpJobInfoTCustomVchar13(String empJobInfoTCustomVchar13) {
		this.empJobInfoTCustomVchar13 = empJobInfoTCustomVchar13;
	}

	public String getEmpJobInfoTCustomVchar14() {
		return empJobInfoTCustomVchar14;
	}

	public void setEmpJobInfoTCustomVchar14(String empJobInfoTCustomVchar14) {
		this.empJobInfoTCustomVchar14 = empJobInfoTCustomVchar14;
	}

	public String getEmpJobInfoTCustomVchar15() {
		return empJobInfoTCustomVchar15;
	}

	public void setEmpJobInfoTCustomVchar15(String empJobInfoTCustomVchar15) {
		this.empJobInfoTCustomVchar15 = empJobInfoTCustomVchar15;
	}

	public String getSubBusinessUnitId() {
		return subBusinessUnitId;
	}

	public void setSubBusinessUnitId(String subBusinessUnitId) {
		this.subBusinessUnitId = subBusinessUnitId;
	}

	public String getSubBusinessUnitName() {
		return subBusinessUnitName;
	}

	public void setSubBusinessUnitName(String subBusinessUnitName) {
		this.subBusinessUnitName = subBusinessUnitName;
	}

	public String getSeparationDate() {
		return separationDate;
	}

	public void setSeparationDate(String separationDate) {
		this.separationDate = separationDate;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public String getAdd3() {
		return add3;
	}

	public void setAdd3(String add3) {
		this.add3 = add3;
	}

	public String getAdd4() {
		return add4;
	}

	public void setAdd4(String add4) {
		this.add4 = add4;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	
	@Override
    public String toString() {
        return "{\"employeeId\":\"" + employeeId + "\",\"username\":\"" + username +"\",\"employeeStatus\":\"" + employeeStatus+ "\",\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\","
                + "\"middleName\":\"" + middleName + "\",\"businessEmailInformationEmailAddress\":\"" + businessEmailInformationEmailAddress  + "\",\"cellPhoneInformationPhoneNumber\":\"" + cellPhoneInformationPhoneNumber 
                +"\",\"jobTitle\":\"" + jobTitle + "\",\"supervisor\":\"" +  supervisor + "\",\"event\":\"" + event + "\",\"eventDate\":\"" + eventDate + "\",\"eventReason\":\"" + eventReason 
                + "\",\"companyCompanyName\":\"" + companyCompanyName + "\",\"divisionDivisionName\":\"" + divisionDivisionName + "\",\"locationLocationName\":\"" + locationLocationName 
                + "\",\"departmentDepartmentName\":\"" + departmentDepartmentName + "\",\"salaryGradeSalaryGradeName\":\"" + salaryGradeSalaryGradeName + "\",\"gender\":\"" + gender 
                + "\",\"bloodGroup\":\"" + bloodGroup + "\",\"dateOfBirth\":\"" + dateOfBirth + "\",\"indiaDrivingLicenseNationalIdInformation\":\"" + indiaDrivingLicenseNationalIdInformation 
                + "\",\"managerUserSysId\":\"" + managerUserSysId + "\",\"employmentDetailsDateOfJoiningTheCurrentCompany\":\"" + employmentDetailsDateOfJoiningTheCurrentCompany + "\",\"companyCompanyId\":\"" + companyCompanyId 
                + "\",\"divisionDivisionCode\":\"" + divisionDivisionCode + "\",\"locationLocationCode\":\"" + locationLocationCode + "\",\"departmentDepartmentCode\":\"" + departmentDepartmentCode 
                + "\",\"indiaPermanentAccountNumberNationalIdInformation\":\"" + indiaPermanentAccountNumberNationalIdInformation + "\",\"maritalStatus\":\"" + maritalStatus 
                + "\",\"indiaGlobalInformationFatherName\":\"" + indiaGlobalInformationFatherName + "\",\"permanentAddress1\":\"" + permanentAddress1 + "\",\"costCenterCostCenterCode\":\"" + costCenterCostCenterCode 
                + "\",\"cellPhoneInformationLastModifiedOn\":\"" + cellPhoneInformationLastModifiedOn + "\",\"biographicalInformationLastModifiedOn\":\"" + biographicalInformationLastModifiedOn + "\",\"businessUnitBusinessUnitCode\":\"" + businessUnitBusinessUnitCode 
                + "\",\"level\":\"" + level + "\",\"costCenter\":\"" + costCenter + "\",\"jobClassificationJobCode\":\"" + jobClassificationJobCode + "\",\"jobClassificationJobTitle\":\"" + jobClassificationJobTitle 
                + "\",\"salaryGradeSalaryGradeId\":\"" + salaryGradeSalaryGradeId + "\",\"salaryGradeSalaryGradeLevel\":\"" + salaryGradeSalaryGradeLevel + "\",\"functionName\":\"" + functionName 
                + "\",\"functionJobFunctionId\":\"" + functionJobFunctionId + "\",\"personalInformationLastModifiedOn\":\"" + personalInformationLastModifiedOn + "\",\"lastModifiedOn\":\"" + lastModifiedOn 
                + "\",\"employmentDetailsDateOfJoiningGilac\":\"" + employmentDetailsDateOfJoiningGilac + "\",\"empJobInfoTCustomVchar13\":\"" + empJobInfoTCustomVchar13 + "\",\"empJobInfoTCustomVchar14\":\"" + empJobInfoTCustomVchar14 
                + "\",\"empJobInfoTCustomVchar15\":\"" + empJobInfoTCustomVchar15 + "\",\"subBusinessUnitId\":\"" + subBusinessUnitId + "\",\"subBusinessUnitName\":\"" + subBusinessUnitName + "\",\"separationDate\":\"" + separationDate 
                + "\",\"add1\":\"" + add1 
                + "\",\"add2\":\"" + add2 
                + "\",\"add3\":\"" + add3 
                + "\",\"add4\":\"" + add4 
                + "\",\"pinCode\":\"" + pinCode+ "\"}";
    }

}
