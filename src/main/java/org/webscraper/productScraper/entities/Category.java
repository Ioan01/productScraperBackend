package org.webscraper.productScraper.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryId", nullable = false)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String categoryName;



}
