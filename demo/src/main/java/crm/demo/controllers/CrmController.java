package crm.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crm.demo.Dto.CrmDto;
import crm.demo.models.Crm;
import crm.demo.models.Notification;
import crm.demo.models.User;
import crm.demo.repo.CrmRepo;
import crm.demo.repo.NotificationRepo;
import crm.demo.repo.UserRepo;
import crm.demo.services.NotificationService;
import crm.demo.services.SendMailService;
import crm.demo.utils.ErrorUtil;
import io.micrometer.common.util.StringUtils;

import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path = "/api/crms")
public class CrmController {
    @Autowired
    private CrmRepo crmRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private SendMailService emailService;

    @Autowired
    private NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(CrmController.class);

    ErrorUtil errorUtil = new ErrorUtil();

    @GetMapping(value = "/")
    public List<Crm> getCrm() {
        // return crmRepo.findAll();
        return crmRepo.findAllActiveCrms();
    }

    @GetMapping(value = "/{id}")
    public Crm getCrmById(@PathVariable long id) {
        return crmRepo.findById(id).get();
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Map<String, Object>> createCrm(@RequestBody CrmDto crmDto) {
        try {
            // check input
            if (StringUtils.isEmpty(crmDto.getCustomerName()) ||
                    crmDto.getPhoneNumber() == 0 ||
                    StringUtils.isEmpty(crmDto.getTitle()) ||
                    StringUtils.isEmpty(crmDto.getDescription()) ||
                    crmDto.getStartDate() == null ||
                    crmDto.getEndDate() == null) {
                return errorUtil.badStatus("crm is not enough attribute");
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
    public ResponseEntity<Map<String, Object>> updateCrm(@PathVariable long id, @RequestBody CrmDto crmDto) {
        try {
            // check input
            if (StringUtils.isEmpty(crmDto.getCustomerName()) ||
                    crmDto.getPhoneNumber() == 0 ||
                    StringUtils.isEmpty(crmDto.getTitle()) ||
                    StringUtils.isEmpty(crmDto.getDescription()) ||
                    crmDto.getStartDate() == null ||
                    crmDto.getEndDate() == null) {
                return errorUtil.badStatus("crm is not enough attribute");
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
