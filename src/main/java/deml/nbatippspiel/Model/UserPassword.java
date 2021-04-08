package deml.nbatippspiel.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserPassword {
    @Id
    private Integer userId;
    private String password;

    public UserPassword(final Integer userid, final String password) {
        this.userId = userid;
        this.password = password;
    }

    public UserPassword() {
        this.userId = null;
        this.password = null;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj.getClass().equals(UserPassword.class) &&
                this.userId.equals(((UserPassword) obj).getUserId()) &&
                this.password.equals(((UserPassword) obj).getPassword());
    }

    @Id
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
