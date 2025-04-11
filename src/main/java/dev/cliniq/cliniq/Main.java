package dev.cliniq.cliniq;


import java.awt.EventQueue;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dev.cliniq.cliniq.View.CliniQLoginUI;

@SpringBootApplication
@EnableJpaRepositories("dev.cliniq.cliniq.Repository")
@EntityScan("dev.cliniq.cliniq.Model")
@ComponentScan("dev.cliniq.cliniq")
public class Main {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = 
			new SpringApplicationBuilder(Main.class)
				.headless(false)
				.web(WebApplicationType.NONE)
				.run(args);
		//Ejecutar el programa
		EventQueue.invokeLater(() -> {
			//Obtenemos el objeto frame atraves de spring
			CliniQLoginUI login = context.getBean(CliniQLoginUI.class);
			login.setVisible(true);
		});
	}
	

}

 