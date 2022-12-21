package org.webscraper.productScraper.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URI;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
public class Store implements IVisualEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Long id;


    @Column(unique = true)
    private String storeName;

    private URI webSite;

    @JsonIgnore
    @Transient
    private URI imageUri;


    @JsonIgnore
    @Setter
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;



}
