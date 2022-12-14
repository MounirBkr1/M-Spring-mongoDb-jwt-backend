package com.mnr.gestionproduit;

import com.mnr.gestionproduit.entities.Category;
import com.mnr.gestionproduit.entities.Product;
import com.mnr.gestionproduit.repositories.CategoryRepository;
import com.mnr.gestionproduit.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.stream.Stream;

@SpringBootApplication
public class GestionProduitApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionProduitApplication.class, args);
    }


    @Bean
    CommandLineRunner start(CategoryRepository categoryRepository, ProductRepository productRepository){
        return args->{

            categoryRepository.deleteAll();
            Stream.of("c1 ordinateur","c2 imprimante").forEach(c->{
                categoryRepository.save(new Category(c.split(" ")[0],c.split(" ")[1],new ArrayList<>()));
            });

            categoryRepository.findAll().forEach(System.out::println);

            productRepository.deleteAll();
            Category c1= categoryRepository.findById("c1").get();
            Stream.of("p1","p2","p3").forEach(p->{
                productRepository.save(new Product(null,p,Math.random()*1000,c1));
                    }
            );

            Category c2= categoryRepository.findById("c2").get();
            Stream.of("p5","p6").forEach(p->{
                        productRepository.save(new Product(null,p,Math.random()*1000,c2));
                    }
            );
        };
    }
}
