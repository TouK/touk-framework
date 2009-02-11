package pl.touk.security.context;

import org.springframework.security.userdetails.UserDetails;

/**
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
public interface SecurityContextInterface {
    public UserDetails getLoggedInUser();
}
