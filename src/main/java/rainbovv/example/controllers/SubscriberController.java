package rainbovv.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rainbovv.example.domain.subscriber.Subscriber;
import rainbovv.example.repos.SubscriberRepo;
import java.util.List;

@RestController
public class SubscriberController {

	@Autowired
	SubscriberRepo subscriberRepo;

	@GetMapping("/subscribers")
	public List<Subscriber> subscriberIndex() {

		return subscriberRepo.getSubscribers();
	}

	@PostMapping("/subscribers/add")
	public String subscriberAdd(@RequestParam String email,
	                            @RequestParam String name ) {

		subscriberRepo.save(new Subscriber(name, email));

		return "Subscriber Added!";
	}

	@GetMapping("/subscribers/setname/{email}/{newName}")
	public String subscriberSetName(@PathVariable String email,
	                            @PathVariable String newName ) {

		subscriberRepo.updateName(email,newName);

		return "Subscriber's name updated!";
	}


}
