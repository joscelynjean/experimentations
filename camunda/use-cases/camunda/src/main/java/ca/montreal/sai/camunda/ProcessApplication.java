package ca.montreal.sai.camunda;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starting point for Camunda and the bpmn.
 * Using the Greenfield Stack, recommandation of Camunda
 * @see https://camunda.com/best-practices/using-a-greenfield-stack/
 * 
 * @author Joscelyn Jean
 *
 */
@SpringBootApplication
@EnableProcessApplication
public class ProcessApplication {

	private static Logger logger = LoggerFactory.getLogger(ProcessApplication.class);

	
	/**
	 * Main function. Used to start the boot strap application based on Camunda
	 * @see EnableProcessApplication annotation
	 * @param args
	 */
	public static void main(String... args) {
		SpringApplication application = new SpringApplication(ProcessApplication.class);
		application.setBannerMode(Mode.OFF);
        application.run(args);
	}
	
}
