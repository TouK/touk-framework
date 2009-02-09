/*
 * Copyright (c) 2009 TouK
 * All rights reserved
 */
package pl.touk.framework.logging.renderers;

import org.apache.log4j.or.*;
import pl.touk.framework.logging.messages.BusinessMessage;

/**
 * Rendering Business Message to a form insertable to database by JDBCAppender
 * @author Mateusz Lipczyński
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
public class BusinessMessageRenderer implements ObjectRenderer {

    /**
     * Neutralizes the java String to a format accepted by database.
     * Not really a good way to prevent an sql injection attack.
     * @param s String to be neutralized
     * @return Normalized string
     */
    //TODO: find a better way to prevent sql injection attack in JDBCAppender (which means using prepared statement is not possible)
    protected String neutralize(String s) {
        if(s == null) {
            return "NULL";
        } else {
            return "'" + s.replaceAll("'", "''") + "'";
        }
    }


    /**
     * Render the object passed as parameter as a String.
     * @param arg0 Expected to be of BusinessMessage class
     * @return
     */
    public String doRender(Object arg0) {
        if (!(arg0 instanceof BusinessMessage)) {
            throw new UnsupportedOperationException("Class "+arg0.getClass() +" Not supported yet. Expecting and object of BusinessMessage class.");
        }

        BusinessMessage bm = (BusinessMessage)(arg0);
        String resultTranslatedToOracleConvention = (bm.getFinishedWithoutErrors()) ? "'Y'" : "'N'";

        StringBuffer insertParameters = new StringBuffer();

        insertParameters.append(neutralize(bm.getUser()));
        insertParameters.append(", ");
        insertParameters.append("sysdate");
        insertParameters.append(", ");
        insertParameters.append(neutralize(bm.getOperation()));
        insertParameters.append(", ");
        insertParameters.append(neutralize(bm.getData()));
        insertParameters.append(", ");
        insertParameters.append(resultTranslatedToOracleConvention);

        return insertParameters.toString();
    }
}