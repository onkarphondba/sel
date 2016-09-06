package com.cglean.cloudnative.demo.client.ui.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cglean.cloudnative.demo.client.model.Show;

/**
 * ShowController
 *
 * This is the MVC controller for the application. All UI HTTP requests get
 * here. We're using Thymeleaf as the template engine.
 *
 *
 * @author mborges
 *
 */
@Controller
public class ShowController {

	private Log log = LogFactory.getLog(ShowController.class);

	@Autowired
	private ShowService showService;

	/**
	 * INDEX
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/")
	public String index(Model model) throws Exception {
		addAppEnv(model);
		return "index";
	}

	/**
	 * Core
	 *
	 * Action to initiate shutdown of the system. In CF, the application
	 * <em>should</em> restart. In other environments, the application runtime
	 * will be shut down.
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/core", method = RequestMethod.GET)
	public String kill(@RequestParam(value = "doit", required = false) boolean doit, Model model) throws Exception {

		addAppEnv(model);

		if (doit) {
			model.addAttribute("killed", true);
			log.warn("*** The system is shutting down. ***");
			Runnable killTask = () -> {
				try {
					String name = Thread.currentThread().getName();
					log.warn("killing shortly " + name);
					TimeUnit.SECONDS.sleep(5);
					log.warn("killed " + name);
					System.exit(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
			new Thread(killTask).start();
		}

		return "core";

	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/ssh-file", method = RequestMethod.GET)
	public String writeFile(Model model) throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy HH:mm:ss");
		Date date = new Date();
		File f = new File("ers-ssh-demo.log");
		FileWriter fw = new FileWriter(f, true);
		Writer w = new BufferedWriter(fw);
		w.write(dateFormat.format(date) + "\n");
		w.flush();

		model.addAttribute("ssh_file", f.getAbsoluteFile());
		addAppEnv(model);

		return "core";

	}

	/**
	 * SERVICES
	 *
	 * @param model
	 *            The model for this action.
	 * @return The path to the view.
	 */
	@RequestMapping(value = "/services", method = RequestMethod.GET)
	public String shows(Model model) throws Exception {

		model.addAttribute("shows", showService.getShows());

		addAppEnv(model);
		return "services";
	}

	/**
	 * SERVICES - clean DB
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/clean", method = RequestMethod.GET)
	public String clean(Model model) throws Exception {

		showService.deleteAll();
		return shows(model);
	}


	/**
	 * SERVICES - Add Show
	 *
	 * NOTE: this method chains (calls) the "shows" method so it returns the
	 * services template with the updated show list.
	 *
	 * TODO:
	 * - Turn this this into REST call
	 *
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param model
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add-show", method = RequestMethod.POST)
	public String addShow(@RequestParam("title") String title, @RequestParam("episodes") String episodes,
			@RequestParam("airDate") String airDate, Model model) throws Exception {

		Show show = new Show();
		show.setTitle(title);
		show.setEpisodes(episodes);
		show.setAirDate(airDate);

		showService.add(show);
		model.addAttribute("shows", showService.getShows());

		addAppEnv(model);
		return "services";
	}

	/**
	 * BLUEGREEN
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bluegreen")
	public String bluegreen(Model model) throws Exception {

		for (String key : System.getenv().keySet()) {
			System.out.println(key + ":" + System.getenv(key));
		}

		addAppEnv(model);

		return "bluegreen";
	}

	///////////////////////////////////////
	// Helper Methods
	///////////////////////////////////////

	private void addAppEnv(Model model) throws Exception {

		Map<String, Object> modelMap = showService.addAppEnv();
		model.addAllAttributes(modelMap);
	}

}
