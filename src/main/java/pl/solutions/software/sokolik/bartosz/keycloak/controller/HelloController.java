package pl.solutions.software.sokolik.bartosz.keycloak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/external")
  public String index() {
    return "external";
  }

  @GetMapping("/customers")
  public String customers() {
    return "customers";
  }
}
