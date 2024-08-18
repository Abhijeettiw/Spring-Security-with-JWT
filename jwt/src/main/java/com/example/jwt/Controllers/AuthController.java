package com.example.jwt.Controllers;

import com.example.jwt.Entities.AppUsers;
import com.example.jwt.Entities.UserRoles;
import com.example.jwt.Login.LoginDto;
import com.example.jwt.Login.LoginResponseDto;
import com.example.jwt.Login.RegistrationDto;
import com.example.jwt.Repositories.AppUserRepo;
import com.example.jwt.Repositories.UserRolesRepo;
import com.example.jwt.Security.CustomAuthProvider;
import com.example.jwt.Security.JwtServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth/")
public class AuthController {
    @Autowired
    private AppUserRepo usersRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CustomAuthProvider authProvider;
    @Autowired
    private UserRolesRepo userRoles;
    @Autowired
    private JwtServices jwtServices;

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto){
        var authenticate = authProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));;
        if(authenticate.isAuthenticated()) {
            var user = usersRepo.findByUsername(loginDto.getUsername());
            String token = jwtServices.generateToken(user.get());
            var build = new LoginResponseDto();
            build.setToken(token);
            return new ResponseEntity<>(build,HttpStatus.OK);
        }else {
            var build = new LoginResponseDto();
            build.setToken("Invalid Credentials");
            return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("signup")
    private ResponseEntity<?> signup(@RequestBody RegistrationDto registrationDto) {
        try {
            AtomicReference<Boolean> userNameExists = new AtomicReference<>(false);
            StringBuffer message = new StringBuffer();
            var byUserName = usersRepo.findByUsername(registrationDto.getUsername());
            byUserName.ifPresent(u -> {
                userNameExists.set(true);
            });
            if (userNameExists.get()) {
                return new ResponseEntity<>("Username Already Exists !", HttpStatus.NOT_ACCEPTABLE);
            } else {
                Gson gson = new Gson();
                String dataJson = gson.toJson(registrationDto);
                var users = gson.fromJson(dataJson, new TypeToken<AppUsers>() {
                });
                users.setPassword(passwordEncoder.encode(users.getPassword()));
                String type = registrationDto.getRole();
                var userTypesMap = userRoles.findAll().stream().collect(Collectors.toMap(UserRoles::getId, Function.identity()));
                List<UserRoles> role = new ArrayList<>();
                Arrays.stream(type.split(",")).map(ut -> Long.parseLong(ut.trim()))
                        .filter(ut -> userTypesMap.containsKey(ut)).forEach(ut -> role.add(userTypesMap.get(ut)));
                users.setRoles(role);
                usersRepo.save(users);
                message.append("Registered Successfully");
                return new ResponseEntity<>("Registered Successfully", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
