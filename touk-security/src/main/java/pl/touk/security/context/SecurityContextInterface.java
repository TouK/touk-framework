package pl.touk.security.context;

import org.springframework.security.userdetails.UserDetails;

/**
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
public interface SecurityContextInterface {

    /**
     * @return Logged in user
     */
    public UserDetails getLoggedInUser();

    /**
     * This method is provided so your class do not have to violate "Do not talk to strangers" rule
     * when all it needs is a name of logged user 
     * @return Name of Logged in user
     */
    public String getLoggedUserName();
}
