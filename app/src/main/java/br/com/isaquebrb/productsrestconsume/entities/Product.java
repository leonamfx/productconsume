package br.com.isaquebrb.productsrestconsume.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;

}
