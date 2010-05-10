/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.security.context;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
public class SpringSecurityContext implements SecurityContextInterface {

	private UserDetails getLoggedInUser() {

		// call outside of operating security context
		// not exactly bad, but possible during hibernate bootstrap.
		// @see
		// org.hibernate.engine.UnsavedValueFactory.getUnsavedIdentifierValue;
		if (SecurityContextHolder.getContext() == null) {
			return null;
		}

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return null;
		}

		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user == null) {
			throw new SecurityException("No user logged in");
		}

		if (!(user instanceof UserDetails)) {
			throw new SecurityException(
					"Wrong user object instance. Expected UserDetails.");
		}

		return (UserDetails) user;
	}

	public String getLoggedUserName() {
		UserDetails userDetails = this.getLoggedInUser();

		if (null == userDetails) {
			return null;
		} else {
			return userDetails.getUsername();
		}
	}

    public Collection<String> getAuthorities() {
        UserDetails userDetails = this.getLoggedInUser();
        Collection<String> authorities = new ArrayList<String>();
        if (null == userDetails) {
            return null;
        } else {
            for (GrantedAuthority a : userDetails.getAuthorities()) {
                authorities.add(a.getAuthority());
            }
            return authorities;
        }
    }
}
