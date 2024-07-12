package com.desafio;

import com.desafio.procesos.ConsumirGutendex;
import com.desafio.repository.AutorRepository;
import com.desafio.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumoApiLibrosApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ConsumoApiLibrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("####Se inicio correctamente");
		ConsumirGutendex consumirGutendex = new ConsumirGutendex(libroRepository, autorRepository);
		consumirGutendex.ejecutaMenu();
	}
}
