package crm.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import crm.demo.models.User;

@Service
public interface UserService {

  User finByUserName(String userName);

  boolean existByUserName(String userName);

  boolean existByEmail(String email);

  User saveOrUpdate(User users);
}
