package estm.dsic.jee.rest.Business;

import estm.dsic.jee.rest.Dao.NoteDao;
import estm.dsic.jee.rest.Dao.UserDao;
import estm.dsic.jee.rest.Model.Notes;
import jakarta.inject.Inject;

public class impNoteServices {
    @Inject NoteDao notedao;

    public impNoteServices(NoteDao noteDao) {
        this.notedao = noteDao;
    
}

    public Notes addNote(Notes note){
        Notes noteAdded = notedao.create(note);
        if(noteAdded != null){
            System.out.println("Note added successfully");
            return noteAdded;
        }else{
            System.out.println("note failed to add");
            return null;
        }

    }

    public boolean deleteNote(Notes note){
        if(notedao.delete(note, note.getId())){
            System.out.println("Note deleted successfully");
            return true;
        }else{
            System.out.println("note failed to delete");
            return false;
    
    }
}


    public boolean updateNote(Notes note){
    if(notedao.update(note,note.getId())!=null){
        System.out.println("Note updated");
        return true;
    }else{
        System.out.println("note failed to update");
        return false;
        }
    
    }
}
