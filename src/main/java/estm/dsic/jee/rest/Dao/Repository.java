package estm.dsic.jee.rest.Dao;

import java.util.Vector;

public interface Repository<T,I> {
    
    T create (T  entity);
    T findUser(T entity);
    boolean delete (T entity , I index);
    T  update (T entity, I index);
    I getIdbyEmail(T entity);
    Vector<T> fetchAll();
   // Vector<T> fetchAllNotes(I index);

}
