package deml.nbatippspiel.Utility;

import deml.nbatippspiel.Model.User;

public class UserUtility {

    public static User getMergedUser(final User oldUser,
                                     final String username,
                                     final String firstname,
                                     final String lastname) {
        return new User(oldUser.getId(),
                isNullOrEmpty(username) ? oldUser.getUsername() : username,
                isNullOrEmpty(firstname) ? oldUser.getFirstname() : firstname,
                isNullOrEmpty(lastname) ? oldUser.getLastname() : lastname);
    }

    public static boolean isNullOrEmpty(final String s) {
        return s == null || s.equals("");
    }
}
