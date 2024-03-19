package com.alchemy.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.alchemy.dto.CertificateRequestDto;
import com.alchemy.dto.CertificateResponseDto;
import com.alchemy.dto.UserCertificateRequestDto;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.MailTemplate;
import com.alchemy.entities.SubTrackEntity;
import com.alchemy.entities.UserCertificateEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.entities.UserTrackEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListUserCertificate;
import com.alchemy.repositories.CertificateRepository;
import com.alchemy.repositories.FileUploadRepository;
import com.alchemy.repositories.LearningTrackRepository;
import com.alchemy.repositories.MailRepository;
import com.alchemy.repositories.SubTrackRepository;
import com.alchemy.repositories.UserCertificateRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.repositories.UserTrackRepository;
import com.alchemy.serviceInterface.EmailInterface;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.serviceInterface.UserCertificateInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.Pagination;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;

@Service
public class UserCertificateServiceImpl implements UserCertificateInterface{

	@Value("${file.upload-dir}")
	private String myProperty;
	
	@Autowired
	private UserCertificateRepository userCertificateRepository;
	
	@Autowired
	private SubTrackRepository subTrackRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailInterface emailInterface;
	
	@Autowired
	private MailRepository mailRepository;
	
	@Autowired
	private FileUploadInterface fileuploInterface;	
	@Autowired
	private UserTrackRepository userTrackRepository;
	
	@Autowired 
	private LearningTrackRepository learningTrackRepository;
	
	@Autowired
	private FileUploadRepository fileUploadRepository;
	
	@Autowired
	private SpringTemplateEngine engine;
	
	@Autowired
	private EmailServiceImpl impl;
	
	@Override
	public void add(UserCertificateRequestDto certificateRequest) {
		
		ArrayList<UserCertificateEntity> userCertificate =  new ArrayList<>();
		UserEntity user = this.userRepository.findById(certificateRequest.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USER_NOT_PRESENT));
		
		for (int i = 0; i < certificateRequest.getCertificateId().size(); i++) {
			FileUploadEntity certificateMaster = this.fileUploadRepository.findById(certificateRequest.getCertificateId().get(i))
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TEMPLATE_NOT_FOUND));
			
			UserCertificateEntity certificateEntity = userCertificateRepository.findByUserIdAndCertificateId(user,certificateMaster);
			if(certificateEntity != null) {
				throw new ResourceNotFoundException(ErrorMessageCode.USER_CERTIFICATE_ALREADY_PRESENT);
			}

			
		UserCertificateEntity userCertificateEntity = new UserCertificateEntity();
		userCertificateEntity.setUserId(user);
		userCertificateEntity.setCertificateId(certificateMaster);
		userCertificate.add(userCertificateEntity);
		}
		
		userCertificateRepository.saveAll(userCertificate);
			
	}

	@Override
	public Page<IListUserCertificate> getAllUserCertificates(String search, String pageNo, String pageSize) throws Exception{
		Page<IListUserCertificate> iListUserCertificate;

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
		
		if (search == "" || search == null || search.length() == 0) {

			iListUserCertificate = this.userCertificateRepository.findByOrderByIdDesc(pageable, IListUserCertificate.class);
		} else {

			iListUserCertificate = this.userCertificateRepository.findByUserCertificate(search, pageable, IListUserCertificate.class);
		}
		return iListUserCertificate;
		
	}
	
	 public static String convertToTitleCase(String input) {
	        if (input == null || input.isEmpty()) {
	            return input;
	        }

	        StringBuilder titleCase = new StringBuilder(input.length());
	        boolean nextTitleCase = true;

	        for (char c : input.toCharArray()) {
	            if (Character.isSpaceChar(c)) {
	                nextTitleCase = true;
	            } else if (nextTitleCase) {
	                c = Character.toTitleCase(c);
	                nextTitleCase = false;
	            } else {
	                c = Character.toLowerCase(c);
	            }
	            titleCase.append(c);
	        }

	        return titleCase.toString();
	    }
	    
	 private static String convertDateToString(String dateString) {
	        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");	        
	        LocalDate date = LocalDate.parse(dateString, inputFormatter);	        
	        String formattedDate = date.getDayOfMonth() + getDayOfMonthSuffix(date.getDayOfMonth())
	                + " " + date.getMonth().getDisplayName(TextStyle.FULL, Locale.US)
	                + " " + date.getYear();
	        
	        System.err.println(formattedDate);
			return formattedDate;
	    }
	    
	    private static String getDayOfMonthSuffix(int day) {
	        if (day >= 11 && day <= 13) {
	            return "th";
	        }
	        switch (day % 10) {
	            case 1:  return "st";
	            case 2:  return "nd";
	            case 3:  return "rd";
	            default: return "th";
	        }
	    }
	@Override
	public CertificateResponseDto getUserCertificate(Long learningTrackId,Long subTrackId, Long userId,HttpServletRequest request )
			throws Exception{
		CertificateResponseDto certificateResponseDto = new CertificateResponseDto();
		LearningTrackEntity track = this.learningTrackRepository.findById(learningTrackId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND)); 
		UserEntity user = this.userRepository.findById(userId)
		        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.CERTIFICATE_USER_NOT_FOUND)); 
		
		UserTrackEntity usertrack = null;
		if (subTrackId == null) {
			usertrack = this.userTrackRepository.findByUserEntityIdAndTrackId(track, user);
		} else  {
			SubTrackEntity subTrack = this.subTrackRepository.findById(subTrackId)
			        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NOT_PRESENT)); 
			usertrack = this.userTrackRepository.findByTrackIdAndUserEntityAndSubtrackId(track, user, subTrack);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		String convertedDate = convertDateToString(dateFormat.format(usertrack.getCompleteDate()));
		String nameTitle = convertToTitleCase(usertrack.getUserEntity().getName());
		
		if(usertrack != null) {
		if (usertrack.getCompleteDate()!=null) {
		UserCertificateEntity userCertificateEntity = new UserCertificateEntity();
		String fileUrl=null;
		String imageUrl=null;
					if(subTrackId != null) {
						if(usertrack.getFile_id()==null) {
						Context context=new Context();
						context.setVariable("username", nameTitle);
						context.setVariable("Probability",usertrack.getSubtrackId().getName() );
						context.setVariable("LearningTrack",usertrack.getTrackId().getName() );
						context.setVariable("date",convertedDate);
						String	finalhtml=engine.process("LearningTrack", context);
					
						MultipartFile pdf = emailInterface.htmlToPdf(finalhtml,usertrack.getUserEntity().getId().toString()+usertrack.getSubtrackId().getId().toString());
						FileUploadEntity fileUploadEntity = fileuploInterface.storePdf(pdf,usertrack.getUserEntity().getId().toString()+usertrack.getSubtrackId().getId().toString());
						fileUrl = GlobalFunctions.getFileUrl(fileUploadEntity.getOriginalName());
						String url=fileUrl+".pdf";
						usertrack.setFile_id(fileUploadEntity);
						certificateResponseDto.setPdfUrl(url);
						
						MultipartFile image = emailInterface.generateImageFromPDFf(fileUploadEntity.getOriginalName(), 
								usertrack.getFile_id().getOriginalName()+"-image", 
								".png");
						FileUploadEntity imageUploadEntity = fileuploInterface.storeImage(image,usertrack.getFile_id().getOriginalName()+"-image");
						imageUrl = GlobalFunctions.getFileUrl(imageUploadEntity.getOriginalName());
						String url1=imageUrl+".png";
						certificateResponseDto.setImageUrl(url1);
						
						
						usertrack.setImageId(imageUploadEntity);
						userCertificateEntity.setCertificateId(fileUploadEntity);
						userCertificateEntity.setUserId(user);
						this.userCertificateRepository.save(userCertificateEntity);
						fileUploadEntity.setId(userCertificateEntity.getId());
						return certificateResponseDto;
						}
						else
							fileUrl = GlobalFunctions.getFileUrl(usertrack.getFile_id().getOriginalName());
						    String url=fileUrl+".pdf";
						    certificateResponseDto.setPdfUrl(url);
						    imageUrl=GlobalFunctions.getFileUrl(usertrack.getImageId().getOriginalName());
						    String url1=imageUrl+".png";
						    certificateResponseDto.setImageUrl(url1);
						    return certificateResponseDto;
				        }
					else if(subTrackId == null) {   	
						if(usertrack.getFile_id()==null) {
							Context context=new Context();
							context.setVariable("username", nameTitle);
							context.setVariable("Probability",track.getName() );
							context.setVariable("date", convertedDate);
							
						String	finalhtml=engine.process("SubTrack", context);
							
					MultipartFile pdf = emailInterface.htmlToPdf(finalhtml,usertrack.getUserEntity().getId().toString()+usertrack.getTrackId().getId().toString());
					FileUploadEntity fileUploadEntity = fileuploInterface.storePdf(pdf,usertrack.getUserEntity().getId().toString()+usertrack.getTrackId().getId().toString());
					fileUrl = GlobalFunctions.getFileUrl(fileUploadEntity.getOriginalName());
					String url=fileUrl+".pdf";
					usertrack.setFile_id(fileUploadEntity);
					certificateResponseDto.setPdfUrl(url);
					
					MultipartFile image = emailInterface.generateImageFromPDFf(fileUploadEntity.getOriginalName(), 
							usertrack.getFile_id().getOriginalName()+"-image", 
							".png");
					FileUploadEntity imageUploadEntity = fileuploInterface.storeImage(image,usertrack.getFile_id().getOriginalName()+"-image");
					imageUrl = GlobalFunctions.getFileUrl(imageUploadEntity.getOriginalName());
					String url1=imageUrl+".png";
					certificateResponseDto.setImageUrl(url1);
					
					usertrack.setImageId(imageUploadEntity);
					userCertificateEntity.setCertificateId(fileUploadEntity);
					userCertificateEntity.setUserId(user);
					this.userCertificateRepository.save(userCertificateEntity);
					fileUploadEntity.setId(userCertificateEntity.getId());
					return certificateResponseDto;
					}
					else
						fileUrl = GlobalFunctions.getFileUrl(usertrack.getFile_id().getOriginalName());
					    String url=fileUrl+".pdf";
					    certificateResponseDto.setPdfUrl(url);
					    imageUrl=GlobalFunctions.getFileUrl(usertrack.getImageId().getOriginalName());
					    String url1=imageUrl+".png";
					    certificateResponseDto.setImageUrl(url1);
					    return certificateResponseDto;
			        }
		}else throw new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_INCOMPLETE); 
		}else throw new ResourceNotFoundException(ErrorMessageCode.USERTRACK_NOT_FOUND);
		return  null; 
				
		}
}
