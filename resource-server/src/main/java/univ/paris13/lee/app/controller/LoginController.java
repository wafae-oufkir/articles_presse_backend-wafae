package univ.paris13.lee.app.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import univ.paris13.lee.app.error.NoTokenException;

import java.util.Map;

import static univ.paris13.lee.app.controller.ControllerUtils.*;

@RestController
public class LoginController {

    @RequestMapping("/login")
    public ResponseEntity<Object> login() {
        return ResponseEntity.status(HttpStatus.FOUND).
                header(HttpHeaders.LOCATION, authorizeURL())
                .build();
    }

    @RequestMapping("/code")
    public ResponseEntity<Object> code(String code) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, authentication());
        final ResponseEntity<Map> response = new RestTemplate().exchange(tokenURL(code),
                HttpMethod.POST, new HttpEntity<>(headers), Map.class);

        final String token = (String) response.getBody().get("access_token");
        if (token == null){
            throw new NoTokenException("authentication failed");
        }
        ControllerUtils.setSessionAttribute("access_token", token);
        return ResponseEntity.status(HttpStatus.FOUND).
                header(HttpHeaders.LOCATION, "/index.html")
                .build();
    }
}
