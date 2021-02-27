package rainbovv.example.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import rainbovv.example.domain.exceptions.NothingToSendException;
import rainbovv.example.domain.message.Message;
import rainbovv.example.domain.message.MessageMapper;
import rainbovv.example.domain.subscriber.Subscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MessageRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SubscriberRepo subscriberRepo;

    public void saveMessage(Message message) {

        String sql = String.format("INSERT INTO public.messages("
                                + "content)%nVALUES ('%s');",message.getContent());

        jdbcTemplate.update(sql);
    }

    public Message getMessageById(int id) {

        String sql = String.format("SELECT * FROM public.messages%n"
                                + "WHERE id = %d", id);

        return jdbcTemplate.queryForObject(sql, new MessageMapper());
    }

    public List<Message> getMessages() {

        return jdbcTemplate.query("SELECT * FROM public.messages",
                                    new MessageMapper());
    }

    public Map<Subscriber, Message> getNextUnsentMessage() throws NothingToSendException {

        Map<Subscriber, Message> tuple = new HashMap<>();

        String sql = "SELECT subscriber_id, message_id FROM public.message_subscriber"
                + "\nWHERE sent = false LIMIT 1 OFFSET 0;";

        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sql);

        if (!resultSet.first()) {
            throw new NothingToSendException();
        }

        Message message = getMessageById(resultSet.getInt("message_id"));
        Subscriber subscriber = subscriberRepo.getSubscriberById(resultSet.getInt("subscriber_id"));

        tuple.put(subscriber, message);

        return tuple;
    }

    public void setMessageAsSent(int messageId) {

        jdbcTemplate.update("UPDATE message_subscriber SET sent=true WHERE message_id="+messageId+';');
    }
}
