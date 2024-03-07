package estm.dsic.jee.rest.Model;

public class User {
    
    private Long id;
    private String email;
    private String password;
    public boolean isAdmin;
    public boolean isValide;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User() {
    }
 
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public boolean isValide() {
        return isValide;
    }
    public void setValide(boolean isValide) {
        this.isValide = isValide;
    }

    
}
