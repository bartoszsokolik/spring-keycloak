package pl.solutions.software.sokolik.bartosz.keycloak.service;

import java.io.IOException;
import java.util.List;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.KeycloakData;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.UserCredentials;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.UserDto;
import retrofit2.Call;
import retrofit2.Response;

@Service
public class UserService {

  private final KeycloakService keycloakService;
  private final RealmResource realmResource;

  public UserService(KeycloakService keycloakService, RealmResource realmResource) {
    this.keycloakService = keycloakService;
    this.realmResource = realmResource;
  }

  public KeycloakData getToken(UserCredentials userCredentials) {

    Call<KeycloakData> password = keycloakService
        .getCredentials(userCredentials.getUsername(), userCredentials.getPassword(), "login-app",
            "password", "fa1b87f7-6903-4bfd-b59a-2f85ba744717");
    Response<KeycloakData> execute;

    try {
      execute = password.execute();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }

    return execute.body();
  }

  public void createUser(UserDto dto) {
    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
    credentialRepresentation.setValue(dto.getPassword());

    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setUsername(dto.getUsername());
    userRepresentation.setEmail(dto.getEmail());
    userRepresentation.setCredentials(List.of(credentialRepresentation));

    realmResource.users().create(userRepresentation);
  }
}
