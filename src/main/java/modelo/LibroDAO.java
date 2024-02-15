package modelo;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class LibroDAO { 
	
	public List<Libro> getLibros() throws RuntimeException{
		
	        
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Biblioteca");
		EntityManager em = factory.createEntityManager();
		TypedQuery<Libro> q = em.createQuery("select l from Libro l",Libro.class);
		List<Libro> listaLibros = null;
		try {
			listaLibros = q.getResultList();
		} finally {
			em.close();
		}
		
		return listaLibros; 
	}

	public void insertar(Libro libro) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Biblioteca");
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.persist(libro);
			tx.commit();
		} catch (PersistenceException e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new RuntimeException("Error insertando libro" , e);
		}
		finally {
			em.close();
		}
	}
	
}
