package data;

import java.util.Set;

import entities.Hike;

public interface HikeDAO {
	Set<Hike> index();
	Hike show(int id);
	Hike create(Hike h);
	Hike update(Hike h, int id);
	boolean destroy(int id);
}
