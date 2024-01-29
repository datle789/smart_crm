package crm.demo.services;

import java.util.List;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crm.demo.models.User;
import crm.demo.repo.UserRepo;

@Service
public interface UserService {

  User finByUserName(String userName);

  boolean existByUserName(String userName);

  boolean existByEmail(String email);

  User saveOrUpdate(User users);
}
