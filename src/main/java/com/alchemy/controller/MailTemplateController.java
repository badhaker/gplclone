package com.alchemy.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.MailTemplateDto;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.entities.MailTemplate;
import com.alchemy.repositories.MailRepository;
import com.alchemy.serviceInterface.MailTemplateInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.SuccessMessageCode;

@RestController
@RequestMapping(ApiUrls.MAILTEMPLATE)
public class MailTemplateController {

	@Autowired
	MailRepository mailRepository;
	
	@Autowired
	MailTemplateInterface templateInterface;

	@PostConstruct
	private void addTemplates() {

		MailTemplate otpTemplate = mailRepository.findBytemplatename(Constant.OTP_TEMPLATE_NAME);
		MailTemplate dashboardTemplate = mailRepository.findBytemplatename(Constant.ONBOARD_TEMPLATE_NAME);
		MailTemplate nominationReceived = mailRepository.findBytemplatename(Constant.NOMINATION_RECIEVEVD);
		MailTemplate nominationAccepted = mailRepository.findBytemplatename(Constant.NOMINATION_ACCEPTED);
		MailTemplate nominationRejected = mailRepository.findBytemplatename(Constant.NOMINATION_REJECTED);
		MailTemplate nominationOnHold = mailRepository.findBytemplatename(Constant.NOMINATION_ONHOLD);

		
		if (nominationOnHold == null) {
			MailTemplate mailTemplate = new MailTemplate();
			mailTemplate.setMailtemp(
					"<!DOCTYPE html>\r\n"
					+ "<html lang=\"en\">\r\n"
					+ "    <head>\r\n"
					+ "        <meta charset=\"UTF-8\" />\r\n"
					+ "        <meta http-equiv=\"Content-Type\" content=\"text/html charset=UTF-8\" />\r\n"
					+ "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
					+ "        <!-- Added viewport meta tag for mobile responsiveness -->\r\n"
					+ "        <title>Email Template</title>\r\n"
					+ "    </head>\r\n"
					+ "    <body style=\"\r\n"
					+ "        margin: 0;\r\n"
					+ "        background-color: #ffffff;\r\n"
					+ "        font-family: Arial, sans-serif;\r\n"
					+ "        color: #333333;\r\n"
					+ "        \">\r\n"
					+ "        <div align=\"center\">\r\n"
					+ "            <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"74%\" style=\"width:74.98%;border-collapse:collapse\">\r\n"
					+ "                <tbody>\r\n"
					+ "                    <tr style=\"height:8.5pt\">\r\n"
					+ "                        <td width=\"100%\" style=\"width:100.0%;border:solid windowtext 1.0pt;border-bottom:none;padding:15.0pt 22.5pt 0cm 22.5pt;height:8.5pt\">\r\n"
					+ "                            <p style=\"margin:0cm;margin-bottom:.0001pt\"><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">Dear\r\n"
					+ "                                </span></b><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;\"> < Employee Name > </Employee></span></b><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">,\r\n"
					+ "                                </span></b><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif\"><u></u><u></u></span></b>\r\n"
					+ "                            </p>\r\n"
					+ "                            \r\n"
					+ "                            <p style=\"margin: 0cm; margin-bottom: 24pt;\"><span><b></b></span></p>\r\n"
					+ "                            <p style=\"margin:0cm;margin-bottom:.0001pt\"><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">Thank you for your interest in <b>  < Program Name > </b>. We are keeping your nomination on hold on account of large volume of nominations received for the track.<u></u><u></u></span></p>\r\n"
					+ "                            <p style=\"margin: 0cm; margin-bottom: 16pt;\"><span><b></b></span></p>\r\n"
					+ "                            <p class=\"MsoNormal\"><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">You will soon receive an update on the final status of your application.<u></u><u></u></span></p>\r\n"
					+ "                            <p style=\"margin: 0cm; margin-bottom: 24pt;\"><span><b></b></span></p>\r\n"
					+ "                            <p style=\"line-height:8pt\"><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">Regards,</span></b><b><span style=\"font-family:Calibri Light,sans-serif\"><u></u><u></u></span></b></p>\r\n"
					+ "                            <p style=\"line-height:8pt\"><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">Team GPL Alchemy</span></b><b><span style=\"font-family:Calibri Light,sans-serif;color:#1f497d\"><u></u><u></u></span></b></p>\r\n"
					+ "                            <p style=\"line-height:8pt\"><u></u><img  style=\"height: 80px;width: 140px; margin: 0;\" src=\"https://alchemy.godrejproperties.com/api/downloadFile/831f9e45-4fee-4679-9911-ea66e01f8219_newAlchemyLogo.png\" align=\"left\" hspace=\"12\" data-image-whitelisted=\"\" class=\"CToWUd\" data-bit=\"iit\"><u></u><b><span style=\"font-size:11.0pt;font-family:Calibri,sans-serif;color:#1f497d\"><u></u><u></u></span></b></p>\r\n"
					+ "                        </td>\r\n"
					+ "                    </tr>\r\n"
					+ "                    <tr style=\"height:31.2pt\">\r\n"
					+ "                        <td width=\"100%\" style=\"width:100.0%;border-top:none;border-left:solid windowtext 1.0pt;border-bottom:none;border-right:solid windowtext 1.0pt;padding:15.0pt 22.5pt 0cm 22.5pt\">\r\n"
					+ "                            <div style=\"border:solid #d2d2d2 1.0pt;padding:8.0pt 8.0pt 8.0pt 8.0pt\">\r\n"
					+ "                                <p style=\"line-height:15.0pt\"><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:black\">This is an electronically generated email. Please do not reply to this message. In case of any queries,</span></i><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:#1f497d\">\r\n"
					+ "                                    </span></i><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:black\">please write to\r\n"
					+ "                                    </span></i><a href=\"mailto:GPL.Alchemy@godrejproperties.com\" target=\"_blank\"><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif\">gpl.alchemy@godrejproperties.<wbr>com</span></i></a><i><span style=\"font-family:Calibri Light,sans-serif;color:black\"><u></u><u></u></span></i>\r\n"
					+ "                                </p>\r\n"
					+ "                            </div>\r\n"
					+ "                        </td>\r\n"
					+ "                    </tr>\r\n"
					+ "                    <tr style=\"height:14.15pt\">\r\n"
					+ "                        <td width=\"100%\" style=\"width:100.0%;border:solid windowtext 1.0pt;border-top:none;padding:15.0pt 22.5pt 0cm 22.5pt\">\r\n"
					+ "                            <p style=\"margin-right:8.25pt;margin-bottom:12.0pt;margin-left:8.25pt;line-height:15.0pt\">\r\n"
					+ "                            </p>\r\n"
					+ "                        </td>\r\n"
					+ "                    </tr>\r\n"
					+ "                </tbody>\r\n"
					+ "            </table>\r\n"
					+ "        </div>\r\n"
					+ "       </body>\r\n"
					+ "</html>");
			mailTemplate.setTemplatename("NominationOnHold");
			mailRepository.save(mailTemplate);
		}


		if (nominationRejected == null) {
			MailTemplate mailTemplate = new MailTemplate();
			mailTemplate.setMailtemp(
					"<!DOCTYPE html>\r\n"
					+ "<html lang=\"en\">\r\n"
					+ "    <head>\r\n"
					+ "        <meta charset=\"UTF-8\" />\r\n"
					+ "        <meta http-equiv=\"Content-Type\" content=\"text/html charset=UTF-8\" />\r\n"
					+ "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
					+ "        <!-- Added viewport meta tag for mobile responsiveness -->\r\n"
					+ "        <title>Email Template</title>\r\n"
					+ "    </head>\r\n"
					+ "    <body style=\"\r\n"
					+ "        margin: 0;\r\n"
					+ "        background-color: #ffffff;\r\n"
					+ "        font-family: Arial, sans-serif;\r\n"
					+ "        color: #333333;\r\n"
					+ "        \">\r\n"
					+ "        <div align=\"center\">\r\n"
					+ "            <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"74%\" style=\"width:74.98%;border-collapse:collapse\">\r\n"
					+ "                <tbody>\r\n"
					+ "                    <tr style=\"height:8.5pt\">\r\n"
					+ "                        <td width=\"100%\" style=\"width:100.0%;border:solid windowtext 1.0pt;border-bottom:none;padding:15.0pt 22.5pt 0cm 22.5pt;height:8.5pt\">\r\n"
					+ "                            <p style=\"margin:0cm;margin-bottom:.0001pt\"><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">Dear\r\n"
					+ "                                </span></b><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;\"> < Employee Name > </Employee></span></b><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">,\r\n"
					+ "                                </span></b><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif\"><u></u><u></u></span></b>\r\n"
					+ "                            </p>\r\n"
					+ "                            \r\n"
					+ "                            <p style=\"margin: 0cm; margin-bottom: 24pt;\"><span><b></b></span></p>\r\n"
					+ "                            <p style=\"margin:0cm;margin-bottom:.0001pt\"><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">We regret to inform you that we would not be going ahead with your nomination for <b>  < Program Name > </b>.<u></u><u></u></span></p>\r\n"
					+ "                            <p style=\"margin: 0cm; margin-bottom: 16pt;\"><span><b></b></span></p>\r\n"
					+ "                            <p class=\"MsoNormal\"><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">Thank you for your interest. We look forward to your participation in the upcoming tracks.<u></u><u></u></span></p>\r\n"
					+ "                            <p style=\"margin: 0cm; margin-bottom: 24pt;\"><span><b></b></span></p>\r\n"
					+ "                            <p style=\"line-height:8pt\"><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">Regards,</span></b><b><span style=\"font-family:Calibri Light,sans-serif\"><u></u><u></u></span></b></p>\r\n"
					+ "                            <p style=\"line-height:8pt\"><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">Team GPL Alchemy</span></b><b><span style=\"font-family:Calibri Light,sans-serif;color:#1f497d\"><u></u><u></u></span></b></p>\r\n"
					+ "                            <p style=\"line-height:8pt\"><u></u><img  style=\"height: 80px;width: 140px; margin: 0;\" src=\"https://alchemy.godrejproperties.com/api/downloadFile/831f9e45-4fee-4679-9911-ea66e01f8219_newAlchemyLogo.png\" align=\"left\" hspace=\"12\" data-image-whitelisted=\"\" class=\"CToWUd\" data-bit=\"iit\"><u></u><b><span style=\"font-size:11.0pt;font-family:Calibri,sans-serif;color:#1f497d\"><u></u><u></u></span></b></p>\r\n"
					+ "                        </td>\r\n"
					+ "                    </tr>\r\n"
					+ "                    <tr style=\"height:31.2pt\">\r\n"
					+ "                        <td width=\"100%\" style=\"width:100.0%;border-top:none;border-left:solid windowtext 1.0pt;border-bottom:none;border-right:solid windowtext 1.0pt;padding:15.0pt 22.5pt 0cm 22.5pt\">\r\n"
					+ "                            <div style=\"border:solid #d2d2d2 1.0pt;padding:8.0pt 8.0pt 8.0pt 8.0pt\">\r\n"
					+ "                                <p style=\"line-height:15.0pt\"><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:black\">This is an electronically generated email. Please do not reply to this message. In case of any queries,</span></i><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:#1f497d\">\r\n"
					+ "                                    </span></i><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:black\">please write to\r\n"
					+ "                                    </span></i><a href=\"mailto:GPL.Alchemy@godrejproperties.com\" target=\"_blank\"><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif\">gpl.alchemy@godrejproperties.<wbr>com</span></i></a><i><span style=\"font-family:Calibri Light,sans-serif;color:black\"><u></u><u></u></span></i>\r\n"
					+ "                                </p>\r\n"
					+ "                            </div>\r\n"
					+ "                        </td>\r\n"
					+ "                    </tr>\r\n"
					+ "                    <tr style=\"height:14.15pt\">\r\n"
					+ "                        <td width=\"100%\" style=\"width:100.0%;border:solid windowtext 1.0pt;border-top:none;padding:15.0pt 22.5pt 0cm 22.5pt\">\r\n"
					+ "                            <p style=\"margin-right:8.25pt;margin-bottom:12.0pt;margin-left:8.25pt;line-height:15.0pt\">\r\n"
					+ "                            </p>\r\n"
					+ "                        </td>\r\n"
					+ "                    </tr>\r\n"
					+ "                </tbody>\r\n"
					+ "            </table>\r\n"
					+ "        </div>\r\n"
					+ "    </body>\r\n"
					+ "</html>");
			mailTemplate.setTemplatename("NominationRejection");
			mailRepository.save(mailTemplate);
		}
		
		
		
		if (otpTemplate == null) {
			MailTemplate mailTemplate = new MailTemplate();
			mailTemplate.setMailtemp(
					"<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">\r\n"
							+ "  <div style=\"margin:50px auto;width:70%;padding:20px 0\">\r\n"
							+ "    <div style=\"border-bottom:1px solid #eee\">\r\n"
							+ "      <a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\"></a>\r\n"
							+ "    </div>\r\n" + "    <p style=\"font-size:1.1em\">Hi,</p>\r\n"
							+ "    <p>Thank you for choosing GPL ALCHEMY. Use the following OTP to complete your Sign Up procedures. OTP is valid for 5 Minutes</p>\r\n"
							+ "    <h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">otp-alchemy</h2>\r\n"
							+ "    <p style=\"font-size:0.9em;\">This is an auto-generated email. Please do not reply.</p>\r\n"
							+ "    <hr style=\"border:none;border-top:1px solid #eee\" />\r\n" + "\r\n" + "  </div>");
			mailTemplate.setTemplatename("otpTemplate");
			mailRepository.save(mailTemplate);
		}

	if (dashboardTemplate == null) {

		MailTemplate onboardTemplate = new MailTemplate();
		onboardTemplate.setMailtemp("\r\n" + "\r\n"
				+ "<div style=\"margin: 0; padding: 0; width: 100%; word-break: break-word; -webkit-font-smoothing: antialiased; --bg-opacity: 1; background-color: #eceff1; background-color: rgba(236, 239, 241, var(--bg-opacity));\">\r\n"
				+ "    <div role=\"article\" aria-roledescription=\"email\" aria-label=\"Welcome to PixInvent 👋\" lang=\"en\">\r\n"
				+ "        <table style=\"font-family: Montserrat, -apple-system, 'Segoe UI', sans-serif; width: 100%;\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
				+ "        <tbody><tr>\r\n"
				+ "            <td align=\"center\" style=\"--bg-opacity: 1; background-color: #eceff1; background-color: rgba(236, 239, 241, var(--bg-opacity)); font-family: Montserrat, -apple-system, 'Segoe UI', sans-serif;\" bgcolor=\"rgba(236, 239, 241, var(--bg-opacity))\">\r\n"
				+ "            <table class=\"sm-w-full\" style=\"font-family: 'Montserrat',Arial,sans-serif; width: 600px;\" width=\"600\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
				+ "                <tbody><tr>\r\n"
				+ "                <td class=\"sm-py-32 sm-px-24\" style=\"font-family: Montserrat, -apple-system, 'Segoe UI', sans-serif; padding: 48px; text-align: center;\" align=\"center\">\r\n"
				+ "                </td>\r\n" + "                </tr>\r\n" + "                <tr>\r\n"
				+ "                <td align=\"center\" class=\"sm-px-24\" style=\"font-family: 'Montserrat',Arial,sans-serif;\">\r\n"
				+ "                    <table style=\"font-family: 'Montserrat',Arial,sans-serif; width: 100%;\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
				+ "                    <tbody><tr>\r\n"
				+ "                        <td class=\"sm-px-24\" style=\"--bg-opacity: 1; background-color: #ffffff; background-color: rgba(255, 255, 255, var(--bg-opacity)); border-radius: 4px; font-family: Montserrat, -apple-system, 'Segoe UI', sans-serif; font-size: 14px; line-height: 24px; padding: 48px; text-align: left; --text-opacity: 1; color: #626262; color: rgba(98, 98, 98, var(--text-opacity));\" bgcolor=\"rgba(255, 255, 255, var(--bg-opacity))\" align=\"left\">\r\n"
				+ "                        <span style=\"font-weight: 600; font-size: 18px; margin-bottom: 0;\">Hey</span>\r\n"
				+ "                        <span style=\"font-weight: 700; font-size: 18px; margin-top: 0; --text-opacity: 1; color: #ff5850; color: rgba(255, 88, 80, var(--text-opacity));\">USER_NAME,</span>\r\n"
				+ "                        <p style=\"font-size: 16px; margin: 24px 0;\">\r\n"
				+ "                            We are pleased to welcome you to GPL.\r\n"
				+ "                        </p>\r\n"
				+ "                        <p style=\"font-size: 16px; margin-bottom: 24px;\">Click on the button below for onboarding.</p>\r\n"
				+ "                        <div style=\"margin-bottom: 24px; width: 200px; \">\r\n"
				+ "                            <a href=\"ONBOARDING_URL\" style=\"display: block; border-radius: 4px; background-color:#7367f0; font-weight: 600; font-size: 14px; line-height: 100%; padding: 16px 24px; --text-opacity: 1; color: #ffffff; color: rgba(255, 255, 255, var(--text-opacity)); text-decoration: none;\">Verify Email Now →</a>\r\n"
				+ "                        </div>\r\n"
				+ "                        <p style=\"margin: 0 0 16px;\">Thanks, <br>GPL Team</p>\r\n"
				+ "                        </td>\r\n" + "                    </tr>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td style=\"font-family: 'Montserrat',Arial,sans-serif; height: 20px;\" height=\"20\"></td>\r\n"
				+ "                    </tr>\r\n" + "                    <tr>\r\n"
				+ "                        <td style=\"font-family: Montserrat, -apple-system, 'Segoe UI', sans-serif; font-size: 12px; padding-left: 48px; padding-right: 48px; --text-opacity: 1; color: #eceff1; color: rgba(236, 239, 241, var(--text-opacity));\">\r\n"
				+ "                        \r\n"
				+ "                        <p style=\"--text-opacity: 1; color: #263238; color: rgba(38, 50, 56, var(--text-opacity));\">\r\n"
				+ "                            This is an autogenerated email. Please do not reply\r\n"
				+ "                        </p>\r\n" + "                        </td>\r\n"
				+ "                    </tr>\r\n" + "                    <tr>\r\n"
				+ "                        <td style=\"font-family: 'Montserrat',Arial,sans-serif; height: 16px;\" height=\"16\"></td>\r\n"
				+ "                    </tr>\r\n" + "                    </tbody></table>\r\n"
				+ "                </td>\r\n" + "                </tr>\r\n" + "            </tbody></table>\r\n"
				+ "            </td>\r\n" + "        </tr>\r\n" + "        </tbody></table>\r\n" + "    </div>\r\n"
				+ "    </div>");
		onboardTemplate.setTemplatename("onboardingTemplate");
		mailRepository.save(onboardTemplate);

	}
	
	if (nominationReceived == null) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setMailtemp(
				"<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "    <head>\r\n"
				+ "        <meta charset=\"UTF-8\" />\r\n"
				+ "        <meta http-equiv=\"Content-Type\" content=\"text/html charset=UTF-8\" />\r\n"
				+ "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
				+ "        <!-- Added viewport meta tag for mobile responsiveness -->\r\n"
				+ "        <title>Email Template</title>\r\n"
				+ "    </head>\r\n"
				+ "    <body style=\"\r\n"
				+ "        margin: 0;\r\n"
				+ "        background-color: #ffffff;\r\n"
				+ "        font-family: Arial, sans-serif;\r\n"
				+ "        color: #333333;\r\n"
				+ "        \">\r\n"
				+ "        <div align=\"center\">\r\n"
				+ "            <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"74%\" style=\"width:74.98%;border-collapse:collapse\">\r\n"
				+ "                <tbody>\r\n"
				+ "                    <tr style=\"height:8.5pt\">\r\n"
				+ "                        <td width=\"100%\" style=\"width:100.0%;border:solid windowtext 1.0pt;border-bottom:none;padding:15.0pt 22.5pt 0cm 22.5pt;height:8.5pt\">\r\n"
				+ "                            <p style=\"margin:0cm;margin-bottom:.0001pt\"><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">Dear\r\n"
				+ "                                </span></b><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;\"> < Employee Name > </Employee></span></b><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">,\r\n"
				+ "                                </span></b><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif\"><u></u><u></u></span></b>\r\n"
				+ "                            </p>\r\n"
				+ "                            \r\n"
				+ "                            <p style=\"margin: 0cm; margin-bottom: 24pt;\"><span><b></b></span></p>\r\n"
				+ "                            <p style=\"margin:0cm;margin-bottom:.0001pt\"><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">We have received your nomination for <b>  < Program Name > </b>.<u></u><u></u></span></p>\r\n"
				+ "                            <p style=\"margin: 0cm; margin-bottom: 16pt;\"><span><b></b></span></p>\r\n"
				+ "                            <p class=\"MsoNormal\"><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">You will soon hear from us on the next steps.<u></u><u></u></span></p>\r\n"
				+ "                            <p style=\"margin: 0cm; margin-bottom: 24pt;\"><span><b></b></span></p>\r\n"
				+ "                            <p style=\"line-height:8pt\"><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">Regards,</span></b><b><span style=\"font-family:Calibri Light,sans-serif\"><u></u><u></u></span></b></p>\r\n"
				+ "                            <p style=\"line-height:8pt\"><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">Team GPL Alchemy</span></b><b><span style=\"font-family:Calibri Light,sans-serif;color:#1f497d\"><u></u><u></u></span></b></p>\r\n"
				+ "                            <p style=\"line-height:8pt\"><u></u><img  style=\"height: 80px;width: 140px; margin: 0;\" src=\"https://alchemy.godrejproperties.com/api/downloadFile/831f9e45-4fee-4679-9911-ea66e01f8219_newAlchemyLogo.png\" align=\"left\" hspace=\"12\" data-image-whitelisted=\"\" class=\"CToWUd\" data-bit=\"iit\"><u></u><b><span style=\"font-size:11.0pt;font-family:Calibri,sans-serif;color:#1f497d\"><u></u><u></u></span></b></p>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                    <tr style=\"height:31.2pt\">\r\n"
				+ "                        <td width=\"100%\" style=\"width:100.0%;border-top:none;border-left:solid windowtext 1.0pt;border-bottom:none;border-right:solid windowtext 1.0pt;padding:15.0pt 22.5pt 0cm 22.5pt\">\r\n"
				+ "                            <div style=\"border:solid #d2d2d2 1.0pt;padding:8.0pt 8.0pt 8.0pt 8.0pt\">\r\n"
				+ "                                <p style=\"line-height:15.0pt\"><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:black\">This is an electronically generated email. Please do not reply to this message. In case of any queries,</span></i><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:#1f497d\">\r\n"
				+ "                                    </span></i><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:black\">please write to\r\n"
				+ "                                    </span></i><a href=\"mailto:GPL.Alchemy@godrejproperties.com\" target=\"_blank\"><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif\">gpl.alchemy@godrejproperties.<wbr>com</span></i></a><i><span style=\"font-family:Calibri Light,sans-serif;color:black\"><u></u><u></u></span></i>\r\n"
				+ "                                </p>\r\n"
				+ "                            </div>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                    <tr style=\"height:14.15pt\">\r\n"
				+ "                        <td width=\"100%\" style=\"width:100.0%;border:solid windowtext 1.0pt;border-top:none;padding:15.0pt 22.5pt 0cm 22.5pt\">\r\n"
				+ "                            <p style=\"margin-right:8.25pt;margin-bottom:12.0pt;margin-left:8.25pt;line-height:15.0pt\">\r\n"
				+ "                            </p>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </tbody>\r\n"
				+ "            </table>\r\n"
				+ "        </div>\r\n"
				+ "    </body>\r\n"
				+ "</html>");
		mailTemplate.setTemplatename("NominationReceived");
		mailRepository.save(mailTemplate);
	}

	if (nominationAccepted == null) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setMailtemp(
				"<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "    <head>\r\n"
				+ "        <meta charset=\"UTF-8\" />\r\n"
				+ "        <meta http-equiv=\"Content-Type\" content=\"text/html charset=UTF-8\" />\r\n"
				+ "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
				+ "        <!-- Added viewport meta tag for mobile responsiveness -->\r\n"
				+ "        <title>Email Template</title>\r\n"
				+ "    </head>\r\n"
				+ "    <body style=\"\r\n"
				+ "        margin: 0;\r\n"
				+ "        background-color: #ffffff;\r\n"
				+ "        font-family: Arial, sans-serif;\r\n"
				+ "        color: #333333;\r\n"
				+ "        \">\r\n"
				+ "        <div align=\"center\">\r\n"
				+ "            <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"74%\" style=\"width:74.98%;border-collapse:collapse\">\r\n"
				+ "                <tbody>\r\n"
				+ "                    <tr style=\"height:8.5pt\">\r\n"
				+ "                        <td width=\"100%\" style=\"width:100.0%;border:solid windowtext 1.0pt;border-bottom:none;padding:15.0pt 22.5pt 0cm 22.5pt;height:8.5pt\">\r\n"
				+ "                            <p style=\"margin:0cm;margin-bottom:.0001pt\"><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">Dear\r\n"
				+ "                                </span></b><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;\"> < Employee Name > </Employee></span></b><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">,\r\n"
				+ "                                </span></b><b><span style=\"font-size:14px;font-family:Calibri Light,sans-serif\"><u></u><u></u></span></b>\r\n"
				+ "                            </p>\r\n"
				+ "                            \r\n"
				+ "                            <p style=\"margin: 0cm; margin-bottom: 24pt;\"><span><b></b></span></p>\r\n"
				+ "                            <p style=\"margin:0cm;margin-bottom:.0001pt\"><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">We are delighted to inform you that your nomination for <b>  < Program Name > </b> has been accepted. We strongly believe that this track will be an excellent opportunity for you to find ‘A New You’ in your domain of expertise.<u></u><u></u></span></p>\r\n"
				+ "                            <p style=\"margin: 0cm; margin-bottom: 16pt;\"><span><b></b></span></p>\r\n"
				+ "                            <p class=\"MsoNormal\"><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">Please keep an eye on emails from <a href=\"mailto:GPL.Alchemy@godrejproperties.com\" target=\"_blank\"><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif\">gpl.alchemy@godrejproperties.<wbr>com</span></a> for the next steps. Further, you can track your progress on GPL Alchemy’s website <a href=\"https://alchemy.godrejproperties.com\" target=\"_blank\"><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif\">https://alchemy.godrejproperties.com</span></a>.<u></u><u></u></span></p>\r\n"
				+ "                            <p style=\"margin: 0cm; margin-bottom: 24pt;\"><span><b></b></span></p>\r\n"
				+ "                            <p class=\"MsoNormal\"><span style=\"font-size:14px;font-family:Calibri Light,sans-serif;color:black\">We are excited to have you onboard and look forward to an enriching and rewarding learning experience.<u></u><u></u></span></p>\r\n"
				+ "                            <p style=\"margin: 0cm; margin-bottom: 24pt;\"><span><b></b></span></p>\r\n"
				+ "                            <p style=\"line-height:8pt\"><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">Regards,</span></b><b><span style=\"font-family:Calibri Light,sans-serif\"><u></u><u></u></span></b></p>\r\n"
				+ "                            <p style=\"line-height:8pt\"><b><span style=\"font-family:Calibri Light,sans-serif;color:black\">Team GPL Alchemy</span></b><b><span style=\"font-family:Calibri Light,sans-serif;color:#1f497d\"><u></u><u></u></span></b></p>\r\n"
				+ "                            <p style=\"line-height:8pt\"><u></u><img  style=\"height: 80px;width: 140px; margin: 0;\" src=\"https://alchemy.godrejproperties.com/api/downloadFile/831f9e45-4fee-4679-9911-ea66e01f8219_newAlchemyLogo.png\" align=\"left\" hspace=\"12\" data-image-whitelisted=\"\" class=\"CToWUd\" data-bit=\"iit\"><u></u><b><span style=\"font-size:11.0pt;font-family:Calibri,sans-serif;color:#1f497d\"><u></u><u></u></span></b></p>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                    <tr style=\"height:31.2pt\">\r\n"
				+ "                        <td width=\"100%\" style=\"width:100.0%;border-top:none;border-left:solid windowtext 1.0pt;border-bottom:none;border-right:solid windowtext 1.0pt;padding:15.0pt 22.5pt 0cm 22.5pt\">\r\n"
				+ "                            <div style=\"border:solid #d2d2d2 1.0pt;padding:8.0pt 8.0pt 8.0pt 8.0pt\">\r\n"
				+ "                                <p style=\"line-height:15.0pt\"><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:black\">This is an electronically generated email. Please do not reply to this message. In case of any queries,</span></i><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:#1f497d\">\r\n"
				+ "                                    </span></i><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif;color:black\">please write to\r\n"
				+ "                                    </span></i><a href=\"mailto:GPL.Alchemy@godrejproperties.com\" target=\"_blank\"><i><span style=\"font-size:10.0pt;font-family:Calibri Light,sans-serif\">gpl.alchemy@godrejproperties.<wbr>com</span></i></a><i><span style=\"font-family:Calibri Light,sans-serif;color:black\"><u></u><u></u></span></i>\r\n"
				+ "                                </p>\r\n"
				+ "                            </div>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                    <tr style=\"height:14.15pt\">\r\n"
				+ "                        <td width=\"100%\" style=\"width:100.0%;border:solid windowtext 1.0pt;border-top:none;padding:15.0pt 22.5pt 0cm 22.5pt\">\r\n"
				+ "                            <p style=\"margin-right:8.25pt;margin-bottom:12.0pt;margin-left:8.25pt;line-height:15.0pt\">\r\n"
				+ "                            </p>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </tbody>\r\n"
				+ "            </table>\r\n"
				+ "        </div>\r\n"
				+ "    </body>\r\n"
				+ "</html>");
		mailTemplate.setTemplatename("NominationAccepted");
		mailRepository.save(mailTemplate);
	}

	
}
	
	@PostMapping
	public ResponseEntity<?> Add(MailTemplateDto mailTemplateDto) {
		try {

			templateInterface.addMailTemplate(mailTemplateDto);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.ADDED, SuccessMessageCode.SUCCESS),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.MAILTEMPLATE_E033701),
					HttpStatus.BAD_REQUEST);
		}

	}
	
	}
