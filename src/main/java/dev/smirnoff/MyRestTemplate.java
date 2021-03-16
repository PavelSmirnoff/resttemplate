package dev.smirnoff;

import dev.smirnoff.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author pavelsmirnov
 * @version 1.0
 * дата создания 16.03.2021
 */
public class MyRestTemplate {

    static final String URL_API = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) {

        HttpHeaders headers = new HttpHeaders();

        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("my_other_key", "my_other_value");

        HttpEntity<User[]> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> response = restTemplate.exchange(URL_API, HttpMethod.GET, entity, User[].class);

        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Satus Code: " + statusCode);

        HttpHeaders httpHeaders = response.getHeaders();
        System.out.println("Response Headers: " + httpHeaders);

        String sessionId = httpHeaders.getFirst("set-cookie");
        System.out.println(sessionId);

        if (statusCode == HttpStatus.OK) {
            User[] list = response.getBody();
            Arrays.stream(list).forEach(System.out::println);
        }

        User userThree = new User(3L,"James","Brown",(byte) 33);

    }
}
