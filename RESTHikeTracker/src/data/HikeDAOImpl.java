package data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import entities.Hike;

@Transactional
public class HikeDAOImpl implements HikeDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Set<Hike> index() {
		String query = "SELECT h FROM Hike h LEFT JOIN FETCH h.pictures";
		return new HashSet<Hike>(em.createQuery(query, Hike.class).getResultList());
	}

	@Override
	public Hike show(int id) {
		String query = "SELECT h FROM Hike h LEFT JOIN FETCH h.pictures WHERE h.id = :id";
		return em.createQuery(query,Hike.class).setParameter("id", id).getSingleResult();
	}

	@Override
	public Hike create(Hike h) {
		em.persist(h);
		em.flush();
		return h;
	}

	@Override
	public Hike update(Hike h, int id) {
		String query = "SELECT h FROM Hike h LEFT JOIN FETCH h.pictures WHERE h.id=:id";
		Hike updatedHike = em.createQuery(query,Hike.class).setParameter("id", id).getSingleResult();
		if (h.getName() != null) {
			updatedHike.setName(h.getName());
		}
		if (h.getDistance() != 0) {
			updatedHike.setDistance(h.getDistance());
		}
		if (h.getElevation() != 0) {
			updatedHike.setElevation(h.getElevation());
		}
		if (h.getTime() != 0) {
			updatedHike.setTime(h.getTime());
		}
		if (h.getPictures() != null) {
			updatedHike.setPictures(h.getPictures());
		}
		return updatedHike;
	}

	@Override
	public boolean destroy(int id) {
		Hike hikeToDelete = em.find(Hike.class, id);
		if (hikeToDelete != null) {
			em.remove(hikeToDelete);
			return true;
		}
		return false;
	}
}
