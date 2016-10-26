package musicrecognition.dao.impl;

import musicrecognition.dao.interfaces.UserDao;
import musicrecognition.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setEnabled(resultSet.getBoolean("enabled"));
            user.setCreatedAt(resultSet.getTimestamp("created_at"));
            user.setRole(User.Role.valueOf(resultSet.getString("role")));
            
            return user;
        }
    }
    
    private static final class PartialUserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setEnabled(resultSet.getBoolean("enabled"));
            user.setCreatedAt(resultSet.getTimestamp("created_at"));
            user.setRole(User.Role.valueOf(resultSet.getString("role")));
    
            return user;
        }
    }
    
    @Override
    public Integer insert(User user) throws DuplicateKeyException {
        if (validate(user)) {
            user.prePersist();
    
            String query = "INSERT INTO \"user\" (username, password, role, enabled, created_at, email) " +
                    "VALUES (:username, :password, :role, :enabled, :createdAt, :email)";
    
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("username", user.getUsername());
            parameterMap.put("password", user.getPassword());
            parameterMap.put("role", user.getRole().toString());
            parameterMap.put("enabled", user.getEnabled());
            parameterMap.put("createdAt", user.getCreatedAt());
            parameterMap.put("email", user.getEmail());
            
            SqlParameterSource parameterSource = new MapSqlParameterSource(parameterMap);
            
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(query, parameterSource, keyHolder);

            if (!keyHolder.getKeys().isEmpty())
                return (Integer) keyHolder.getKeys().get("id");
        }
        
        return null;
    }
    
    @Override
    public User getByUsername(String username) {
        String query = "select * from \"user\" u where u.username = :username";
        
        Map<String, String> parameterMap = Collections.singletonMap("username", username);
        List<User> result = namedParameterJdbcTemplate.query(query, parameterMap, new UserRowMapper());
        
        if (result.isEmpty())
            return null;
        else
            return result.get(0);
    }
    
    @Override
    public List<User> getAll() {
        String query = "select * from \"user\" u";
        List<User> result = namedParameterJdbcTemplate.query(query, new PartialUserRowMapper());
        
        if (result.isEmpty())
            return null;
        else
            return result;
    }
    
    @Override
    public boolean checkIfExists(User user) {
        String query = "select count(*) from \"user\" u where u.username = :username and u.password = :password";
        
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        Integer result = namedParameterJdbcTemplate.queryForObject(query, parameterSource, Integer.class);
        
        return result > 0;
    }
    
    private boolean validate(User user) {
        return user != null &&
                user.getUsername() != null && user.getUsername().length() > 1 &&
                user.getPassword() != null && user.getPassword().length() > 1;
    }
}
