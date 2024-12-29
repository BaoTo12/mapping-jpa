import com.manning.javapersistence.Message;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldHibernateToJPATest {
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
}
