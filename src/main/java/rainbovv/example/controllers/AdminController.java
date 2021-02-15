package rainbovv.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rainbovv.example.repos.SubscriberRepo;

import java.util.List;


@Controller
public class AdminController {

	@Autowired
	SubscriberRepo subscriberRepo;

	@GetMapping("/admin/subscribers")
	public String adminSubscriberIndex(Model model) {

		model.addAttribute("subscribers", subscriberRepo.getSubscribers());
		return "admin/subscribers";
	}
	@GetMapping("/admin/subscribers/compose")
	public String adminSubscriberCompose(@RequestParam List<Integer> subscribers) {

		List<String> emails = subscriberRepo.getSubscribersEmailsByIds(subscribers);
		return "admin/compose";
	}

	@PostMapping("/admin/subscribers/remove")
	public String adminSubscriberSetName(@RequestParam int id) {

		subscriberRepo.removeById(id);

		return "Subscriber " + id + " removed!";
	}

	@PostMapping("/admin/subscribers/send")
	public String adminSubscriberSendMessage(@RequestParam String message) {

		return "Admin sends a message:<br>" + message;
	}

	@PostMapping("/admin/subscribers/setemail")
	public String adminSubscriberSetEmail(@RequestParam int id,
	                                      @RequestParam String email) {

		subscriberRepo.updateEmail(id, email);

		return "Subscriber's email updated";
	}

}
