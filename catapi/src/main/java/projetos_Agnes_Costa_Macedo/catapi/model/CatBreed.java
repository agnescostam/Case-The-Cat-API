package projetos_Agnes_Costa_Macedo.catapi.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class CatBreed {
    @Id
    private String id;
    private String name;
    private String temperament;
    private String origin;

    @OneToMany(mappedBy = "breed")
    private List<CatImage> images;

    // Getters e setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTemperament() { return temperament; }
    public void setTemperament(String temperament) { this.temperament = temperament; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public List<CatImage> getImages() { return images; }
    public void setImages(List<CatImage> images) { this.images = images; }
}