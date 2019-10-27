package pl.solutions.software.sokolik.bartosz.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class UserDto {

  @JsonProperty("id")
  private String id;

  @JsonProperty("username")
  private String username;

  @JsonProperty("email")
  private String email;

  @JsonProperty("createdTimestamp")
  private Long createdTimestamp;

  @JsonProperty("enabled")
  private Boolean enabled;
}
