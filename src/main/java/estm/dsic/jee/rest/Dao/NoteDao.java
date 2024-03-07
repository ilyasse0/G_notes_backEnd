package estm.dsic.jee.rest.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import javax.sql.DataSource;

import estm.dsic.jee.rest.Model.Notes;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class NoteDao implements Repository<Notes , Long> {
     @Resource(lookup ="jbdc/myDB")
    private DataSource dataSource;
    private Connection connection;


    public NoteDao(DataSource dataSource){
            this.dataSource = dataSource;
     initializeConnection();
    }
    public NoteDao(){
    }

    public void initializeConnection() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
public Notes create(Notes entity) {
    String query = "INSERT INTO NOTE (`Date-time`, subject, body, `id_user`) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, entity.getDate());
        ps.setString(2, entity.getSubject());
        ps.setString(3, entity.getBody());
        ps.setLong(4, entity.getIdUser());
        
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 1) {
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                    return entity;
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    

    @Override
    public Notes findUser(Notes entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUser'");
    }

    @Override
    public boolean delete(Notes entity, Long index) {
        String query = "DELETE FROM NOTE WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, index);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Notes update(Notes entity, Long index) {
        String query = "UPDATE NOTE SET `Date-time` = ?, subject = ?, body = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, entity.getDate());
            ps.setString(2, entity.getSubject());
            ps.setString(3, entity.getBody());
            ps.setLong(4, index);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long getIdbyEmail(Notes entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdbyEmail'");
    }

    public Long getIdNoteByUserId(Long id) {
        String query = "SELECT id FROM NOTE WHERE `id-user` = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Vector<Notes> fetchAllNote(Long iduser) {
        Vector<Notes> notesList = new Vector<>();
        String query = "SELECT * FROM NOTE WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // Set the id-user parameter value
            ps.setLong(1, iduser);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notes note = new Notes();
                    note.setId(rs.getLong("id"));
                    note.setDate(rs.getString("Date-time"));
                    note.setSubject(rs.getString("subject"));
                    note.setBody(rs.getString("body"));
                    note.setIdUser(rs.getLong("id_user")); // Assuming this is the correct column name
                    notesList.add(note);
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notesList;
    }
    @Override
    public Vector<Notes> fetchAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchAll'");
    }
    
    
    
    
    
}
