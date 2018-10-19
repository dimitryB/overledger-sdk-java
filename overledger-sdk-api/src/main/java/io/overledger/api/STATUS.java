package io.overledger.api;

/**
 * All available status of Overledger transaction
 */
public enum STATUS {

    created,

    signed,

    broadcasted,

    confirmed,

    error,

    rejected

}
