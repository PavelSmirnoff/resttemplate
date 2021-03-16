package dev.smirnoff;

import dev.smirnoff.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author pavelsmirnov
 * @version 1.0
 * дата создания 16.03.2021
 */
public class MyRestTemplate {

    static final String URL_API = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) {

        //GET
        HttpHeaders headers = new HttpHeaders();

        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User[]> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> response = restTemplate.exchange(URL_API, HttpMethod.GET, entity, User[].class);

        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Satus Code: " + statusCode);

        HttpHeaders httpHeaders = response.getHeaders();
        System.out.println("Response Headers: " + httpHeaders);


        String sessionId = response.getHeaders().getFirst("Set-Cookie");
        headers.set("Cookie", sessionId);

        if (statusCode == HttpStatus.OK) {
            Arrays.stream(Objects.requireNonNull(response.getBody())).forEach(System.out::println);
        }

        //POST
        User userThree = new User();
        userThree.setId(3L);
        userThree.setName("James");
        userThree.setLastName("Brown");
        userThree.setAge((byte)3);

        HttpEntity<User> requestBody = new HttpEntity<>(userThree, headers);

        ResponseEntity<String> postResult = restTemplate.exchange(URL_API, HttpMethod.POST, requestBody, String.class);

        System.out.println("Status code:" + postResult.getStatusCode());
        if (postResult.getStatusCode() == HttpStatus.OK) {
            System.out.println(postResult.getBody());
        }

        //PUT
        userThree.setName("Thomas");
        userThree.setLastName("Shelby");

        ResponseEntity<String> putResult = restTemplate.exchange(URL_API, HttpMethod.PUT, requestBody, String.class);
        System.out.println("Status code:" + putResult.getStatusCode());
        if (putResult.getStatusCode() == HttpStatus.OK) {
            System.out.println(putResult.getBody());
        }

        //DELETE
        ResponseEntity<String> deleteResult = restTemplate.exchange(URL_API+"/3", HttpMethod.DELETE, requestBody, String.class);
        System.out.println("Status code:" + deleteResult.getStatusCode());
        if (deleteResult.getStatusCode() == HttpStatus.OK) {
            System.out.println(deleteResult.getBody());
        }

        System.out.println("Итоговый ответ: " + postResult.getBody()+putResult.getBody()+deleteResult.getBody());
    }
}
