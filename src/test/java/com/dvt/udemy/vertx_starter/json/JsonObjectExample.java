package com.dvt.udemy.vertx_starter.json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonObjectExample {

  @Test
  void jsonObjectCanBeMapped() {
    final JsonObject myJsonObject = new JsonObject();
    myJsonObject.put("id", 1);
    myJsonObject.put("name", "Alice");
    myJsonObject.put("loves_vertx", true);

    final String encoded = myJsonObject.encode();
 //old method
    //    assertEquals("{\"id\":1,\"name\":\"Alice\",\"loves_vertx\":true}", encoded);

    // new method to avoid issues with formatting differences
    String expected = """
      {"id":1,"name":"Alice","loves_vertx":true}""".trim();
    assertEquals(expected, encoded);

    final JsonObject decodedJsonObject = new JsonObject(encoded);
    assertEquals(myJsonObject, decodedJsonObject);
  }

  @Test
  void jsonObjectCanBeCreatedFromMap() {
    final Map<String, Object> myMap = Map.<String, Object>of("id", 1, "name", "Alice", "loves_vertx", true);

    final JsonObject asJsonObject = new JsonObject(myMap);
    assertEquals(myMap, asJsonObject.getMap());
    assertEquals(1, asJsonObject.getInteger("id"));
    assertEquals("Alice", asJsonObject.getString("name"));
    assertTrue(asJsonObject.getBoolean("loves_vertx"));

  }

  @Test
  void jsonArrayCanBeMapped() {
    final JsonArray myJsonArray = new JsonArray();
    myJsonArray.add(new JsonObject().put("id", 1)).add(new JsonObject().put("id", 2)).add(new JsonObject().put("id", 3)).add("randomValue");
  String expected = """
      [{"id":1},{"id":2},{"id":3},"randomValue"]""".trim();
    assertEquals(expected, myJsonArray.encode());
  }

  @Test
  void canMapJavaObjects() {
    final Person person = new Person(1, "Alice", true);
    final JsonObject alice = JsonObject.mapFrom(person);

    assertEquals(person.id(), alice.getInteger("id"));
    assertEquals(person.name(), alice.getString("name"));
    assertEquals(person.lovesVertx(), alice.getBoolean("lovesVertx"));

    final Person person2 = alice.mapTo(Person.class);
    assertEquals(person, person2); // java 21 record equality


    // This is an old Java
    assertEquals(person.id(), person2.id());
    assertEquals(person.name(), person2.name());
    assertEquals(person.lovesVertx(), person2.lovesVertx());
  }

}
