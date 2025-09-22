package projetos_Agnes_Costa_Macedo.catapi.model;

public class CatBreedApiResponse {
    private String id;
    private String name;
    private String temperament;
    private String origin;

    // Getters e setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTemperament() { return temperament; }
    public void setTemperament(String temperament) { this.temperament = temperament; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
}