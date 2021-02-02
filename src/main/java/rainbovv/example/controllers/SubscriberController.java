package rainbovv.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rainbovv.example.domain.Subscriber;
import rainbovv.example.repos.SubscriberRepo;

import java.util.List;

@RestController
public class SubscriberController {

	@Autowired
	SubscriberRepo sr;

	@GetMapping("/subscribers")
	public List<Subscriber> subscriberIndex() {

		List<Subscriber> subscribers = sr.getSubscribers();

		return subscribers;
	}
}
