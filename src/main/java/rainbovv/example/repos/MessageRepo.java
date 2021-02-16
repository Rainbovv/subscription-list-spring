package rainbovv.example.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rainbovv.example.domain.message.Message;
import rainbovv.example.domain.message.MessageMapper;

import java.util.List;

@Repository
public class MessageRepo {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public void updateMessageContent(int id, String content) {

        String sql = String.format("UPDATE public.message%n" +
                                    "SET content='%s'%n" +
                                    "WHERE id=%d", content, id);
        jdbcTemplate.update(sql);
    }

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

    public int getMessageIdByContent(String content) {

        String sql = String.format("SELECT id FROM public.messages%n" +
                "WHERE content='%s';", content);

        return jdbcTemplate.queryForList(sql, Integer.class).get(0);
    }

    public List<Message> getMessages() {

        return jdbcTemplate.query("SELECT * FROM public.messages",
                                    new MessageMapper());
    }
}
