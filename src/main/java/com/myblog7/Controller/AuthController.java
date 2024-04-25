package com.myblog7.Controller;
import com.myblog7.Entity.User;
import com.myblog7.Payload.JWTAuthResponse;
import com.myblog7.Payload.LoginDto;
import com.myblog7.Payload.SignUpDto;
import com.myblog7.Repository.UserRepository;
import com.myblog7.Security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    //this will help to Authentication and Authorization functionalities for you application
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //Http://localhost:8080/api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate
    (new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
        //return new ResponseEntity<>("User signed-in successfully!.",HttpStatus.OK);
}
    //Http://localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?>registerUser(@RequestBody SignUpDto signUpDto){
        System.out.println(signUpDto.getUsername());
        System.out.println(signUpDto.getName());
    Boolean emailExist = userRepository.existsByEmail(signUpDto.getEmail());
    if(emailExist){return new ResponseEntity<>("Email Id Already Exist",HttpStatus.BAD_REQUEST);}

    Boolean username = userRepository.existsByUsername(signUpDto.getUsername());
    if(username){return new ResponseEntity<>("Username already Exist",HttpStatus.BAD_REQUEST);}

        User user = mapToEntity(signUpDto);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        User SavedUser = userRepository.save(user);
        return new ResponseEntity<>("User is Registered", HttpStatus.CREATED);
    }

    private User mapToEntity(SignUpDto signUpDto){
        User user = modelMapper.map(signUpDto, User.class);
        return user;
    }
    private SignUpDto mapToDto(User user){
        SignUpDto signUpDto = modelMapper.map(user, SignUpDto.class);
        return signUpDto;
    }
}
