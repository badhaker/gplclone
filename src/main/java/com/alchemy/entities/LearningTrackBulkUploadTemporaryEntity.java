package com.alchemy.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "learningTrack_BulkUpload_TemporaryEntity")
public class LearningTrackBulkUploadTemporaryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "department_name")
	private String departmentName;

	@Column(name = "bulk_upload_id")
	private Long bulkId;

	@Column(name = "flag")
	private Boolean flag = false;

	@Column(name = "track_name")
	private String trackName;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "enroll_close_date")
	private Date enrollCloseDate;

	@Lob
	@Column(name = "objective", columnDefinition = "TEXT")
	private String objective;

	@Column(name = "enroll_start_date")
	private Date enrollStartDate;

	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "sub_track1")
	private String subTrack1;

	@Column(name = "sub_track2")
	private String subTrack2;

	@Column(name = "sub_track3")
	private String subTrack3;

	@Column(name = "sub_track4")
	private String subTrack4;

	@Column(name = "sub_track5")
	private String subTrack5;

	@Column(name = "sub_track6")
	private String subTrack6;

	@Column(name = "trainer_name1")
	private String trainerName1;

	@Column(name = "trainer_name2")
	private String trainerName2;

	@Column(name = "trainer_name3")
	private String trainerName3;

	@Column(name = "trainer_name4")
	private String trainerName4;

	@Column(name = "trainer_name5")
	private String trainerName5;

	@Column(name = "trainer_name6")
	private String trainerName6;

	@Column(name = "trainerDescription1", columnDefinition = "TEXT")
	private String trainerDescription1;

	@Column(name = "trainerDescription2", columnDefinition = "TEXT")
	private String trainerDescription2;

	@Column(name = "trainerDescription3", columnDefinition = "TEXT")
	private String trainerDescription3;

	@Column(name = "trainerDescription4", columnDefinition = "TEXT")
	private String trainerDescription4;

	@Column(name = "trainerDescription5", columnDefinition = "TEXT")
	private String trainerDescription5;

	@Column(name = "trainerDescription6", columnDefinition = "TEXT")
	private String trainerDescription6;

	private String sponsor1Names;
	
	@Column(name = "sponsor1Introduction", columnDefinition = "TEXT")
	private String sponsor1Introduction;
	private String sponsor1Designation;

	private String sponsor2Names;
	
	@Column(name = "sponsor2Introduction", columnDefinition = "TEXT")
	private String sponsor2Introduction;
	
	
	private String sponsor2Designation;

	private String trainer1Designation;

	private String trainer2Designation;

	private String trainer3Designation;

	private String trainer4Designation;

	private String trainer5Designation;

	private String Trainer6Designation;

	private String level;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public LearningTrackBulkUploadTemporaryEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public Date getEnrollStartDate() {
		return enrollStartDate;
	}

	public void setEnrollStartDate(Date enrollStartDate) {
		this.enrollStartDate = enrollStartDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubTrack1() {
		return subTrack1;
	}

	public void setSubTrack1(String subTrack1) {
		this.subTrack1 = subTrack1;
	}

	public String getSubTrack2() {
		return subTrack2;
	}

	public void setSubTrack2(String subTrack2) {
		this.subTrack2 = subTrack2;
	}

	public String getSubTrack3() {
		return subTrack3;
	}

	public void setSubTrack3(String subTrack3) {
		this.subTrack3 = subTrack3;
	}

	public String getSubTrack4() {
		return subTrack4;
	}

	public void setSubTrack4(String subTrack4) {
		this.subTrack4 = subTrack4;
	}

	public String getSubTrack5() {
		return subTrack5;
	}

	public void setSubTrack5(String subTrack5) {
		this.subTrack5 = subTrack5;
	}

	public String getSubTrack6() {
		return subTrack6;
	}

	public void setSubTrack6(String subTrack6) {
		this.subTrack6 = subTrack6;
	}

	public String getTrainerName1() {
		return trainerName1;
	}

	public void setTrainerName1(String trainerName1) {
		this.trainerName1 = trainerName1;
	}

	public String getTrainerName2() {
		return trainerName2;
	}

	public void setTrainerName2(String trainerName2) {
		this.trainerName2 = trainerName2;
	}

	public String getTrainerName3() {
		return trainerName3;
	}

	public void setTrainerName3(String trainerName3) {
		this.trainerName3 = trainerName3;
	}

	public String getTrainerName4() {
		return trainerName4;
	}

	public void setTrainerName4(String trainerName4) {
		this.trainerName4 = trainerName4;
	}

	public String getTrainerName5() {
		return trainerName5;
	}

	public void setTrainerName5(String trainerName5) {
		this.trainerName5 = trainerName5;
	}

	public String getTrainerName6() {
		return trainerName6;
	}

	public void setTrainerName6(String trainerName6) {
		this.trainerName6 = trainerName6;
	}

	public String getTainerDescription1() {
		return trainerDescription1;
	}

	public void setTainerDescription1(String trainerDescription1) {
		this.trainerDescription1 = trainerDescription1;
	}

	public String getTainerDescription2() {
		return trainerDescription2;
	}

	public void setTainerDescription2(String trainerDescription2) {
		this.trainerDescription2 = trainerDescription2;
	}

	public String getTainerDescription3() {
		return trainerDescription3;
	}

	public void setTainerDescription3(String trainerDescription3) {
		this.trainerDescription3 = trainerDescription3;
	}

	public String getTainerDescription4() {
		return trainerDescription4;
	}

	public void setTainerDescription4(String trainerDescription4) {
		this.trainerDescription4 = trainerDescription4;
	}

	public String getTainerDescription5() {
		return trainerDescription5;
	}

	public void setTainerDescription5(String trainerDescription5) {
		this.trainerDescription5 = trainerDescription5;
	}

	public String getTainerDescription6() {
		return trainerDescription6;
	}

	public void setTainerDescription6(String trainerDescription6) {
		this.trainerDescription6 = trainerDescription6;
	}

	public List<?> getSubTrackList() {
		List<String> subTrackList = new ArrayList<>();

		subTrackList.add(this.getSubTrack1());
		subTrackList.add(this.getSubTrack2());
		subTrackList.add(this.getSubTrack3());
		subTrackList.add(this.getSubTrack4());
		subTrackList.add(this.getSubTrack5());
		subTrackList.add(this.getSubTrack6());

		return subTrackList;
	}

	public List<?> getTrainerList() {
		List<String> list = new ArrayList<>();

		list.add(this.getTrainerName1());
		list.add(this.getTainerDescription1());
		list.add(this.getTrainer1Designation());

		list.add(this.getTrainerName2());
		list.add(this.getTainerDescription2());
		list.add(this.getTrainer2Designation());

		list.add(this.getTrainerName3());
		list.add(this.getTainerDescription3());
		list.add(this.getTrainer3Designation());

		list.add(this.getTrainerName4());
		list.add(this.getTainerDescription4());
		list.add(this.getTrainer4Designation());

		list.add(this.getTrainerName5());
		list.add(this.getTainerDescription5());
		list.add(this.getTrainer5Designation());

		list.add(this.getTrainerName6());
		list.add(this.getTainerDescription6());
		list.add(this.getTrainer6Designation());

		System.err.println(list);
		return list;
	}

	public Long getBulkId() {
		return bulkId;
	}

	public void setBulkId(Long bulkId) {
		this.bulkId = bulkId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getTrainerDescription1() {
		return trainerDescription1;
	}

	public void setTrainerDescription1(String trainerDescription1) {
		this.trainerDescription1 = trainerDescription1;
	}

	public String getTrainerDescription2() {
		return trainerDescription2;
	}

	public void setTrainerDescription2(String trainerDescription2) {
		this.trainerDescription2 = trainerDescription2;
	}

	public String getTrainerDescription3() {
		return trainerDescription3;
	}

	public void setTrainerDescription3(String trainerDescription3) {
		this.trainerDescription3 = trainerDescription3;
	}

	public String getTrainerDescription4() {
		return trainerDescription4;
	}

	public void setTrainerDescription4(String trainerDescription4) {
		this.trainerDescription4 = trainerDescription4;
	}

	public String getTrainerDescription5() {
		return trainerDescription5;
	}

	public void setTrainerDescription5(String trainerDescription5) {
		this.trainerDescription5 = trainerDescription5;
	}

	public String getTrainerDescription6() {
		return trainerDescription6;
	}

	public void setTrainerDescription6(String trainerDescription6) {
		this.trainerDescription6 = trainerDescription6;
	}

	public String getSponsor1Names() {
		return sponsor1Names;
	}

	public void setSponsor1Names(String sponsor1Names) {
		this.sponsor1Names = sponsor1Names;
	}

	public String getSponsor1Introduction() {
		return sponsor1Introduction;
	}

	public void setSponsor1Introduction(String sponsor1Introduction) {
		this.sponsor1Introduction = sponsor1Introduction;
	}

	public String getSponsor1Designation() {
		return sponsor1Designation;
	}

	public void setSponsor1Designation(String sponsor1Designation) {
		this.sponsor1Designation = sponsor1Designation;
	}

	public String getSponsor2Names() {
		return sponsor2Names;
	}

	public void setSponsor2Names(String sponsor2Names) {
		this.sponsor2Names = sponsor2Names;
	}

	public String getSponsor2Introduction() {
		return sponsor2Introduction;
	}

	public void setSponsor2Introduction(String sponsor2Introduction) {
		this.sponsor2Introduction = sponsor2Introduction;
	}

	public String getSponsor2Designation() {
		return sponsor2Designation;
	}

	public void setSponsor2Designation(String sponsor2Designation) {
		this.sponsor2Designation = sponsor2Designation;
	}

	public String getTrainer1Designation() {
		return trainer1Designation;
	}

	public void setTrainer1Designation(String trainer1Designation) {
		this.trainer1Designation = trainer1Designation;
	}

	public String getTrainer2Designation() {
		return trainer2Designation;
	}

	public void setTrainer2Designation(String trainer2Designation) {
		this.trainer2Designation = trainer2Designation;
	}

	public String getTrainer3Designation() {
		return trainer3Designation;
	}

	public void setTrainer3Designation(String trainer3Designation) {
		this.trainer3Designation = trainer3Designation;
	}

	public String getTrainer4Designation() {
		return trainer4Designation;
	}

	public void setTrainer4Designation(String trainer4Designation) {
		this.trainer4Designation = trainer4Designation;
	}

	public String getTrainer5Designation() {
		return trainer5Designation;
	}

	public void setTrainer5Designation(String trainer5Designation) {
		this.trainer5Designation = trainer5Designation;
	}

	public String getTrainer6Designation() {
		return Trainer6Designation;
	}

	public void setTrainer6Designation(String trainer6Designation) {
		Trainer6Designation = trainer6Designation;
	}

	public Date getEnrollCloseDate() {
		return enrollCloseDate;
	}

	public void setEnrollCloseDate(Date enrollCloseDate) {
		this.enrollCloseDate = enrollCloseDate;
	}

}
