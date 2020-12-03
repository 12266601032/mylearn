package com.example.jsonschema;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.json.JsonReader;
import javax.json.JsonValue;
import org.junit.Test;
import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.api.JsonValidationService;
import org.leadpony.justify.api.ProblemHandler;

public class JustifyTests {

  @Test
  public void name() {
    JsonValidationService service = JsonValidationService.newInstance();

    // Reads the JSON schema
    JsonSchema schema = service.readSchema(getClass().getClassLoader().getResourceAsStream("product.schema.json"));

    // Problem handler which will print problems found.
    ProblemHandler handler = service.createProblemPrinter(System.out::println);

    // Reads the JSON instance by JsonReader
    try (JsonReader reader = service.createReader(getClass().getClassLoader().getResourceAsStream("product.json"), schema, handler)) {
      JsonValue value = reader.readValue();
      // Do something useful here
    }
  }
}
