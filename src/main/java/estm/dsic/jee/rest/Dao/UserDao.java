package estm.dsic.jee.rest.Dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.sql.DataSource;

import estm.dsic.jee.rest.Model.User;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class UserDao implements Repository<User , Long>  {
    @Resource(lookup ="jbdc/myDB")
    private DataSource dataSource;
    private Connection connection;
    

   
    public UserDao(DataSource dataSource) {

        this.dataSource = dataSource;
     initializeConnection();
    }
    public UserDao() {
        // Default constructor required by CDI
        
    }



     public void initializeConnection() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
public User create(User entity) {
    String query = "INSERT INTO USER (EMAIL, PASSWORD, isAdmin, isValide) VALUES (?, ?, ?, ?)";
    try {
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, entity.getEmail());
        ps.setString(2, entity.getPassword());
        ps.setBoolean(3, entity.isAdmin());
        ps.setBoolean(4, entity.isValide());
        
        int rowsAffected = ps.executeUpdate(); // Use executeUpdate() for INSERT operation
       // System.out.println(entity.isAdmin);
        if (rowsAffected > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
                return entity;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    @Override
    public User findUser(User entity) {
        initializeConnection();


    String query = "SELECT * FROM USER WHERE EMAIL = ? AND PASSWORD = ?";
    try {
        
       PreparedStatement ps = connection.prepareStatement(query);
       ps.setString(1, entity.getEmail()); // Index starts from 1
       ps.setString(2, entity.getPassword()); // Index starts from 1
       ResultSet rs = ps.executeQuery();

       if (rs.next()) {
           User user = new User();
           user.setId(rs.getLong("id"));
           user.setEmail(rs.getString("EMAIL"));
           user.setPassword(rs.getString("PASSWORD"));
           user.setAdmin(rs.getBoolean("isAdmin"));
           user.setValide(rs.getBoolean("isValide"));
           return user;
       }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}



@Override
public boolean delete(User entity, Long index) {
    String query = "DELETE FROM USER WHERE ID = ?";
    try {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, index);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            return true;
        } else {
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
        
    }
}

@Override
public User update(User entity, Long index) {
    String query = "UPDATE USER SET EMAIL = ?, isValide = ?, isAdmin = ? WHERE ID = ?";
    try {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, entity.getEmail());
        ps.setBoolean(2, entity.isValide());
        ps.setBoolean(3, entity.isAdmin());
        ps.setLong(4, index); // Assuming index is the ID
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            return entity; // Return the updated user object
        } else {
            return null; // Return null if no rows were affected
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return null; // Return null in case of an exception
    }
}

@Override
public Long getIdbyEmail(User entity) {
    String query = "SELECT ID FROM USER WHERE EMAIL = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, entity.getEmail());
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("ID");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
@Override
public Vector<User> fetchAll() {
    Vector<User> users = new Vector<>();
    String query = "SELECT * FROM USER";
    try {
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setAdmin(rs.getBoolean("isAdmin"));
            user.setValide(rs.getBoolean("isValide"));
            users.add(user);
        }
        rs.close();
        ps.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}






    
    
}
