package rainbovv.example.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rainbovv.example.domain.Subscriber;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SubscriberRepo {

	@Autowired
	JdbcTemplate jdbcT;

	public List<Subscriber> getsubscribers() {

		String selectEmailsQuery = "Select email From public.subscribers;";
		String selectNamesQuery = "Select name From public.subscribers;";
		String selectIdQuery = "Select id From public.subscribers;";

		List<String> emails = jdbcT.queryForList(selectEmailsQuery, String.class);
		List<String> names = jdbcT.queryForList(selectNamesQuery, String.class);
		List<Integer> ids = jdbcT.queryForList(selectIdQuery, Integer.class);

		List<Subscriber> subscribers = new ArrayList<>();

		for (int i = 0; i < emails.size(); i++) {

			subscribers.add(new Subscriber(ids.get(i), names.get(i), emails.get(i)));
		}

		return subscribers;
	}
}
