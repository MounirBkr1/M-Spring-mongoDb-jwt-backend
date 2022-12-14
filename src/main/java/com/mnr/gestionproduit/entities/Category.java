package com.mnr.gestionproduit.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Category {

    //in mongodb preferable d utiliser string pour id
    @Id
    private String id;

    private String name;


    //@DBRef : ds document n enregistre que le id du produit
    @DBRef
    private Collection<Product> products =new ArrayList<>();

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
