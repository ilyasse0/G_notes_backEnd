package estm.dsic.jee.rest.Business;

import java.util.Vector;

import estm.dsic.jee.rest.Dao.UserDao;
import estm.dsic.jee.rest.Model.User;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


public class ImpUserServices  {
@Inject UserDao userdao;

    public ImpUserServices(UserDao userdao){
        this.userdao = userdao;
    }
 

  
    
  


    
    public int Login(User user ,HttpServletResponse response) {
        // TODO Auto-generated method stub

        User retrievedUser= userdao.findUser(user);
        if (retrievedUser != null && retrievedUser.getPassword().equals(user.getPassword())) {
            // Check if the user is an admin
            if (retrievedUser.isAdmin()==true) {
                
                Long idUser = userdao.getIdbyEmail(retrievedUser);
                Cookie userIdCookie = new Cookie("userId", String.valueOf(idUser));
            userIdCookie.setMaxAge(3600); // Cookie expires in 1 hour
            userIdCookie.setPath("/"); // Set cookie path to root directory
            //userIdCookie.setSecure(true); // Set Secure attribute to false
            response.addCookie(userIdCookie);
            
                return 1; // Admin user
            } else {
                Long idUser = userdao.getIdbyEmail(retrievedUser);
                Cookie userIdCookie = new Cookie("userId", String.valueOf(idUser));
            userIdCookie.setMaxAge(3600); // Cookie expires in 1 hour
            userIdCookie.setPath("/"); // Set cookie path to root directory
            //userIdCookie.setSecure(true); // Set Secure attribute to false
                return 2; // Non-admin user
            }
        } else {
            return 0; // Authentication failed
        }
      
    }

    public User addUser(User user) {
        User createdUser = userdao.create(user);
        if (createdUser != null) {
            System.out.println( "User created successfully");
            System.out.println(user.isValide());
            System.out.println(user.isAdmin());
            System.out.println(user.getEmail());
            return createdUser;

        } else {
            System.out.println("Failed to create user");
            return null;
        }
       
    }

    public boolean deleteUser(User user){
        System.out.println(userdao.getIdbyEmail(user));
      if (userdao.delete(user,userdao.getIdbyEmail(user) )) {
        
        System.out.println("User deleted");
        return true;
      } else {
        System.out.println("Failed to delete user");
        return false;
      }

    }

    public boolean updateUser(User user){
        if (userdao.update(user, userdao.getIdbyEmail(user))!=null) {
            System.out.println("User updated");
            return true;
        } else {
            System.out.println("Failed to update user");
            return false;
        }
    
}


}
    

 
