package rainbovv.example.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController extends SubscriberController{

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
