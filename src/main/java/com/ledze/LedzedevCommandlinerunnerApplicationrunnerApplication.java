package com.ledze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class LedzedevCommandlinerunnerApplicationrunnerApplication {
    private static Logger logger = LoggerFactory.getLogger(LedzedevCommandlinerunnerApplicationrunnerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LedzedevCommandlinerunnerApplicationrunnerApplication.class, args);
    }

    @Bean
    CommandLineRunner cargaEquiposClasicos(LigaRepository ligaRepository) {
        logger.info("Cargando con CommandLineRunner");
        return x -> {
            logger.info("CommandLineRunner tiene como parámetros un arreglo de Strings: "+ Arrays.toString(x));
            Arrays.asList(
                    new EquipoDeFutbol("América"),
                    new EquipoDeFutbol("Cruz Azul"),
                    new EquipoDeFutbol("Chivas"),
                    new EquipoDeFutbol("Pumas")
            ).forEach(e -> {
                logger.info(e.toString());
                ligaRepository.save(e);
                    });
            logger.info("Carga inicial finalizada.");
        };
    };

    @Bean
    ApplicationRunner cargaEquiposDelNorte(LigaRepository ligaRepository){
        logger.info("Cargando con ApplicationRunner");
        return x -> {
            logger.info("ApplicationRunner tiene como parámetro un objeto ApplicationArguments");

            Arrays.asList(
                    new EquipoDeFutbol("Monterrey"),
                    new EquipoDeFutbol("Tigres"),
                    new EquipoDeFutbol("Xolos")
            ).forEach(e -> {
                logger.info(e.toString());
                ligaRepository.save(e);
            });
            logger.info("Carga equipos del norte finalizada");
        };
    };
}

@RepositoryRestResource
interface LigaRepository extends JpaRepository<EquipoDeFutbol, Long> {
    Collection<EquipoDeFutbol> findByNombre(@Param("n") String nombre);
}

@Entity
class EquipoDeFutbol {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    public EquipoDeFutbol() {
    }

    public EquipoDeFutbol(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "EquipoDeFutbol{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}