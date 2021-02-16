package rainbovv.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rainbovv.example.domain.message.Message;
import rainbovv.example.repos.MessageSubscriberIDRepo;
import rainbovv.example.repos.MessageRepo;
import rainbovv.example.repos.SubscriberRepo;

import java.util.List;


@Controller
public class AdminController {

	@Autowired
	SubscriberRepo subscriberRepo;
	@Autowired
	MessageRepo messageRepo;
	@Autowired
	MessageSubscriberIDRepo idRepo;

	@PostMapping("/admin/subscribers")
	public String adminSubscriberIndex(@RequestParam String message,
			Model model) {

		if ( message.contains("'"))
			message = message.replace("'", "''");

		messageRepo.saveMessage(new Message(message));
		int messageId = messageRepo.getMessageIdByContent(message);

		model.addAttribute("subscribers", subscriberRepo.getSubscribers());

		return "admin/subscribers";
	}
	@PostMapping("/admin/subscribers/compose")
	public String adminSubscriberCompose(@RequestParam List<Integer> subscribers,
										 @RequestParam String message) {

		if ( message.contains("'"))
			message = message.replace("'", "''");

		messageRepo.saveMessage(new Message(message));
		int messageId = messageRepo.getMessageIdByContent(message);

		subscribers.forEach(i -> idRepo.adIds(i,messageId));

		List<String> emails = subscriberRepo.getSubscribersEmailsByIds(subscribers);
		return "admin/compose";
	}

	@GetMapping("/admin/subscribers/send")
	public String adminSubscriberSendMessage(Model model/*@RequestParam List<Integer> ids,*/) {

		model.addAttribute("subscribers", subscriberRepo.getSubscribers());

		return "admin/send";
	}

	@PostMapping("/admin/subscribers/remove")
	public String adminSubscriberSetName(@RequestParam int id) {

		subscriberRepo.removeById(id);

		return "Subscriber " + id + " removed!";
	}


	@PostMapping("/admin/subscribers/setemail")
	public String adminSubscriberSetEmail(@RequestParam int id,
	                                      @RequestParam String email) {

		subscriberRepo.updateEmail(id, email);

		return "Subscriber's email updated";
	}

}
