package pl.solutions.software.sokolik.bartosz.keycloak.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.solutions.software.sokolik.bartosz.keycloak.service.KeycloakService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class KeycloakConfig {

  private final String url;

  public KeycloakConfig(@Value("${external.keycloak.url}") String url) {
    this.url = url;
  }

  @Bean
  public KeycloakService keycloakService(ObjectMapper objectMapper) {
    OkHttpClient okHttpClient = new OkHttpClient();
    return new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .client(okHttpClient)
        .build()
        .create(KeycloakService.class);
  }


}
