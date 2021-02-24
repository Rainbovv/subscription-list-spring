package rainbovv.example.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rainbovv.example.domain.message.Message;
import rainbovv.example.domain.subscriber.Subscriber;
import rainbovv.example.repos.MessageRepo;

import java.util.Map;

@Component
public class EmailScheduler {

	@Autowired
	MessageRepo messageRepo;
	@Autowired
	JavaMailSender javaMailSender;

	@Scheduled(initialDelay = 10000, fixedRate = 1000)
	public void sendEmail() {

		System.err.println("Preparing to send email!");
		Map<Subscriber, Message> tuple = messageRepo.getNextUnsentMessage();
		System.err.println(tuple);
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setFrom("sdads");
		simpleMailMessage.setTo(((Subscriber)tuple.keySet().toArray()[0]).getEmail());
		simpleMailMessage.setSubject("new letter");
		simpleMailMessage.setText(((Message)tuple.values().toArray()[0]).getContent());

		javaMailSender.send(simpleMailMessage);

	}
}
