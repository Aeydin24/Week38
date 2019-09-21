package facades;

import entities.Person;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            return (long) em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
        } finally {
            em.close();
        }
    }

    public Person getPerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            Person person = em.find(Person.class, id);
            if (person != null) {
                return person;
            }
            throw new PersonNotFoundException("No person with provided id found");
        } finally {
            em.close();
        }
    }

    public List<Person> getAllPersons() throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            List<Person> result = em.createNamedQuery("Person.getAllPersons").getResultList();
            if (result != null && !result.isEmpty()) {
                return result;
            }
            throw new PersonNotFoundException("Problem finding all persons");
        } finally {
            em.close();
        }
    }

    public Person addPerson(String fName, String lName, String phone) throws MissingInputException {
        if (((((fName == null || lName == null) || phone == null) || fName.isEmpty()) || lName.isEmpty()) || phone.isEmpty()) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        }
        EntityManager em = getEntityManager();
        try {
            Person target = new Person(fName, lName, phone);
            //if (target != null) { //all input is already checked.
            em.getTransaction().begin();
            em.persist(target);
            em.getTransaction().commit();
            return target;
            //}
            //em.getTransaction().rollback();
            //throw new PersonNotFoundException("Could not add user");
        } finally {
            em.close();
        }
    }

    public Person deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person result = em.find(Person.class,
                    id);
            if (result != null) {
                em.remove(result);
                em.getTransaction().commit();
                return result;
            }
            throw new PersonNotFoundException("Could not delete, provided id does not exist");
        } finally {
            em.close();
        }
    }

    public Person editPerson(Person p) throws MissingInputException {
        if (p == null
                || p.getFirstName() == null
                || p.getLastName() == null
                || p.getPhone() == null
                || p.getFirstName().isEmpty()
                || p.getLastName().isEmpty()
                || p.getPhone().isEmpty()) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        }

        EntityManager em = getEntityManager();
        try {
            //if (p != null) { //all input is already checked.
            em.getTransaction().begin();
            p.setLastEdited(new Date());
            em.merge(p);
            em.getTransaction().commit();
            return p;
            //}
            //em.getTransaction().rollback();
            //throw new PersonNotFoundException("Problem editing person");
        } finally {
            em.close();
        }
    }
    
}
