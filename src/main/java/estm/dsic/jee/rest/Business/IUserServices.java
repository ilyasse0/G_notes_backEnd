package estm.dsic.jee.rest.Business;

import estm.dsic.jee.rest.Dao.UserDao;
import estm.dsic.jee.rest.Model.User;

public interface IUserServices {
    public boolean Login( User user);
}
