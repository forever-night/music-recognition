package musicrecognition.dao.impl;

import musicrecognition.dao.interfaces.UserDao;
import musicrecognition.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setEnabled(resultSet.getBoolean("enabled"));
            user.setCreatedAt(resultSet.getTimestamp("created_at"));
            user.setRole(User.Role.valueOf(resultSet.getString("role")));
            
            return user;
        }
    }
    
    @Override
    public Integer insert(User user) {
        if (validate(user)) {
            user.prePersist();
    
            String query = "INSERT INTO \"user\" (username, password, role, enabled, created_at) " +
                    "VALUES (:username, :password, :role, :enabled, :createdAt)";
    
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("username", user.getUsername());
            parameterMap.put("password", user.getPassword());
            parameterMap.put("role", user.getRole().toString());
            parameterMap.put("enabled", user.getEnabled());
            parameterMap.put("createdAt", user.getCreatedAt());
            
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
    
    private boolean validate(User user) {
        return user != null &&
                user.getUsername() != null && user.getUsername().length() > 1 &&
                user.getPassword() != null && user.getPassword().length() > 1;
    }
}