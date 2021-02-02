package rainbovv.example.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rainbovv.example.domain.subscriber.Subscriber;
import rainbovv.example.domain.subscriber.SubscriberMapper;
import java.util.List;

@Repository
public class SubscriberRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Subscriber> getSubscribers() {

		return jdbcTemplate.query("Select * From public.subscribers;",
									new SubscriberMapper());
	}
}