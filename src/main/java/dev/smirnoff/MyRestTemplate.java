package dev.smirnoff;

import dev.smirnoff.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author pavelsmirnov
 * @version 1.0
 * дата создания 16.03.2021
 */
public class MyRestTemplate {

    static final String URL_API = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) {

        //GET
        HttpHeaders headersGet = new HttpHeaders();

        headersGet.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headersGet.setContentType(MediaType.APPLICATION_JSON);
        headersGet.set("my_other_key", "my_other_value");

        HttpEntity<User[]> entity = new HttpEntity<>(headersGet);

        RestTemplate restTemplateGet = new RestTemplate();
        ResponseEntity<User[]> response = restTemplateGet.exchange(URL_API, HttpMethod.GET, entity, User[].class);

        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Satus Code: " + statusCode);

        HttpHeaders httpHeaders = response.getHeaders();
        System.out.println("Response Headers: " + httpHeaders);

        String sessionId = response.getHeaders().getFirst("Set-Cookie");
        System.out.println(sessionId);

        if (statusCode == HttpStatus.OK) {
            User[] list = response.getBody();
            Arrays.stream(list).forEach(System.out::println);
        }

        //POST
        HttpHeaders headersPost = new HttpHeaders();
        headersPost.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headersPost.setContentType(MediaType.APPLICATION_JSON);
        headersPost.set("Cookie", sessionId);

        User userThree = new User();
        userThree.setId(3L);
        userThree.setName("James");
        userThree.setLastName("Brown");
        userThree.setAge((byte)3);

        HttpEntity<User> requestBody = new HttpEntity<>(userThree, headersPost);

        RestTemplate restTemplatePost = new RestTemplate();
        ResponseEntity<String> postResult = restTemplatePost.exchange(URL_API, HttpMethod.POST, requestBody, String.class);

        System.out.println("Status code:" + postResult.getStatusCode());
        if (postResult.getStatusCode() == HttpStatus.OK) {
            String u = postResult.getBody();
            System.out.println(u.toString());
        }
    }
}
