package rainbovv.example.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rainbovv.example.domain.exceptions.NothingToSendException;
import rainbovv.example.domain.message.Message;
import rainbovv.example.domain.subscriber.Subscriber;
import rainbovv.example.repos.MessageSubscriberRepo;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class EmailScheduler {

	@Autowired
	MessageSubscriberRepo messageSubscriberRepo;
	@Autowired
	JavaMailSender javaMailSender;

	@Scheduled(initialDelay = 1000, fixedRate = 10000)
	public void sendEmail() {

		System.err.println("Preparing to send email!");
		Map<Message, Set<Subscriber>> tuple;
		try {
			Subscriber subscriber;
			tuple = messageSubscriberRepo.getUnsentMessages();
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

			for (Message message : tuple.keySet()) {
				Iterator<Subscriber> subscriberIterator = tuple.get(message).iterator();
				subscriber = subscriberIterator.next();

				simpleMailMessage.setFrom("Admin");
				simpleMailMessage.setTo(subscriber.getEmail());
				simpleMailMessage.setSubject("New Message");
				simpleMailMessage.setText(message.getContent());

				System.out.println(simpleMailMessage.toString());
//				javaMailSender.send(simpleMailMessage);
				messageSubscriberRepo.setMessageAsSent(message.getId());
			}

		} catch (NothingToSendException e) {
			System.err.println(e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
