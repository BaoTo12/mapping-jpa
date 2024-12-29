package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

// enable Spring Data JPA
// Scan the repo specified in the annotation
@EnableJpaRepositories("com.manning.javapersistence.repository") // A
public class SpringDataConfiguration {
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ch02?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
    // needed by JPA to interact with Hibernate.
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new
                HibernateJpaVendorAdapter(); // H
        // Configure this vendor adapter to access a MySQL database.
        jpaVendorAdapter.setDatabase(Database.MYSQL); // I
        jpaVendorAdapter.setShowSql(true); // J
        return jpaVendorAdapter; // H
    }
    // This is a factory bean that
    //produces an EntityManagerFactory
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() { // K
        LocalContainerEntityManagerFactoryBean // K
                localContainerEntityManagerFactoryBean = // K
                new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource()); // L
        Properties properties = new Properties(); // M
        properties.put("hibernate.hbm2ddl.auto", "create");
        localContainerEntityManagerFactoryBean.
                setJpaProperties(properties);
        localContainerEntityManagerFactoryBean.// M
                setJpaVendorAdapter(jpaVendorAdapter()); // N
        localContainerEntityManagerFactoryBean.
                setPackagesToScan("com.manning.javapersistence"); // O
        return localContainerEntityManagerFactoryBean;// K
    }
}
