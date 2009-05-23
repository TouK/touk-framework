/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.logging.messages;

import static pl.touk.framework.util.contract.ContractValidator.mustBeNotEmpty;
import static pl.touk.framework.util.contract.ContractValidator.mustBeNotNull;

/**
 * When a business operation is proceeded (a method is invoked) and logged, this is the object
 * representing logged information.
 * @author <a href="mailto:mlp@touk.pl">Mateusz Lipczyński</a>.
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
public class BusinessMessage {

    /**
     * Full name of the user performing the business operation.
     */
    private String user;

    /**
     * Full name of the business operation performed by the user.
     * Usually service method's name
     */
    private String operation;

    /**
     * Business operation data that should be logged.
     * Usually service method's atributes
     */
    private String data;

    /**
     * Whether business operation resulted finished without errors.
     * An exception thrown from within the method is considered to be an error.
     */
    private Boolean isFinishedWithoutErrors;

    /**
     * Constructor
     * @param user Full name of the user performing the business operation
     * @param operation Full name of the business operation performed by the user
     * @param data Business operation data that should be logged
     * @param isFinishedWithoutErrors Whether business operation resulted finished without errors
     */
    public BusinessMessage( String user, String operation, String data, Boolean isFinishedWithoutErrors) {

    	mustBeNotNull(mustBeNotEmpty(user), mustBeNotEmpty(operation), mustBeNotEmpty(data), isFinishedWithoutErrors);

        this.user = user;
        this.operation = operation;
        this.data = data;
        this.isFinishedWithoutErrors = isFinishedWithoutErrors;
    }

    //methods

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessMessage)) {
            return false;
        }

        BusinessMessage that = (BusinessMessage) o;

        if (data != null ? !data.equals(that.data) : that.data != null) {
            return false;
        }
        if (operation != null ? !operation.equals(that.operation) : that.operation != null) {
            return false;
        }
        if (isFinishedWithoutErrors != null ? !isFinishedWithoutErrors.equals(that.isFinishedWithoutErrors) : that.isFinishedWithoutErrors != null) {
            return false;
        }
        if (user != null ? !user.equals(that.user) : that.user != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = user != null ? user.hashCode() : 0;
        result1 = 31 * result1 + (operation != null ? operation.hashCode() : 0);
        result1 = 31 * result1 + (data != null ? data.hashCode() : 0);
        result1 = 31 * result1 + (isFinishedWithoutErrors != null ? isFinishedWithoutErrors.hashCode() : 0);
        return result1;
    }

    //setters and getters

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getFinishedWithoutErrors() {
        return isFinishedWithoutErrors;
    }

    public void setFinishedWithoutErrors(Boolean finishedWithoutErrors) {
        this.isFinishedWithoutErrors = finishedWithoutErrors;
    }
}