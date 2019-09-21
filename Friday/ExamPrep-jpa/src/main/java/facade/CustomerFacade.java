/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import entity.Customer;
import entity.ItemType;
import entity.Order;
import entity.OrderLine;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author benjaminbajrami
 */
public class CustomerFacade {

        private static CustomerFacade instance;
        private static EntityManagerFactory emf;

        public CustomerFacade() {
        }

        public static CustomerFacade getStudentFacade(EntityManagerFactory _emf) {
            if (instance == null) {
                emf = _emf;
                instance = new CustomerFacade();
            }
            return instance;
        }

        private EntityManager getEntityManager() {
            return emf.createEntityManager();
        }

    public Customer addCustomer(Customer newCustomer) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newCustomer);
            em.getTransaction().commit();
            return newCustomer;
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer getCustomerById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer customer = em.find(Customer.class, id);
            return customer;
        } finally {
            em.close();
        }
    }

    public ItemType addItemType(ItemType newItemType) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newItemType);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return null;
    }
    
    public ItemType getItemTypeById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            ItemType itemtype = em.find(ItemType.class, id);
            return itemtype;
        } finally {
            em.close();
        }
    }
    public Order createOrder (Customer customer, List<OrderLine> quantity) {
        EntityManager em = emf.createEntityManager();
        Order newOrder = new Order(1, customer, quantity);
        try {
            em.getTransaction().begin();
            em.persist(newOrder);
            em.getTransaction().commit();
            return newOrder;
        } finally {
            em.close();
        }
    }
    public OrderLine createSpecificItem (Order order, OrderLine orderline) {
        EntityManager em = emf.createEntityManager();
        orderline.setOrder(order);
        try {
            em.getTransaction().begin();
            em.persist(orderline);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return null;
    } 
    public List<Order> findAllOrders (Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.find(Customer.class, customer);
        return customer.getOrders();
    }
}
