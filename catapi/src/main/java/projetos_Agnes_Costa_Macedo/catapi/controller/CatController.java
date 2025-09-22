package projetos_Agnes_Costa_Macedo.catapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projetos_Agnes_Costa_Macedo.catapi.model.CatBreed;
import projetos_Agnes_Costa_Macedo.catapi.service.CatApiService;

@RestController
@RequestMapping("/api")
public class CatController {

    @Autowired
    private CatApiService catApiService;

    @GetMapping("/breeds")
    public List<CatBreed> getAllBreeds() {
        return catApiService.getAllBreeds();
    }

    @GetMapping("/breeds/{id}")
    public CatBreed getBreedById(@PathVariable String id) {
        return catApiService.getBreedById(id);
    }

    @GetMapping("/breeds/temperament/{temperament}")
    public List<CatBreed> getBreedsByTemperament(@PathVariable String temperament) {
        return catApiService.getBreedsByTemperament(temperament);
    }

    @GetMapping("/breeds/origin/{origin}")
    public List<CatBreed> getBreedsByOrigin(@PathVariable String origin) {
        return catApiService.getBreedsByOrigin(origin);
    }
}