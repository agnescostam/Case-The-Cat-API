package projetos_Agnes_Costa_Macedo.catapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projetos_Agnes_Costa_Macedo.catapi.model.CatImage;

public interface CatImageRepository extends JpaRepository<CatImage, String> {
}