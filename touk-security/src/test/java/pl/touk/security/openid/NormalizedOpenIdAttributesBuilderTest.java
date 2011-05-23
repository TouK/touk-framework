/*
 * Copyright (c) (2005 - 2009) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */
package pl.touk.security.openid;

import org.junit.Test;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;

public class NormalizedOpenIdAttributesBuilderTest {
    private static final String email = "some@email.pl";
    private static final String emailAttributeKey = "email";
    private static final String identityUrl = "myId";
    private static final String firstName = "Johnny";
    private static final String firstNameAttributeKey = "fname";
    private static final String lastName = "Rambo";
    private static final String lastNameAttributeKey = "lname";
    private static final String fullNameAttributeKey = "fullName";
    private static final String fullName = firstName + " " + lastName;


    @Test
    public void shouldBuildWithAttributesFromGoogle() {
        //given
        NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder = createBuilder();
        OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(OpenIDAuthenticationStatus.SUCCESS, identityUrl, "some message", createGoogleAttributes());

        //when
        NormalizedOpenIdAttributes normalizedOpenIdAttributes = normalizedOpenIdAttributesBuilder.build(token);

        //then
        assertEquals(email, normalizedOpenIdAttributes.getEmailAddress());
        assertEquals(identityUrl, normalizedOpenIdAttributes.getUserLocalIdentifier());
        assertEquals(fullName, normalizedOpenIdAttributes.getFullName());
    }


    private List<OpenIDAttribute> createGoogleAttributes() {
        List<OpenIDAttribute> openIDAttributes = new ArrayList<OpenIDAttribute>();
        openIDAttributes.add(new OpenIDAttribute(emailAttributeKey, "String", newArrayList(email)));
        openIDAttributes.add(new OpenIDAttribute(firstNameAttributeKey, "String", newArrayList(firstName)));
        openIDAttributes.add(new OpenIDAttribute(lastNameAttributeKey, "String", newArrayList(lastName)));
        return openIDAttributes;
    }

    @Test
    public void shouldBuildWithAttributesFromOpenId() {
        //given
        NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder = createBuilder();
        OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(OpenIDAuthenticationStatus.SUCCESS, identityUrl, "some message", createOpenIdAttributes());

        //when
        NormalizedOpenIdAttributes normalizedOpenIdAttributes = normalizedOpenIdAttributesBuilder.build(token);

        //then
        assertEquals(email, normalizedOpenIdAttributes.getEmailAddress());
        assertEquals(identityUrl, normalizedOpenIdAttributes.getUserLocalIdentifier());
        assertEquals(fullName, normalizedOpenIdAttributes.getFullName());
    }


    private List<OpenIDAttribute> createOpenIdAttributes() {
        List<OpenIDAttribute> openIDAttributes = new ArrayList<OpenIDAttribute>();
        openIDAttributes.add(new OpenIDAttribute(emailAttributeKey, "String", newArrayList(email)));
        openIDAttributes.add(new OpenIDAttribute(fullNameAttributeKey, "String", newArrayList(fullName)));
        return openIDAttributes;
    }

    private NormalizedOpenIdAttributesBuilder createBuilder() {
        NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder = new NormalizedOpenIdAttributesBuilder();
        normalizedOpenIdAttributesBuilder.setEmailAddressAttributeNames(newHashSet(newArrayList(emailAttributeKey)));
        normalizedOpenIdAttributesBuilder.setFirstNameAttributeNames(newHashSet(newArrayList(firstNameAttributeKey)));
        normalizedOpenIdAttributesBuilder.setLastNameAttributeNames(newHashSet(newArrayList(lastNameAttributeKey)));
        normalizedOpenIdAttributesBuilder.setFullNameAttributeNames(newHashSet(newArrayList(fullNameAttributeKey)));
        return normalizedOpenIdAttributesBuilder;
    }
}
