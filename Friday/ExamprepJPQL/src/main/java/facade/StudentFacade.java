/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Semester;
import entity.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author benjaminbajrami
 */
public class StudentFacade {

    private static StudentFacade instance;
    private static EntityManagerFactory emf;

    public StudentFacade() {
    }

    public static StudentFacade getStudentFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StudentFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Student> getAllStudents() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Student> query = em.createNamedQuery("Select s FROM Student s", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> getAllStudentsWithNameAnders(String firstname) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Student> query =  em.createNamedQuery("Student.findByFirstname", Student.class)
                    .setParameter(firstname, "Anders");
            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public Student addStudent(Student student) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            return student;
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }
    public void setStudentToSemester(long studentID, long semesterID) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Student> query = em.createNamedQuery("Student.findById", Student.class);
            Student student = query.getSingleResult();
            TypedQuery<Semester> query2 = em.createNamedQuery("Semester.findById", Semester.class);
            Semester semester = query2.getSingleResult();
            student.setCurrentsemesterId(semester);
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    public List<Student> getStudentsWithLastNameAnd(String lastname) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Student> query =  em.createNamedQuery("Student.findByLastname", Student.class)
                    .setParameter(lastname, "And");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public int getStudentsCount() {
        EntityManager em = emf.createEntityManager();
        try {
            int query = em.createNamedQuery("Student.findStudentCount", Student.class).getResultList().size();
            return query;
        } finally {
            em.close();
        }
    }
    
    public int studentCountForSemester (String semesterName) {
        EntityManager em = emf.createEntityManager();
        try {
            int query = em.createNamedQuery("Semester.findStudentCount", Student.class)
                                        .setParameter(semesterName, "name").getResultList().size();
            return query;

        }finally {
            em.close();
        }
    }
}
