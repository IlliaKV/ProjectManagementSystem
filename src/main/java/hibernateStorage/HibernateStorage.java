package hibernateStorage;

import entity.Developer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateStorage {
    private SessionFactory sessionFactory;

    public HibernateStorage(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void createDeveloper(Developer developer){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(developer);
        transaction.commit();
        session.close();
    }

    public Developer getDeveloper(long idDeveloper){
        Developer developer = null;

        Session session = sessionFactory.openSession();
        developer = session.get(Developer.class, idDeveloper);
        session.close();

        return developer;
    }

    public void updateDeveloper(Developer developer){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(developer);
        transaction.commit();
        session.close();
    }

    public void deleteDeveloper(Developer developer){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(developer);
        transaction.commit();
        session.close();
    }
    public static void main(String[] args) {
        HibernateStorage hibernateStorage = new HibernateStorage();
        Developer developer = new Developer();

//        developer.setFirstName("Adam");
//        developer.setLastName("Lavlei");
//        developer.setSex(1);
//        developer.setAge(29);
//        developer.setSalary(9800);
//        hibernateStorage.createDeveloper(developer);  //create

//        developer = hibernateStorage.getDeveloper(9);   //read
//        System.out.println(developer);
//        developer.setAge(30);
//        hibernateStorage.updateDeveloper(developer);    //update
//        System.out.println(developer);

//        developer = hibernateStorage.getDeveloper(9);
//        System.out.println(developer);
//        hibernateStorage.deleteDeveloper(developer);    //delete
    }
}
