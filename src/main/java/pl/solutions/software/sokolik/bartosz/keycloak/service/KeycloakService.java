package pl.solutions.software.sokolik.bartosz.keycloak.service;

import io.reactivex.Single;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.KeycloakData;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface KeycloakService {

  @POST("token")
  @FormUrlEncoded
  Single<KeycloakData> getCredentials(
      @Field("username") String username,
      @Field("password") String password,
      @Field("client_id") String clientId,
      @Field("grant_type") String grantType,
      @Field("client_secret") String secret);

}
