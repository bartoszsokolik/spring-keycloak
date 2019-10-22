package pl.solutions.software.sokolik.bartosz.keycloak.service;

import java.io.IOException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.stereotype.Service;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.KeycloakData;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.UserCredentials;
import retrofit2.Call;
import retrofit2.Response;

@Service
public class UserService {

  private final KeycloakService keycloakService;

  public UserService(KeycloakService keycloakService) {
    this.keycloakService = keycloakService;
  }

  public KeycloakData getToken(UserCredentials userCredentials) {

    Call<KeycloakData> password = keycloakService
        .getCredentials(userCredentials.getUsername(), userCredentials.getPassword(), "login-app",
            "password");
    Response<KeycloakData> execute;

    try {
      execute = password.execute();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }

    return execute.body();
  }

//  private UsersResource getKeycloakUserResource() {
//
//    Keycloak kc = KeycloakBuilder.builder().serverUrl(AUTHURL).realm("master").username("admin").password("admin")
//        .clientId("admin-cli").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
//        .build();
//
//    RealmResource realmResource = kc.realm(REALM);
//    UsersResource userRessource = realmResource.users();
//
//    return userRessource;
//  }
}
