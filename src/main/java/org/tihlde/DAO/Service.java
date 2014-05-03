package org.tihlde.DAO;

import org.tihlde.DTO.Transaction;

import java.rmi.Remote;

/**
 * Created by kanin on 03.05.14.
 */

public interface Service extends Remote {

    Transaction makeTransaction(double amount);

}
