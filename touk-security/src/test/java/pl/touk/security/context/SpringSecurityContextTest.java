package pl.touk.security.context;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class SpringSecurityContextTest  {

    @Test
    //TODO: remove comments when changing version of spring-security-core to higher than 2.0.4
    //for rationale see http://jira.springframework.org/browse/SEC-1010
    public void testGetLoggedInUser() throws Exception {

        //setting up environment
        GrantedAuthority[] grantedAuthorities = new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_CUSTOMER")};
        UserDetails userDetails = new User("username", "123456", true, false, false, false, grantedAuthorities);
        Authentication authentication = new TestingAuthenticationToken(userDetails, "password", grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SpringSecurityContext context = new SpringSecurityContext();

        Assert.assertSame(userDetails.getUsername(), context.getLoggedUserName());

    }



}
