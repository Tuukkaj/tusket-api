package fi.tuukka.tusketapi.api.authentication;

import fi.tuukka.tusketapi.api.authentication.dto.AuthenticationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @PostMapping
    public String createToken(@RequestBody AuthenticationRequest request) {
        return
    }
}
