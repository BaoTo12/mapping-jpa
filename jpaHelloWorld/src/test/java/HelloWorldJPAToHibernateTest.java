import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HelloWorldJPAToHibernateTest {
    private static SessionFactory getSessionFactory(EntityManagerFactory managerFactory){
        return managerFactory.unwrap(SessionFactory.class);
    }
}
