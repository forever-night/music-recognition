package musicrecognition.entities;

import java.util.Date;
import java.util.Objects;


public class User {
    public enum Role {
        USER("USER"),
        ADMIN("ADMIN");
    
        String role;
        
        Role(String role) {
            this.role = role;
        }
        
        @Override
        public String toString() {
            return name().toUpperCase();
        }
    }
    
    private Integer id;
    private String username;
    private String password;
    private String email;
    private Boolean isEnabled = Boolean.TRUE;
    private Role role = Role.USER;
    private Date createdAt;
    
    public User() {}
    
    public void prePersist() {
        createdAt = new Date();
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Boolean getEnabled() {
        return isEnabled;
    }
    
    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(isEnabled, user.isEnabled) &&
                role == user.role;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, isEnabled, role);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", isEnabled=").append(isEnabled);
        sb.append(", role=").append(role);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
