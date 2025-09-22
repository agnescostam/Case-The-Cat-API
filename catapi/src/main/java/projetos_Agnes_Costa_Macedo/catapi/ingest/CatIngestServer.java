package projetos_Agnes_Costa_Macedo.catapi.ingest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import projetos_Agnes_Costa_Macedo.catapi.service.CatApiService;

@SpringBootApplication
public class CatIngestServer {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(CatIngestServer.class, args)) {
            CatApiService catApiService = ctx.getBean(CatApiService.class);
            catApiService.fetchAndSaveBreeds();
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}