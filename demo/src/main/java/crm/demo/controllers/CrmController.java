package crm.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import crm.demo.Dto.CrmDto;
import crm.demo.Payload.Response.JwtResponse;
import crm.demo.Security.CustomUserDetail;
import crm.demo.models.Crm;
import crm.demo.models.User;
import crm.demo.repo.CrmRepo;
import crm.demo.repo.UserRepo;
import crm.demo.services.NotificationService;
import crm.demo.services.SendMailService;
import crm.demo.utils.ErrorUtil;
import io.jsonwebtoken.Claims;

import java.time.LocalDate;
import java.util.Collections;
import io.micrometer.common.util.StringUtils;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/crms")
public class CrmController {
    @Autowired
    private CrmRepo crmRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SendMailService emailService;

    @Autowired
    private NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(CrmController.class);

    ErrorUtil errorUtil = new ErrorUtil();

    @GetMapping(value = "/")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Crm> getCrm(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails != null) {

            // Sử dụng thông tin người dùng theo nhu cầu của bạn
            // Ví dụ: lấy ID nếu UserDetails là CustomUserDetails
            if (userDetails instanceof CustomUserDetail) {
                CustomUserDetail customUserDetails = (CustomUserDetail) userDetails;
                Long userId = customUserDetails.getId();
                return crmRepo.findAllActiveCrms(userId);
            }
        }

        return Collections.emptyList();
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Crm> getAdminCrm(
            @RequestParam(name = "userId", required = false) Long selectedUserId,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedStartDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedEndDate) {
        return crmRepo.filterCrms(selectedUserId, selectedStartDate, selectedEndDate);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public Crm getCrmById(@PathVariable long id) {
        return crmRepo.findById(id).get();
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Map<String, Object>> createCrm(@RequestBody CrmDto crmDto) {
        try {

            if (crmDto.getPhoneNumber() == 0) {
                return errorUtil.badStatus("Số điện thoại không phù hợp với định dạng");
            }

            if (StringUtils.isEmpty(crmDto.getDescription())) {
                return errorUtil.badStatus("Mô tả phải nhiều hơn hoặc bằng 100 kí tự");
            }

            Crm crm = new Crm();
            User user = userRepo.findById(crmDto.getUserId()).orElse(null);
            if (user != null) {
                crm.setUser(user);
                crm.setCustomerName(crmDto.getCustomerName());
                crm.setPhoneNumber(crmDto.getPhoneNumber());
                crm.setTitle(crmDto.getTitle());
                crm.setDescription(crmDto.getDescription());
                crm.setCrmFile(crmDto.getCrmFile());
                crm.setStartDate(crmDto.getStartDate());
                crm.setEndDate(crmDto.getEndDate());
                crmRepo.save(crm);

                // create notification
                notificationService.createNotification(crm);

                // send mail
                String to = "datdt56789@gmail.com";
                String subject = crm.getTitle();
                String body = crm.getDescription();
                emailService.sendEmail(to, subject, body);

                return errorUtil.goodStatus("Crm created successfully");
            } else {
                return errorUtil.badStatus("user id is invalid");
            }
        } catch (Exception e) {
            return errorUtil.badStatus("crm is invalid");
        }

    }

    @PutMapping(value = "/update/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Map<String, Object>> updateCrm(@PathVariable long id, @RequestBody CrmDto crmDto) {
        try {

            if (crmDto.getPhoneNumber() == 0) {
                return errorUtil.badStatus("Số điện thoại không phù hợp với định dạng");
            }

            if (StringUtils.isEmpty(crmDto.getDescription())) {
                return errorUtil.badStatus("Mô tả phải nhiều hơn hoặc bằng 100 kí tự");
            }

            Crm crm = crmRepo.findById(id).orElse(null);
            if (crm != null) {
                // Update CRM
                crm.setCustomerName(crmDto.getCustomerName());
                crm.setPhoneNumber(crmDto.getPhoneNumber());
                crm.setTitle(crmDto.getTitle());
                crm.setDescription(crmDto.getDescription());
                crm.setCrmFile(crmDto.getCrmFile());
                crm.setStartDate(crmDto.getStartDate());
                crm.setEndDate(crmDto.getEndDate());
                crmRepo.save(crm);

                // Create Notification
                notificationService.createNotification(crm);

                // Send Mail
                String to = "datletb789@gmail.com";
                String subject = crm.getTitle();
                String body = crm.getDescription();
                emailService.sendEmail(to, subject, body);

                return errorUtil.goodStatus("Crm updated successfully");
            } else {
                return errorUtil.badStatus("crm with specified id not found");
            }
        } catch (Exception e) {
            return errorUtil.badStatus("crm is invalid");
        }

    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Map<String, Object>> deleteCrm(@PathVariable long id) {

        Crm crm = crmRepo.findById(id).orElse(null);
        if (crm != null) {
            crm.setStatus(0);
            crmRepo.save(crm);
            return errorUtil.goodStatus("Crm deleted successfully");
        } else {

            return errorUtil.badStatus("crm with specified id not found");
        }

    }

}
