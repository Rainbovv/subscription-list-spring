package rainbovv.example.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rainbovv.example.domain.message.Message;
import rainbovv.example.domain.message.MessageMapper;
import rainbovv.example.domain.subscriber.Subscriber;
import rainbovv.example.domain.subscriber.SubscriberMapper;
import java.util.List;

@Repository
public class SubscriberRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;


	public Subscriber getSubscriberById(int id) {

		String sql = String.format("SELECT * FROM public.subscribers%n"
				+ "WHERE id = %d", id);

		return jdbcTemplate.queryForObject(sql, new SubscriberMapper());
	}


	public Subscriber getSubscriber(int id) {

		return jdbcTemplate.query("SELECT * FROM public.subscribers \n WHERE id =" + id+';',
				new SubscriberMapper()).get(0);
	}

	public List<Subscriber> getSubscribers() {

		return jdbcTemplate.query("SELECT * FROM public.subscribers;",
									new SubscriberMapper());
	}

	public List<String> getSubscribersEmailsByIds(List<Integer> ids) {

		StringBuilder id_values = new StringBuilder();

		for (Integer id : ids) {
			id_values.append(id).append(",");
		}
		id_values = new StringBuilder(id_values.substring(0, id_values.length() - 1));



		return jdbcTemplate.queryForList("SELECT email FROM public.subscribers WHERE id IN ( "+id_values+" );",
				String.class);
	}

	public void save(Subscriber subscriber) {

		jdbcTemplate.update("INSERT INTO public.subscribers("
								+ "name, email)"
								+ "VALUES ('" + subscriber.getName() + "','"
								+ subscriber.getEmail() + "' );");
	}

	public void updateName(String email, String newName) {


		jdbcTemplate.update("UPDATE public.subscribers\n"
								+ "SET name = '" + newName + "'\n"
								+ "WHERE email = '" + email + "';");
	}

	public void updateEmail(int id, String newEmail) {

		String sql = String.format("UPDATE public.subscribers%n" +
									"SET email = '%s'%n" +
									"WHERE id = %d", newEmail, id);

		jdbcTemplate.update(sql);
	}

	public void removeById(int id) {

		String sql = String.format("DELETE FROM public.subscribers%n" +
									"WHERE id = %d", id);

		jdbcTemplate.update(sql);
	}
}