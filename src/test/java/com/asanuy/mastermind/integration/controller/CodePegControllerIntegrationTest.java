package com.asanuy.mastermind.integration.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8080")
public class CodePegControllerIntegrationTest {

    TestRestTemplate restTemplate = new TestRestTemplate();

    private String createURL(String uri) {
        return "http://localhost:8080" + uri;
    }

    @Test
    public void whenTestGet_thenReturnOk() {
        ResponseEntity<?> response =
                restTemplate.exchange(createURL("/games/codepegs/1"), HttpMethod.GET, new HttpEntity<>(null, new HttpHeaders()), String.class);

        String jsonResponse =
                "{\"id\":1,\"position\":1,\"pegColor\":\"YELLOW\",\"_links\":{\"self\":{\"href\":\"http://localhost:8080/games/codepegs/1\"}," +
                        "\"codePegs\":{\"href\":\"http://localhost:8080/games/codepegs?gameId=1\"}}}";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jsonResponse, response.getBody());
    }

    @Test
    public void whenTestGet_thenReturnNotFound() {
        ResponseEntity<?> response =
                restTemplate.exchange(createURL("/games/codepegs/999"), HttpMethod.GET, new HttpEntity<>(null, new HttpHeaders()), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void whenTestGetByGame_thenReturnOk() {
        ResponseEntity<?> response =
                restTemplate.exchange(createURL("/games/codepegs?gameId=1"), HttpMethod.GET, new HttpEntity<>(null, new HttpHeaders()), String.class);

        String jsonResponse =
                "{\"_embedded\":{\"codePegList\":[{\"id\":1,\"position\":1,\"pegColor\":\"YELLOW\",\"_links\":{\"self\":{\"href\":\"http://localhost:8080/games/codepegs/1\"}," +
                        "\"codePegs\":{\"href\":\"http://localhost:8080/games/codepegs?gameId=1\"}}}]},\"_links\":{\"self\":{\"href\":\"http://localhost:8080/games/codepegs?gameId=1\"}}}";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jsonResponse, response.getBody());
    }

    @Test
    public void whenTestGetByGame_thenReturnNotFound() {
        ResponseEntity<?> response =
                restTemplate.exchange(createURL("/games/codepegs?gameId=999"), HttpMethod.GET, new HttpEntity<>(null, new HttpHeaders()), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
