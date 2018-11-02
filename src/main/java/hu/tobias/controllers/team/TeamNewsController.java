package hu.tobias.controllers.team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Leader;
import hu.tobias.entities.News;
import hu.tobias.services.dao.NewsDao;

@Named(value = "teamNews")
@ViewScoped
public class TeamNewsController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private NewsDao newsService;

	@Inject
	private TeamController teamController;

	private List<News> news = new ArrayList<News>();
	private News editedNews = new News();
	private News newNews = new News();

	public TeamNewsController() {
	}

	@PostConstruct
	public void init() {
		loadData();
	}

	public void loadData() {
		news = newsService.findAll();
	}

	public TeamController getTeamController() {
		return teamController;
	}

	public void setTeamController(TeamController teamController) {
		this.teamController = teamController;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	public News getEditedNews() {
		return editedNews;
	}

	public void setEditedNews(News editedNews) {
		this.editedNews = editedNews;
	}

	public News getNewNews() {
		return newNews;
	}

	public void setNewNews(News newNews) {
		this.newNews = newNews;
	}

	public boolean setForNewNews(Leader l) {
		newNews = new News(l);
		return true;
	}

	public boolean setForEditNews(News n) {
		editedNews = n;
		return true;
	}

	public void saveNews(News n) {
		if (n.getId() == null)
			newsService.create(n);
		else
			newsService.update(n);
		loadData();
	}

	public void deleteNews(News n) {
		news.remove(n);
		newsService.delete(n);
	}

	public List<News> getActualNews() {
		List<News> result = new ArrayList<News>();
		for (News n : news) {
			if (n.getExpire().after(new Date()))
				result.add(n);
		}
		return result;
	}

	public List<News> getExpiredNews() {
		List<News> result = new ArrayList<News>();
		for (News n : news) {
			if (n.getExpire().before(new Date()))
				result.add(n);
		}
		return result;
	}

}
