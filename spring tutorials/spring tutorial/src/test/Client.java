package test;

import beans.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by grace on 01/02/17.
 */
public class Client {
    public static void main(String[] args) {
        Resource resource=new ClassPathResource("resources/spring.xml");

        BeanFactory factory=new XmlBeanFactory(resource);

        System.out.println("Client ..");

        factory.getBean("t");
    }
}
