/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Entity.Address;
import Entity.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author benjaminbajrami
 */
public class Tester2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        Customer cust = new Customer("Kurt", "Wonnegut");
        Address address = new Address("Benjaminvej 45", "Skovlunde");
        cust.setAddress(address);

        try {
            em.getTransaction().begin();
            
            em.persist(cust);
             
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
    }
}
