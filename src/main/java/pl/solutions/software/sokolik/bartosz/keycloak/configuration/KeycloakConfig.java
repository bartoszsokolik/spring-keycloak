package pl.solutions.software.sokolik.bartosz.keycloak.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.solutions.software.sokolik.bartosz.keycloak.service.KeycloakService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class KeycloakConfig {

  private final String authUrl;
  private final String authServerUrl;
  private final String realm;
  private final String resource;
  private final String secret;
  private final String username;
  private final String password;

  public KeycloakConfig(@Value("${external.keycloak.url}") String authUrl,
      @Value("${keycloak.auth-server-url}") String authServerUrl,
      @Value("${keycloak.realm}") String realm,
      @Value("${keycloak.resource}") String resource,
      @Value("${keycloak.credentials.secret}") String secret,
      @Value("${external.keycloak.username}") String username,
      @Value("${external.keycloak.password}") String password) {
    this.authUrl = authUrl;
    this.authServerUrl = authServerUrl;
    this.realm = realm;
    this.resource = resource;
    this.secret = secret;
    this.username = username;
    this.password = password;
  }

  @Bean
  public KeycloakService keycloakService(ObjectMapper objectMapper) {
    OkHttpClient okHttpClient = new OkHttpClient();
    return new Retrofit.Builder()
        .baseUrl(authUrl)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .client(okHttpClient)
        .build()
        .create(KeycloakService.class);
  }

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(authServerUrl)
        .realm(realm)
        .username(username)
        .password(password)
        .clientId(resource)
        .clientSecret(secret)
        .resteasyClient(new ResteasyClientBuilder()
            .connectionPoolSize(10)
            .build())
        .build();
  }

  @Bean
  public RealmResource realmResource(Keycloak keycloak) {
    return keycloak.realm(realm);
  }


}
