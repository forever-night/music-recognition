package musicrecognition.dto.mappers;

import musicrecognition.dto.UserDto;
import musicrecognition.entities.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static musicrecognition.util.TestUtil.createUserDto;
import static musicrecognition.util.TestUtil.dtoToEntity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class UserMapperTest {
    UserMapper userMapper;
    User userEntity;
    UserDto userDto;
    
    @Before
    public void setUp() {
        userMapper = new UserMapper();
        
        userDto = createUserDto();
        userEntity = dtoToEntity(userDto);
    }
    
    @Test
    public void dtoAllFieldsToEntity() {
        userEntity.setCreatedAt(userDto.getCreatedAt());
        userEntity.setEnabled(userDto.getEnabled());
        userEntity.setRole(User.Role.USER);
        
        User actual = userMapper.toEntity(userDto);
        
        assertNotNull(actual);
        assertEquals(userEntity, actual);
        assertEquals(userEntity.getCreatedAt(), actual.getCreatedAt());
    }
    
    @Test
    public void dtoPartialFieldsToEntity() {
        userDto.setEnabled(null);
        userDto.setRole(null);
        
        userEntity.setEnabled(null);
        userEntity.setRole(null);
        
        User actual = userMapper.toEntity(userDto);
        
        assertNotNull(actual);
        assertEquals(userEntity, actual);
    }
    
    @Test
    public void entityToDto() {
        userEntity.setPassword(null);
        userEntity.setCreatedAt(new Date());
        
        userDto.setPassword(userEntity.getPassword());
        userDto.setCreatedAt(userEntity.getCreatedAt());
        userDto.setEnabled(userEntity.getEnabled());
        userDto.setRole(userEntity.getRole().toString());
        
        UserDto actual = userMapper.toDto(userEntity);
        
        assertNotNull(actual);
        assertEquals(userDto, actual);
        assertEquals(userDto.getRole(), actual.getRole());
        assertEquals(userDto.getCreatedAt(), actual.getCreatedAt());
        assertEquals(userDto.getEnabled(), actual.getEnabled());
    }
}