package musicrecognition.services.impl;

import musicrecognition.dao.interfaces.UserDao;
import musicrecognition.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
    
    /**
     * Encrypts password and inserts user to the database.
     * */
    @Override
    @Transactional
    public Integer insert(musicrecognition.entities.User user) throws DuplicateKeyException {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        
        return userDao.insert(user);
    }
    
    @Override
    public User getSpringUserByUsername(String username) {
        musicrecognition.entities.User user = userDao.getByUsername(username);
        
        return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
    }
    
    @Override
    public musicrecognition.entities.User dtoToEntity(musicrecognition.dto.User userDto) {
        musicrecognition.entities.User userEntity = new musicrecognition.entities.User();
        
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        
        return userEntity;
    }
    
    private List<GrantedAuthority> getGrantedAuthorities(musicrecognition.entities.User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        
        return authorities;
    }
}
