package estm.dsic.jee.rest.Controller;

import java.util.Vector;

import javax.sql.DataSource;

import estm.dsic.jee.rest.Business.impNoteServices;
import estm.dsic.jee.rest.Dao.NoteDao;
import estm.dsic.jee.rest.Dao.UserDao;
import estm.dsic.jee.rest.Model.Notes;
import estm.dsic.jee.rest.Model.User;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/notes")
public class NoteController {
    @Resource(lookup = "jdbc/myDB")
     DataSource dataSource;
     @Inject NoteDao noteDao;


     @Path("/add")
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.TEXT_PLAIN)
     public boolean addNote(Notes note){
        noteDao = new NoteDao(dataSource);
        impNoteServices impNote = new impNoteServices(noteDao);
        if(impNote.addNote(note)!=null){
            return true;
        }else{
            return false;
        }
        
     }

     @Path("/delete")
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.TEXT_PLAIN)
     public boolean deleteNote(Notes note){
     noteDao = new NoteDao(dataSource);
     impNoteServices impNote = new impNoteServices(noteDao);
     if(impNote.deleteNote(note)){
         return true;
     }else{
         return false;
     }
    
    
    }



     @Path("/update")
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.TEXT_PLAIN)
     public boolean updateNote(Notes note){

        noteDao = new NoteDao(dataSource);
        impNoteServices impNote = new impNoteServices(noteDao);
        if(impNote.updateNote(note)){
            return true;
        }else{
            return false;
        }
     }

@Path("fetchAllNotes")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Vector<Notes> fetchAllNotes(@QueryParam("id") Long id) {
    noteDao = new NoteDao(dataSource);
    return noteDao.fetchAllNote(id); // Pass the user ID to the fetchAll method
}




}
