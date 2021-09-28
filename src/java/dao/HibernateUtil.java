/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Accusation;
import domain.Admin;
import domain.Department;
import domain.Device;
import domain.DeviceImage;
import domain.Faculty;
import domain.Lecturer;
import domain.Movement;
import domain.Person;
import domain.Security;
import domain.Staff;
import domain.Student;
import domain.University;
import domain.User;
import domain.Visitor;
import java.util.Properties;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author nizey
 */
public class HibernateUtil {

private static final Logger LOG = Logger.getLogger(HibernateUtil.class.getName());
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                Properties settings = new Properties();
                
                settings.put(Environment.DRIVER, System.getenv("DB_DRIVER"));
                settings.put(Environment.URL, System.getenv("DB_URL"));
                settings.put(Environment.USER, System.getenv("DB_USERNAME"));
                settings.put(Environment.PASS, System.getenv("DB_PASSWORD"));
                settings.put(Environment.DIALECT, System.getenv("DB_DIALECT"));
                settings.put(Environment.SHOW_SQL, System.getenv("DB_SHOW_SQL"));
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, System.getenv("DB_HBM2DDL_AUTO"));
                
                config.setProperties(settings);
                
                config.addAnnotatedClass(Accusation.class);
                config.addAnnotatedClass(Department.class);
                config.addAnnotatedClass(Device.class);
                config.addAnnotatedClass(Faculty.class);
                config.addAnnotatedClass(Movement.class);
                config.addAnnotatedClass(Lecturer.class);
                config.addAnnotatedClass(Person.class);
                config.addAnnotatedClass(Staff.class);
                config.addAnnotatedClass(University.class);
                config.addAnnotatedClass(User.class);
                config.addAnnotatedClass(Visitor.class);
                config.addAnnotatedClass(Security.class);
                config.addAnnotatedClass(DeviceImage.class);
                config.addAnnotatedClass(Person.class);
                config.addAnnotatedClass(Student.class);
                config.addAnnotatedClass(Admin.class);

                
                ServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();
                LOG.info("Hibernate Configuration Service Registry Created");
                sessionFactory = config.buildSessionFactory(registry);
                return sessionFactory;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
        
    }
}
