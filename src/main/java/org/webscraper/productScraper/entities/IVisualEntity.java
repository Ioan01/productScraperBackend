package org.webscraper.productScraper.entities;

import java.io.Serializable;
import java.net.URI;

public interface IVisualEntity extends Serializable {
    URI getImageUri();
    void setImage(Image save);
}
