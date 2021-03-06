package rainbovv.example.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import rainbovv.example.domain.exceptions.NothingToSendException;
import rainbovv.example.domain.message.Message;
import rainbovv.example.domain.subscriber.Subscriber;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class MessageSubscriberRepo {

    @Autowired
    SubscriberRepo subscriberRepo;

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Map<Message, Set<Subscriber>> getUnsentMessages() throws NothingToSendException {

        Map<Message, Set<Subscriber>> tuple = new HashMap<>();

        String sql = "SELECT subscriber_id, message_id FROM public.message_subscriber"
                + "\nWHERE sent = false;";

        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sql);

        if (!resultSet.first()) {
            throw new NothingToSendException();
        }

        do {
            Message message = messageRepo.getMessageById(resultSet.getInt("message_id"));
            Subscriber subscriber = subscriberRepo.getSubscriberById(resultSet.getInt("subscriber_id"));


            if (tuple.get(message) == null) {
                Set<Subscriber> subscribers = new HashSet<>();
                tuple.put(message,subscribers);
            }
            tuple.get(message).add(subscriber);

        } while (resultSet.next());

        return tuple;
    }

    public void addNewRow(int subscriberId, int messageId) {

        String sql = "INSERT INTO public.message_subscriber" +
                    "\n (subscriber_id, message_id) VALUES (" +
                    subscriberId + ", " + messageId + ");";

        jdbcTemplate.update(sql);
    }

    public void setMessageAsSent(int messageId) {

        jdbcTemplate.update("UPDATE message_subscriber SET sent=true WHERE message_id="+messageId+';');
    }
}
