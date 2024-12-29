import com.manning.javapersistence.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class HelloWorldHibernateTest {
    private static SessionFactory createSessionFactory(){
        // To create SessionFactory we need Configuration object
        Configuration configuration = new Configuration();
        configuration.configure().addAnnotatedClass(Message.class);
        ServiceRegistry serviceRegistry =  new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
    @Test
    void storeLoadMessage(){
        try (SessionFactory sessionFactory = createSessionFactory()){
            Session session = sessionFactory.openSession();

            session.beginTransaction();
            Message message = new Message();
            message.setText("Hello World from Hibernate!");

            session.persist(message);

            session.getTransaction().commit();

            // begin another transaction
            session.beginTransaction();
            CriteriaQuery<Message> criteriaQuery = session.getCriteriaBuilder().createQuery(Message.class); // L
            criteriaQuery.from(Message.class); //M
            // SELECT * from MESSAGE
            List<Message> messages =
                    session.createQuery(criteriaQuery).getResultList(); // N
            session.getTransaction().commit();
            Assertions.assertAll(
                    () -> Assertions.assertEquals(1, messages.size()),
                    () -> Assertions.assertEquals("Hello World from Hibernate!",
                            messages.get(0).getText())
            );
        }
    }
}
