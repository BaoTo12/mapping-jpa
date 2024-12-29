import com.manning.javapersistence.Message;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloWorldJPATest {
    private static EntityManagerFactory createEntityManagerFactory() {
        Configuration configuration = new Configuration();
        configuration.configure().addAnnotatedClass(Message.class);
        Map<String, String> properties = new HashMap<>();
        Enumeration<?> propertyNames = configuration.getProperties().propertyNames(); // D
        while (propertyNames.hasMoreElements()){
            String element = (String) propertyNames.nextElement(); // E
            properties.put(element, configuration.getProperties().getProperty(element)); // E
        }
        return Persistence.createEntityManagerFactory("ch02", properties);  // F

    }
    @Test
    public void storeLoadMessage() {
        EntityManagerFactory managerFactory = createEntityManagerFactory();
        try {
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Message message = new Message();
            message.setText("Hello World");
            // to make message persistent. Hibernate doesn't call the database immediately
            entityManager.persist(message);
            // commit
            //INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World!')
            entityManager.getTransaction().commit();

            // begin another transaction
            entityManager.getTransaction().begin();
            //SELECT * from MESSAGE
            List<Message> messages = entityManager.createQuery("select m from com.manning.javaperistence.Message m", Message.class).getResultList();
            messages.get(messages.size() - 1).
                    setText("Hello World from Hibernate to JPA!");
            entityManager.getTransaction().commit();
            Assertions.assertAll(
                    () -> Assertions.assertEquals(1, messages.size()),
                    () -> Assertions.assertEquals("Hello World from Hibernate to JPA!",
                            messages.get(0).getText())
            );
            entityManager.close();
        } finally {
            managerFactory.close();
        }
    }
//    @Test
//    public void storeLoadMessage() {
//        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("ch02");
//        try {
//            EntityManager entityManager = managerFactory.createEntityManager();
//            entityManager.getTransaction().begin();
//            com.manning.javaperistence.Message message = new com.manning.javaperistence.Message();
//            message.setText("Hello World");
//            // to make message persistent. Hibernate doesn't call the database immediately
//            entityManager.persist(message);
//            // commit
//            //INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World!')
//            entityManager.getTransaction().commit();
//
//            // begin another transaction
//            entityManager.getTransaction().begin();
//            //SELECT * from MESSAGE
//            List<com.manning.javaperistence.Message> messages = entityManager.createQuery("select m from com.manning.javaperistence.Message m", com.manning.javaperistence.Message.class).getResultList();
//            messages.get(messages.size() - 1).
//                    setText("Hello World from JPA!");
//            entityManager.getTransaction().commit();
//            Assertions.assertAll(
//                    () -> Assertions.assertEquals(1, messages.size()),
//                    () -> Assertions.assertEquals("Hello World from JPA!",
//                            messages.get(0).getText())
//            );
//            entityManager.close();
//        } finally {
//            managerFactory.close();
//        }
//    }
}
