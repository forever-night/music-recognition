package musicrecognition.dto.mappers;

import musicrecognition.dto.UserDto;
import musicrecognition.entities.User;
import musicrecognition.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class UserMapperTest {
    UserMapper userMapper;
    User userEntity;
    UserDto userDto;
    
    @Before
    public void setUp() {
        userMapper = new UserMapper();
        
        userDto = TestUtil.createUserDto();
        userEntity = TestUtil.dtoToEntity(userDto);
    }
    
    @Test
    public void dtoAllFieldsToEntity() {
        userDto.setCreatedAt(new Date());
        userDto.setEnabled(Boolean.TRUE);
        userDto.setRole(User.Role.USER);
        
        userEntity.setCreatedAt(userDto.getCreatedAt());
        userEntity.setEnabled(userDto.getEnabled());
        userEntity.setRole(userDto.getRole());
        
        User actual = userMapper.toEntity(userDto);
        
        assertNotNull(actual);
        assertEquals(userEntity, actual);
        assertEquals(userEntity.getCreatedAt(), actual.getCreatedAt());
    }
    
    @Test
    public void dtoPartialFieldsToEntity() {
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
        userDto.setRole(userEntity.getRole());
        
        UserDto actual = userMapper.toDto(userEntity);
        
        assertNotNull(actual);
        assertEquals(userDto, actual);
        assertEquals(userDto.getRole(), actual.getRole());
        assertEquals(userDto.getCreatedAt(), actual.getCreatedAt());
        assertEquals(userDto.getEnabled(), actual.getEnabled());
    }
}