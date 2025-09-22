package projetos_Agnes_Costa_Macedo.catapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projetos_Agnes_Costa_Macedo.catapi.model.CatBreed;

public interface CatBreedRepository extends JpaRepository<CatBreed, String> {
    List<CatBreed> findByTemperamentContainingIgnoreCase(String temperament);
    List<CatBreed> findByOriginContainingIgnoreCase(String origin);
}