package hiber.dao;

import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        try {
            sessionFactory.getCurrentSession().save(user);
        } catch (HibernateException e) {
            System.out.println("Something strange while saving user " + user);
            e.printStackTrace(System.out);
        }
    }

    @Override
    public User getUserWithCar(String model, int series) {
        User result = null;
        try {
            TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User where car.model = :carModel and car.series = :carSeries");
            query.setParameter("carModel", model);
            query.setParameter("carSeries", series);
            result = query.getSingleResult();
        } catch (HibernateException e) {
            System.out.println("i can't return a user (((");
            e.printStackTrace(System.out);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
       List<User> result = new ArrayList<>();
       try {
          TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
           query.getResultList();
       } catch (Exception e) {
          System.out.println("Some problems with getting all users from DB");
          e.printStackTrace(System.out);
       }
       return result;
    }
}
