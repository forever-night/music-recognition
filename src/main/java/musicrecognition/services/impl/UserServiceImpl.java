package musicrecognition.services.impl;

import musicrecognition.dao.interfaces.UserDao;
import musicrecognition.dto.UserDto;
import musicrecognition.entities.User;
import musicrecognition.services.interfaces.EmailService;
import musicrecognition.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    EmailService emailService;
    
    @Override
    @Transactional
    public Integer insert(User user) throws DuplicateKeyException {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        
        Integer id = userDao.insert(user);
        emailService.sendRegistrationConfirmation(user);
        
        return id;
    }
    
    @Override
    public org.springframework.security.core.userdetails.User getSpringUserByUsername(String username) {
        User user = userDao.getByUsername(username);
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
    }
    
    @Override
    public User dtoToEntity(UserDto userDto) {
        User userEntity = new User();
        
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        
        return userEntity;
    }
    
    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        
        return authorities;
    }
}
