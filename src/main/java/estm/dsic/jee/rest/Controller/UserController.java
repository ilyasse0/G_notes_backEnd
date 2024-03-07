package estm.dsic.jee.rest.Controller;

import java.sql.Connection;
import java.util.Vector;

import javax.sql.DataSource;

import estm.dsic.jee.rest.Business.ImpUserServices;
import estm.dsic.jee.rest.Dao.UserDao;
import estm.dsic.jee.rest.Model.User;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserController {

     @Resource(lookup = "jdbc/myDB")
     DataSource dataSource;
     @Inject UserDao userDao;



  

     @Path("/auth")
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON) // Change to JSON
public Response auth(User user, @Context HttpServletResponse response) {
    userDao = new UserDao(dataSource);
    ImpUserServices impUser = new ImpUserServices(userDao);

    int authResult = impUser.Login(user, response);
    

    // Check the authentication result
    if (authResult == 1) {
        return Response.ok().entity("{\"message\": \"Authentication successful - Admin user\", \"isAdmin\": true}")
                       .header("X-Content-Type-Options", "nosniff")
                       .build();
    } else if (authResult == 2) {
        return Response.ok().entity("{\"message\": \"Authentication successful - Non-admin user\", \"isAdmin\": false}")
                       .header("X-Content-Type-Options", "nosniff")
                       .build();
    } else {
        return Response.status(Response.Status.UNAUTHORIZED)
                       .entity("{\"message\": \"Authentication failed\"}")
                       .header("X-Content-Type-Options", "nosniff")
                       .build();
    }
}
    



    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean register(User user) {
       
         userDao = new UserDao(dataSource);
         ImpUserServices impUser = new ImpUserServices(userDao);
          if(impUser.addUser(user) != null){
            return true;
          }else 
          return false;
    }


@Path("/delete")
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public boolean deleteUser(User user) {
    userDao = new UserDao(dataSource);
    ImpUserServices impUser = new ImpUserServices(userDao);
    if(impUser.deleteUser(user)){
        return true;
    }else
    return false;
}




@Path("/update")
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public boolean updateUser(User user) {
    userDao = new UserDao(dataSource);
    ImpUserServices impUser = new ImpUserServices(userDao);
    if (impUser.updateUser(user)) {
        return true;
    } else {
        return false;
    }
}


@Path("fetchAll")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Vector<User>fetchAll(){
    userDao = new UserDao(dataSource);
    return userDao.fetchAll();

}





    




  
}
