package pl.solutions.software.sokolik.bartosz.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class UserCredentials {

  private String username;

  private String password;
}
