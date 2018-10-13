package hu.tobias.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

@Named
@ApplicationScoped
public class ConfigBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Configuration cfg;

	public ConfigBean() {
	}

	@PostConstruct
	public void init() {
		cfg = new Configuration(Configuration.VERSION_2_3_28);

		WebappTemplateLoader templateLoader = new WebappTemplateLoader(
				(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext(),
				"WEB-INF/templates");
		templateLoader.setURLConnectionUsesCaches(false);
		templateLoader.setAttemptFileAccess(false);
		cfg.setTemplateLoader(templateLoader);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
		cfg.setLogTemplateExceptions(true);
		cfg.setWrapUncheckedExceptions(true);
	}

	public Configuration getCfg() {
		return cfg;
	}

	public void setCfg(Configuration cfg) {
		this.cfg = cfg;
	}

}
