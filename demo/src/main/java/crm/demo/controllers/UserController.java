package crm.demo.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crm.demo.Dto.UserDto;
import crm.demo.Jwt.JwtTokenProvider;
import crm.demo.Payload.Request.LoginRequest;
import crm.demo.Payload.Request.SignupRequest;
import crm.demo.Payload.Response.JwtResponse;
import crm.demo.Payload.Response.MessageResponse;
import crm.demo.Payload.Response.UserResponse;
import crm.demo.Security.CustomUserDetail;
import crm.demo.models.Crm;
import crm.demo.models.Erole;
import crm.demo.models.Roles;
import crm.demo.models.User;
import crm.demo.repo.UserRepo;
import crm.demo.services.RoleService;
import crm.demo.services.UserAdminService;
import crm.demo.services.UserService;
import crm.demo.utils.ErrorUtil;

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
  @Autowired
  private UserAdminService userAdminService;
  @Autowired
  private UserRepo userRepo;

  ErrorUtil errorUtil = new ErrorUtil();

  private static Logger logger = Logger.getLogger(UserController.class);

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
    if (userService.existByUserName(signupRequest.getUsername())) {
      return errorUtil.badStatus("Username đã tồn tại");
    }
    if (signupRequest.getUsername() == null) {
      return errorUtil.badStatus("Username không được để trống");
    }
    if (signupRequest.getPassword() == null) {
      return errorUtil.badStatus("Password không được để trống");
    }
    if (userService.existByEmail(signupRequest.getEmail())) {
      return errorUtil.badStatus("Email đã tồn tại");
    }
    if (signupRequest.getPhone() == null) {
      return errorUtil.badStatus("Số điện thoại không đúng định dạng");
    }

    User users = new User();
    users.setUserName(signupRequest.getUsername());
    users.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    users.setEmail(signupRequest.getEmail());
    users.setName(signupRequest.getName());
    users.setPhone(signupRequest.getPhone());
    users.setStatus(1);
    users.setCreatedAt();
    Set<String> strRoles = signupRequest.getListRoles();
    Set<Roles> listRoles = new HashSet<>();
    if (strRoles == null) {
      Roles userRoles = roleService.findByRoleName(Erole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
      listRoles.add(userRoles);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Roles adminRoles = roleService.findByRoleName(Erole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
            listRoles.add(adminRoles);
          case "moderator":
            Roles modRoles = roleService.findByRoleName(Erole.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
            listRoles.add(modRoles);
          case "user":
            Roles userRoles = roleService.findByRoleName(Erole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
            listRoles.add(userRoles);
        }
      });
    }
    users.setListRoles(listRoles);
    userService.saveOrUpdate(users);
    return errorUtil.goodStatus("Đăng ký thành công");
  }

  @PostMapping("/signin")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      CustomUserDetail customUserDetails = (CustomUserDetail) authentication.getPrincipal();
      if (customUserDetails.getStatus() == 0) {
        return errorUtil.badStatus("user is not actived");
      }
      String jwt = jwtTokenProvider.genarateToken(customUserDetails);
      List<String> listRoles = customUserDetails.getAuthorities().stream()
          .map(item -> item.getAuthority()).collect(Collectors.toList());
      logger.debug("Đăng nhập thành công ");
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
      logger.debug("Sai tài khoản hoặc mật khẩu ");
      return ResponseEntity.status(403).body("Authentication failed. Check your credentials.");
    }
  }

  @GetMapping("/users/detail")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
  public User userDetail(@AuthenticationPrincipal UserDetails userDetails) {

    if (userDetails != null) {

      // Sử dụng thông tin người dùng theo nhu cầu của bạn
      // Ví dụ: lấy ID nếu UserDetails là CustomUserDetails
      if (userDetails instanceof CustomUserDetail) {
        CustomUserDetail customUserDetails = (CustomUserDetail) userDetails;
        Long userId = customUserDetails.getId();
        User findUser = userAdminService.get(userId);
        return findUser;
      }
    }

    return null;

  }

  @PutMapping("/users/edit")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
  public ResponseEntity<Map<String, Object>> editUser(@AuthenticationPrincipal UserDetails userDetails,
      @RequestBody User user) {

    if (userDetails != null) {

      // Sử dụng thông tin người dùng theo nhu cầu của bạn
      // Ví dụ: lấy ID nếu UserDetails là CustomUserDetails
      if (userDetails instanceof CustomUserDetail) {
        CustomUserDetail customUserDetails = (CustomUserDetail) userDetails;
        Long userId = customUserDetails.getId();
        User findUser = userAdminService.get(userId);
        findUser.setUserName(user.getUserName());
        findUser.setName(user.getName());
        findUser.setAvatar(user.getAvatar());
        findUser.setEmail(user.getEmail());
        findUser.setPhone(user.getPhone());
        userAdminService.save(findUser);
      }

      return errorUtil.goodStatus("user updated successfully");
    }

    return errorUtil.badStatus("user with specified id not found");

  }

  @PutMapping("/users/changepassword")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
  public ResponseEntity<Map<String, Object>> changePassword(@AuthenticationPrincipal UserDetails userDetails,
      @RequestBody UserDto user) {
    if (userDetails != null) {

      // Sử dụng thông tin người dùng theo nhu cầu của bạn
      // Ví dụ: lấy ID nếu UserDetails là CustomUserDetails
      if (userDetails instanceof CustomUserDetail) {
        CustomUserDetail customUserDetails = (CustomUserDetail) userDetails;
        Long userId = customUserDetails.getId();
        User findUser = userAdminService.get(userId);
        // Lấy mật khẩu đã mã hóa từ cơ sở dữ liệu
        String currentEncodedPassword = findUser.getPassword();

        // So sánh mật khẩu nhập vào với mật khẩu đã mã hóa
        if (passwordEncoder.matches(user.getOldPassword(), currentEncodedPassword)) {
          findUser.setPassword(passwordEncoder.encode(user.getPassword()));
          userAdminService.save(findUser);
        } else {
          return errorUtil.badStatus("Password doesn't match");
        }
      }

      return errorUtil.goodStatus("Change password successfully");
    }

    return errorUtil.badStatus("user with specified id not found");

  }

  @GetMapping("/admin/users")
  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  public List<User> getListUsers() {
    return userAdminService.getListUsers();
  }

  @GetMapping("/admin/users/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  public User getUserById(@PathVariable("id") Long id) {
    User findUser = userAdminService.get(id);
    return findUser;
  }

  @PutMapping("/admin/users/edit/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  public ResponseEntity<Map<String, Object>> editUserByAdmin(@PathVariable("id") Long id, @RequestBody User user) {
    User findUser = userAdminService.get(id);
    if (findUser != null) {
      findUser.setUserName(user.getUserName());
      findUser.setName(user.getName());
      findUser.setAvatar(user.getAvatar());
      findUser.setEmail(user.getEmail());
      findUser.setPhone(user.getPhone());
      findUser.setListRoles(user.getListRoles());
      userAdminService.save(findUser);
      return errorUtil.goodStatus("admin updated successfully");
    }

    return errorUtil.badStatus("user with specified id not found");

  }

  @DeleteMapping(value = "/admin/users/delete/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable long id) {
    User user = userAdminService.get(id);
    if (user != null) {
      user.setStatus(0);
      userRepo.save(user);
      return errorUtil.goodStatus("user deleted successfully");
    } else {
      return errorUtil.badStatus("user with specified id not found");
    }

  }

}
