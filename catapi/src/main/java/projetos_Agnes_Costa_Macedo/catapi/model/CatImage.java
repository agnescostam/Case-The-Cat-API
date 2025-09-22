package projetos_Agnes_Costa_Macedo.catapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CatImage {
    @Id
    private String id;
    private String url;

    @ManyToOne(optional = true)
    @JoinColumn(name = "breed_id")
    private CatBreed breed;

    // Getters e setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public CatBreed getBreed() { return breed; }
    public void setBreed(CatBreed breed) { this.breed = breed; }
}