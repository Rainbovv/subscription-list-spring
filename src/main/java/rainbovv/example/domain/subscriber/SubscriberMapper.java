package rainbovv.example.domain.subscriber;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SubscriberMapper implements RowMapper<Subscriber> {

	@Override
	public Subscriber mapRow(ResultSet resultSet, int i) throws SQLException {

		Subscriber subscriber = new Subscriber();

		subscriber.setId(resultSet.getInt("id"));
		subscriber.setName(resultSet.getString("name"));
		subscriber.setEmail(resultSet.getString("email"));

		return subscriber;
	}
}
