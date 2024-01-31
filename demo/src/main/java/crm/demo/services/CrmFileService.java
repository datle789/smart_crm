package crm.demo.services;

import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import crm.demo.controllers.CrmController;
import crm.demo.models.Crm;
import crm.demo.models.CrmFile;
import crm.demo.models.Notification;
import crm.demo.repo.CrmFileRepo;

@Service
public class CrmFileService {
    @Autowired
    private CrmFileRepo crmFileRepo;

    private static final Logger logger = LoggerFactory.getLogger(CrmController.class);

    public void createCrmfile(CrmFile fileName, Crm crm) {
        CrmFile crmFile = new CrmFile();
        crmFile.setFilename(fileName.getFilename());
        crmFile.setCrm(crm);
        crmFileRepo.save(crmFile);
    }

    public void updateCrmFile(long crmId) {
        // logger.info("Updating crm --->{}", crmFileRepo.findAllByCrmId(crmId));
        for (int i = 0; i < crmFileRepo.findAllByCrmId(crmId).size(); i++) {
            // crmFileRepo.deleteById(crmFileRepo.findAllByCrmId(crmId).get(i).getId());
            CrmFile crmFile = crmFileRepo.findById(crmFileRepo.findAllByCrmId(crmId).get(i).getId()).orElse(null);
            crmFile.setStatus(0);
            crmFileRepo.save(crmFile);
        }
    }
}
