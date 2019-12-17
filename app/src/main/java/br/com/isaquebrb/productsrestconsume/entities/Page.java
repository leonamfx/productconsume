package br.com.isaquebrb.productsrestconsume.entities;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page implements Serializable {

    //Generic object
    private List<Object> content = null;
    private Pageable pageable;
    private Integer totalPages;
    private Integer totalElements;
    private Boolean last;
    private Integer size;
    private Integer number;
    private Sort sort;
    private Boolean first;
    private Integer numberOfElements;
    private Boolean empty;
}

@Getter
@Setter
class Pageable implements Serializable {

    private Sort sort;
    private Integer offset;
    private Integer pageSize;
    private Integer pageNumber;
    private Boolean paged;
    private Boolean unpaged;
}

@Getter
@Setter
class Sort implements Serializable {

    private Boolean sorted;
    private Boolean unsorted;
    private Boolean empty;
}