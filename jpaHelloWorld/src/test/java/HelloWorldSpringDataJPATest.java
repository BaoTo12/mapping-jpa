import com.manning.javapersistence.Message;
import com.manning.javapersistence.repository.MessageRepository;
import configuration.SpringDataConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

// is used to integrate Spring test vs Junit of Jupiter
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringDataConfiguration.class})
public class HelloWorldSpringDataJPATest {
    @Autowired
    private MessageRepository messageRepository;
    @Test
    public void storeLoadMessage(){
        Message message = new Message();
        message.setText("Hello word with Spring data JPA");

        messageRepository.save(message);

        List<Message> list = (List<Message>) messageRepository.findAll();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, list.size()),
                ()-> Assertions.assertEquals("Hello word with Spring data JPA", list.get(0).getText())
        );
    }
}
