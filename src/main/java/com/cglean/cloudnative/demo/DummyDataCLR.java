package com.cglean.cloudnative.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.cglean.cloudnative.demo.server.repository.ShowRepository;

@Component
public class DummyDataCLR  implements CommandLineRunner {
	
	@Autowired
	private ShowRepository showRepository;

	@Override
	public void run(String... arg0) throws Exception {

		showRepository.findAll().forEach(System.out::println);
		showRepository.findByTitleIgnoreCase("Friends", new PageRequest(1, 10)).forEach(System.out::println);
	}
	
}
