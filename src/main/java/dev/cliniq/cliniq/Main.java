package dev.cliniq.cliniq;


import java.awt.EventQueue;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import dev.cliniq.cliniq.View.CliniQInterfaz;

@SpringBootApplication
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
			CliniQInterfaz cliniQInterfaz = context.getBean(CliniQInterfaz.class);
			cliniQInterfaz.setVisible(true);
		});
	}
	

}

