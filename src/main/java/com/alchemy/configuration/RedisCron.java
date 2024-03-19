//package com.alchemy.configuration;
//
//import java.util.List;
//
//import javax.mail.MessagingException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import com.alchemy.repositories.ErrorLoggerRepository;
//import com.alchemy.repositories.UserRepository;
//import com.alchemy.serviceInterface.EmailInterface;
//import com.alchemy.utils.CacheOperation;
//import com.alchemy.utils.ErrorMessageCode;
//
//@Component
//@EnableScheduling
//public class RedisCron {
//
//	@Autowired
//	private ErrorLoggerRepository repository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private EmailInterface emailInterface;
//
//	@Autowired
//	private TemplateEngine templateEngine;
//
//	@Autowired
//	private CacheOperation cacheOperation;
//	
//	@Value("${spring.redis.host}")
//	private String redisHost;
//
//	@Scheduled(cron = "0 0 */1 * * *") // runs every 1 hours
//	public void checkRedisConnetionFailure() throws MessagingException {
//
//		Long errorCount = this.repository.getErrorCount();
//
//		if (errorCount >= 15 && (redisHost!="127.0.0.1" || redisHost !="localhost")) {
//			Context context = new Context();
//			List<String> list = this.userRepository.getUserByRoleAdmin();
//			for (int i = 0; i < list.size(); i++) {
//				String email = list.get(i);
//				context.setVariable("username", email);
//				context.setVariable("server", redisHost);
//				
//				String finalHtml = templateEngine.process("RedisconnectionFail", context);
//
//				this.emailInterface.sendSimpleMessage(email, "Redis Connection Fail", finalHtml);
//			}
//
//		}
//
//	}
//
//}
