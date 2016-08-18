package musicrecognition.dao;

import musicrecognition.entities.User;


public interface UserDao {
    Integer insert(User user);
        
    User getByUsername(String username);
}
