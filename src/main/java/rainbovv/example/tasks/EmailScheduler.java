package rainbovv.example.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rainbovv.example.domain.exceptions.NothingToSendException;
import rainbovv.example.domain.message.Message;
import rainbovv.example.domain.subscriber.Subscriber;
import rainbovv.example.repos.MessageRepo;

import java.util.Iterator;
import java.util.Map;

@Component
public class EmailScheduler {

	@Autowired
	MessageRepo messageRepo;
	@Autowired
	JavaMailSender javaMailSender;

	@Scheduled(initialDelay = 10000, fixedRate = 60000)
	public void sendEmail() {

		System.err.println("Preparing to send email!");
		Map<Subscriber, Message> tuple;
		try {
			tuple = messageRepo.getNextUnsentMessage();
			Iterator<Message> messageIterator = tuple.values().iterator();
			Message message;
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

			for (Subscriber subscriber : tuple.keySet()) {
				message = messageIterator.next();

				simpleMailMessage.setFrom("Admin");
				simpleMailMessage.setTo(subscriber.getEmail());
				simpleMailMessage.setSubject("New Message");
				simpleMailMessage.setText(message.getContent());

				System.out.println(simpleMailMessage.toString());
//				javaMailSender.send(simpleMailMessage);
				messageRepo.setMessageAsSent(message.getId());
			}

		} catch (NothingToSendException e) {
			System.err.println(e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
