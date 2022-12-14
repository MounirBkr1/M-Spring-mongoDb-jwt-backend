package com.mnr.gestionproduit.repositories;

import com.mnr.gestionproduit.entities.Category;
import com.mnr.gestionproduit.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface CategoryRepository extends MongoRepository<Category, String> {
}
