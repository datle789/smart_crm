package crm.demo.controllers;

import java.util.List;

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
import crm.demo.models.User;
import crm.demo.repo.CrmRepo;
import crm.demo.repo.UserRepo;

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

    private static final Logger logger = LoggerFactory.getLogger(CrmController.class);

    @GetMapping(value = "/")
    public List<Crm> getCrm() {
        // return crmRepo.findAll();
        return crmRepo.findAllActiveCrms();
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createCrm(@RequestBody CrmDto crmDto) {
        Crm crm = new Crm();
        User user = userRepo.findById(crmDto.getUserId()).orElse(null);
        if (user != null) {
            crm.setUser(user);
            crm.setCustomerName(crmDto.getCustomerName());
            crm.setPhoneNumber(crmDto.getPhoneNumber());
            crm.setDescription(crmDto.getDescription());
            crm.setCrmFile(crmDto.getCrmFile());
            crm.setStartDate(crmDto.getStartDate());
            crm.setEndDate(crmDto.getEndDate());
            crmRepo.save(crm);

            return ResponseEntity.ok("Crm created successfully");
        } else {
            logger.info("Log message: id không tồn tại");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with specified id not found");
        }

    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<String> updateCrm(@PathVariable long id, @RequestBody CrmDto crmDto) {
        // Crm crm = new Crm();
        Crm crm = crmRepo.findById(id).orElse(null);
        if (crm != null) {
            // crm.setUser(user);
            crm.setCustomerName(crmDto.getCustomerName());
            crm.setPhoneNumber(crmDto.getPhoneNumber());
            crm.setDescription(crmDto.getDescription());
            crm.setCrmFile(crmDto.getCrmFile());
            crm.setStartDate(crmDto.getStartDate());
            crm.setEndDate(crmDto.getEndDate());
            crmRepo.save(crm);

            return ResponseEntity.ok("Crm updated successfully");
        } else {
            logger.info("Log message: crm không tồn tại");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("crm with specified id not found");
        }

    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteCrm(@PathVariable long id) {
        Crm crm = crmRepo.findById(id).orElse(null);
        if (crm != null) {
            crm.setStatus(0);
            crmRepo.save(crm);
            return ResponseEntity.ok("Crm deleted successfully");
        } else {
            logger.info("Log message: crm không tồn tại");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("crm with specified id not found");
        }

    }

}
