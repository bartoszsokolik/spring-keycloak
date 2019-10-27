package pl.solutions.software.sokolik.bartosz.keycloak.service;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.List;
import javax.ws.rs.core.Response;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.KeycloakData;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.UserCredentials;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.CreateUserDto;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.UserDto;

@Service
public class UserService {

  private static final String GRANT_TYPE = "password";

  private final KeycloakService keycloakService;
  private final RealmResource realmResource;
  private final String clientId;
  private final String secret;

  public UserService(KeycloakService keycloakService, RealmResource realmResource,
      @Value("${keycloak.resource}") String clientId,
      @Value("${keycloak.credentials.secret}") String secret) {

    this.keycloakService = keycloakService;
    this.realmResource = realmResource;
    this.clientId = clientId;
    this.secret = secret;
  }

  public KeycloakData getToken(UserCredentials userCredentials) {

    return keycloakService
        .getCredentials(userCredentials.getUsername(), userCredentials.getPassword(),
            clientId, GRANT_TYPE, secret).blockingGet();
  }

  public UserDto createUser(CreateUserDto dto) {
    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
    credentialRepresentation.setValue(dto.getPassword());
    credentialRepresentation.setTemporary(FALSE);

    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setUsername(dto.getUsername());
    userRepresentation.setEmail(dto.getEmail());
    userRepresentation.setCredentials(List.of(credentialRepresentation));
    userRepresentation.setEnabled(TRUE);

    Response response = realmResource.users().create(userRepresentation);

    String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

    RoleRepresentation role = realmResource.roles().get("user").toRepresentation();
    UserResource userResource = realmResource.users().get(userId);
    userResource.roles().realmLevel().add(List.of(role));

    UserRepresentation user = userResource.toRepresentation();

    return new UserDto(user.getId(), user.getUsername(), user.getEmail(),
        user.getCreatedTimestamp(), user.isEnabled());
  }
}
