package com.cglean.cloudnative.demo.server;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cglean.cloudnative.demo.PcfErsDemo1Application;
import com.cglean.cloudnative.demo.server.model.Show;
import com.cglean.cloudnative.demo.server.repository.ShowRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PcfErsDemo1Application.class)
@WebAppConfiguration
public class PcfErsDemo1ApplicationTests {

	@Autowired
	private ShowRepository showRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void showRepository() throws ParseException {

		showRepository.deleteAll();

		Show a1 = new Show();
		a1.setTitle("Friends");
		a1.setEpisodes("2");
		showRepository.save(a1);

		a1 = new Show();
		a1.setTitle("Shades of Blue");
		a1.setEpisodes("100");
		showRepository.save(a1);

		List<Show> shows = showRepository.findAll();
		assertThat(shows.size(), is(2));

	}

}
