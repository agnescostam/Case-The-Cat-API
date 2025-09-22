package projetos_Agnes_Costa_Macedo.catapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import projetos_Agnes_Costa_Macedo.catapi.model.CatBreed;
import projetos_Agnes_Costa_Macedo.catapi.model.CatBreedApiResponse;
import projetos_Agnes_Costa_Macedo.catapi.model.CatImage;
import projetos_Agnes_Costa_Macedo.catapi.model.CatImageApiResponse;
import projetos_Agnes_Costa_Macedo.catapi.repository.CatBreedRepository;
import projetos_Agnes_Costa_Macedo.catapi.repository.CatImageRepository;

@Service
public class CatApiService {

    private static final Logger logger = LoggerFactory.getLogger(CatApiService.class);

    @Value("${catapi.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CatBreedRepository catBreedRepository;

    @Autowired
    private CatImageRepository catImageRepository;

    public List<CatBreed> fetchAndSaveBreeds() {
        logger.info("Iniciando fetch das raças da TheCatAPI");
        String url = "https://api.thecatapi.com/v1/breeds";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<CatBreedApiResponse[]> response = restTemplate.exchange(
            url, HttpMethod.GET, entity, CatBreedApiResponse[].class
        );

        if (response.getBody() == null) {
            logger.error("Erro ao fetch raças: resposta vazia");
            return new ArrayList<>();
        }
        logger.info("Fetch concluído: {} raças recebidas", response.getBody().length);

        List<CatBreed> breeds = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (CatBreedApiResponse apiBreed : response.getBody()) {
            CatBreed breed = new CatBreed();
            breed.setId(apiBreed.getId());
            breed.setName(apiBreed.getName());
            breed.setTemperament(apiBreed.getTemperament());
            breed.setOrigin(apiBreed.getOrigin());
            breeds.add(breed);
            catBreedRepository.save(breed);
            futures.add(CompletableFuture.runAsync(() -> fetchAndSaveImages(breed, "normal", null), executor));
        }

        // Buscar imagens gerais de chapéu e óculos
        futures.add(CompletableFuture.runAsync(() -> fetchAndSaveImages(null, "hat", 1), executor));
        futures.add(CompletableFuture.runAsync(() -> fetchAndSaveImages(null, "glasses", 4), executor));

        // Esperar todas as threads terminarem
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        return breeds;
    }

    private void fetchAndSaveImages(CatBreed breed, String type, Integer categoryId) {
        logger.debug("Buscando imagens para type: {}, breed: {}, categoryId: {}", 
            type, (breed != null ? breed.getId() : "geral"), categoryId);
        StringBuilder urlBuilder = new StringBuilder("https://api.thecatapi.com/v1/images/search?limit=3");
        if (breed != null) {
            urlBuilder.append("&breed_ids=").append(breed.getId());
        }
        if (categoryId != null) {
            urlBuilder.append("&category_ids=").append(categoryId);
        }
        String url = urlBuilder.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<CatImageApiResponse[]> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, CatImageApiResponse[].class
            );
            if (response.getBody() != null) {
                for (CatImageApiResponse apiImage : response.getBody()) {
                    CatImage image = new CatImage();
                    image.setId(apiImage.getId());
                    image.setUrl(apiImage.getUrl());
                    image.setBreed(breed);
                    catImageRepository.save(image);
                }
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar imagens: {}", e.getMessage());
        }
    }

    public List<CatBreed> getAllBreeds() {
        logger.info("Listando todas as raças");
        return catBreedRepository.findAll();
    }

    public CatBreed getBreedById(String id) {
        logger.info("Buscando raça por ID: {}", id);
        return catBreedRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Raça não encontrada"));
    }

    public List<CatBreed> getBreedsByTemperament(String temperament) {
        logger.info("Buscando raças por temperamento: {}", temperament);
        return catBreedRepository.findByTemperamentContainingIgnoreCase(temperament);
    }

    public List<CatBreed> getBreedsByOrigin(String origin) {
        logger.info("Buscando raças por origem: {}", origin);
        return catBreedRepository.findByOriginContainingIgnoreCase(origin);
    }
}