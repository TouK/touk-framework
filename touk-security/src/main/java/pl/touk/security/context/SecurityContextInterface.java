/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.security.context;

import java.util.Collection;


/**
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 * 
 * Changes:
 * - w wersji 1.0.19 wyrzucono metode z zaleznoscia do Spring: public UserDetails getLoggedInUser();
 */
public interface SecurityContextInterface {

    /**
     * @return
     * @since 1.1.0
     */
    Collection<String> getAuthorities();

    /**
     * This method is provided so your class do not have to violate "Do not talk to strangers" rule
     * when all it needs is a name of logged user 
     * @return Name of Logged in user
     */
    String getLoggedUserName();
}
