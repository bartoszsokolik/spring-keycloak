package pl.solutions.software.sokolik.bartosz.keycloak.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.KeycloakData;
import pl.solutions.software.sokolik.bartosz.keycloak.dto.UserCredentials;
import pl.solutions.software.sokolik.bartosz.keycloak.service.UserService;

@RestController
public class HelloController {

  private final UserService userService;

  public HelloController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/external")
  public String index() {
    return "external";
  }

  @GetMapping("/customers")
  public String customers() {
    return "customers";
  }

  @PostMapping("/token")
  public ResponseEntity<KeycloakData> getToken(@RequestBody UserCredentials userCredentials) {
    KeycloakData token = userService.getToken(userCredentials);
    return new ResponseEntity<>(token, HttpStatus.OK);
  }

  @PostMapping("/register")
  public void registerUser() {

  }
}
