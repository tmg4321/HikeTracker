package controllers;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.HikeDAO;
import entities.Hike;

@RestController
public class HikeController {

	@Autowired
	private HikeDAO hikedao;
	
	@RequestMapping(path = "ping", method = RequestMethod.GET)
	public String ping() {
		return "pong";
	}
	
	@RequestMapping(path = "hikes", method = RequestMethod.GET)
	public Set<Hike> index() {
		return hikedao.index();
	}
	
	@RequestMapping(path = "hikes/{id}", method = RequestMethod.GET)
	public Hike show(@PathVariable int id) {
		return hikedao.show(id);
	}
	
	@RequestMapping(path = "hikes", method = RequestMethod.POST)
	public Hike create(@RequestBody String quizJSON, HttpServletResponse res) {
		ObjectMapper mapper = new ObjectMapper();
		Hike newHike = null;
		
		try {
			newHike = mapper.readValue(quizJSON, Hike.class);
			newHike = hikedao.create(newHike);
			res.setStatus(201);
		}
		catch (Exception e) {
			e.printStackTrace();
			res.setStatus(404);
		}
		return newHike;
	}
	
	@RequestMapping(path = "hikes/{id}", method = RequestMethod.PUT)
	public Hike update(@RequestBody String quizJSON, @PathVariable int id, HttpServletResponse res) {
		ObjectMapper mapper = new ObjectMapper();
		Hike updatedHike = null;
		
		try{
			updatedHike = mapper.readValue(quizJSON, Hike.class);
			updatedHike = hikedao.update(updatedHike,id);
			res.setStatus(202);
		}
		catch (Exception e) {
			e.printStackTrace();
			res.setStatus(404);
		}
				
		return updatedHike;
	}
	
	@RequestMapping(path = "hikes/{id}", method = RequestMethod.DELETE)
	public boolean destroy(@PathVariable int id, HttpServletResponse res) {
		if (hikedao.destroy(id)) {
			res.setStatus(202);
			return true;
		}
		res.setStatus(400);
		return false;
	}
}
