package hu.tobias.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import hu.tobias.entities.News;
import hu.tobias.entities.Person;
import hu.tobias.services.dao.NewsDao;
import hu.tobias.services.dao.PersonDao;

@Named(value = "mainPage")
@RequestScoped
public class MainPageController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private NewsDao newsService;
	@EJB
	private PersonDao personService;

	private List<News> actualNews = new ArrayList<News>();
	// after the new GodOfWar
	private List<Person> happyBois = new ArrayList<Person>();

	public MainPageController() {
	}

	@PostConstruct
	public void init() {
	}

	public void loadData() {
		actualNews = newsService.findActualNews();
		happyBois = personService.findAllHappyBois();
	}

	public List<News> getActualNews() {
		return actualNews;
	}

	public void setActualNews(List<News> actualNews) {
		this.actualNews = actualNews;
	}

	public List<Person> getHappyBois() {
		return happyBois;
	}

	public void setHappyBois(List<Person> happyBois) {
		this.happyBois = happyBois;
	}

}
