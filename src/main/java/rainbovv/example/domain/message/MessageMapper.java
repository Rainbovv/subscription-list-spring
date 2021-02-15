package rainbovv.example.domain.message;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {


    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {

        Message message = new Message();

        message.setId(resultSet.getInt("id"));
        message.setContent(resultSet.getString("content"));

        return message;
    }
}
