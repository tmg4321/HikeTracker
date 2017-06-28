package test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Hike;
import entities.Picture;

public class HikeTest {
	
	private EntityManager em;

	@Before
	  public void setUp() throws Exception { 
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("HikeTracker");
		em = emf.createEntityManager();
	}
	
	@After
	  public void tearDown() throws Exception { 
		if (em != null) {
			em.close();
		}
	}
	
	@Test
	public void test() {
	  boolean pass = true;
	  assertEquals(pass, true);
	}
	
	@Test
	public void testCanRetreiveHike() {
		Hike h = em.find(Hike.class,1);
		assertEquals("Bluffs Regional Park",h.getName());
		assertEquals("2.4", ""+h.getDistance());
	}
	
	@Test
	public void testCanRetreivePicture() {
		Picture p = em.find(Picture.class,1);
		assertEquals("http://dayhikesneardenver.com/wp-content/uploads/2010/08/01-bluffs-loop-trail-header.jpg",p.getUrl());
		assertEquals("1", ""+p.getHike().getId());
	}
	
	@Test
	public void testCanRetreiveSetOfPicture() {
		Hike h = em.find(Hike.class,1);
		assertEquals(1,h.getPictures().size());
	}
	@Test
	public void testCanRetreiveAllHikes() {
		String query = "SELECT h FROM Hike h";
		Set<Hike> hikes = new HashSet<Hike>(em.createQuery(query,Hike.class).getResultList());
		assertEquals(8,hikes.size());
	}
}
