package pl.solutions.software.sokolik.bartosz.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class KeycloakData {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("expires_in")
  private Long expiresIn;

  @JsonProperty("refresh_expires_in")
  private Long refreshExpiresIn;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("not-before-policy")
  private Long notBeforePolicy;

  @JsonProperty("session_state")
  private String sessionState;

  @JsonProperty("scope")
  private String scope;
}
