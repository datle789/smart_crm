package crm.demo.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crm.demo.Jwt.JwtTokenProvider;
import crm.demo.Payload.Request.LoginRequest;
import crm.demo.Payload.Request.SignupRequest;
import crm.demo.Payload.Response.JwtResponse;
import crm.demo.Payload.Response.MessageResponse;
import crm.demo.Security.CustomUserDetail;
import crm.demo.models.Erole;
import crm.demo.models.Roles;
import crm.demo.models.User;
import crm.demo.services.RoleService;
import crm.demo.services.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class UserController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  @Autowired
  private UserService userService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  // @PostMapping("/signup")
  // public ResponseEntity<?> registerUser(@RequestBody SignupRequest
  // signupRequest) {
  // if (userService.existByUserName(signupRequest.getUsername())) {
  // return ResponseEntity.badRequest().body(new MessageResponse("Error : Username
  // is already"));
  // }
  // if (userService.existByEmail(signupRequest.getEmail())) {
  // return ResponseEntity.badRequest().body(new MessageResponse("Error : Email is
  // already"));
  // }

  // User users = new User();
  // users.setUserName(signupRequest.getUsername());
  // users.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
  // users.setEmail(signupRequest.getEmail());
  // users.setName(signupRequest.getName());
  // users.setPhone(signupRequest.getPhone());
  // users.setStatus(1);
  // users.setCreatedAt();
  // Set<String> strRoles = signupRequest.getListRoles();
  // Set<Roles> listRoles = new HashSet<>();
  // if (strRoles == null) {
  // Roles userRoles = roleService.findByRoleName(Erole.ROLE_USER)
  // .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
  // listRoles.add(userRoles);
  // } else {
  // strRoles.forEach(role -> {
  // switch (role) {
  // case "admin":
  // Roles adminRoles = roleService.findByRoleName(Erole.ROLE_ADMIN)
  // .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
  // listRoles.add(adminRoles);
  // case "moderator":
  // Roles modRoles = roleService.findByRoleName(Erole.ROLE_MODERATOR)
  // .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
  // listRoles.add(modRoles);
  // case "user":
  // Roles userRoles = roleService.findByRoleName(Erole.ROLE_USER)
  // .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
  // listRoles.add(userRoles);
  // }
  // });
  // }
  // users.setListRoles(listRoles);
  // userService.saveOrUpdate(users);
  // return ResponseEntity.ok(new MessageResponse("User registered
  // successfully"));
  // }

  @PostMapping("/signin")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      CustomUserDetail customUserDetails = (CustomUserDetail) authentication.getPrincipal();
      String jwt = jwtTokenProvider.genarateToken(customUserDetails);
      List<String> listRoles = customUserDetails.getAuthorities().stream()
          .map(item -> item.getAuthority()).collect(Collectors.toList());
      return ResponseEntity.ok(new JwtResponse(
          jwt,
          customUserDetails.getId(),
          customUserDetails.getUsername(),
          customUserDetails.getName(),
          customUserDetails.getEmail(),
          customUserDetails.getPhone(),
          customUserDetails.getCrms(),
          listRoles));
    } catch (Exception e) {
      return ResponseEntity.status(403).body("Authentication failed. Check your credentials.");
    }
  }
}
