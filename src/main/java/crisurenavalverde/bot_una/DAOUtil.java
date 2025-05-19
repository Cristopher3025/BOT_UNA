package crisurenavalverde.bot_una;

import jakarta.persistence.*;
import java.util.List;

public class DAOUtil {

    private EntityManagerFactory emf;

    public DAOUtil() {
        emf = Persistence.createEntityManagerFactory("unaBotPersistence");
    }

    public void addUser(UsersBot user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public List<UsersBot> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<UsersBot> users = em.createQuery("SELECT u FROM UsersBot u", UsersBot.class).getResultList();
        em.close();
        return users;
    }

    public void updateUser(UsersBot user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long id) {
        EntityManager em = emf.createEntityManager();
        UsersBot user = em.find(UsersBot.class, id);
        if (user != null) {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        }
        em.close();
    }
}

