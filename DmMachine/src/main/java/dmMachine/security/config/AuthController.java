package dmMachine.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dmMachine.security.model.JwtRequest;
import dmMachine.security.model.JwtResponse;
import dmMachine.security.principle.JwtHelper;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", methods  = { RequestMethod.GET, RequestMethod.POST }) // or specify your allowed origins and methods
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired(required = true)
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = helper.generateToken(userDetails);
            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .userName(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}


