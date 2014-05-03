package org.tihlde.service;

import org.tihlde.DB.Register;
import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.NoTransactionFoundException;

import java.util.ArrayList;

/**
 * Created by kanin on 04.05.14.
 */
public class BrokerImp implements Broker {

    ArrayList<Register> registers = new ArrayList<Register>();


    @Override
    public void addRegistry(Register register) {
        registers.add(register);
    }

    @Override
    public ArrayList<Register> getRegisters() {
        return registers;
    }

    @Override
    public void makeTransaction(Transaction transaction) throws NoTransactionFoundException {
        for (Register r : registers) {
            r.makeTransaction(transaction);
        }
        checkVersion(transaction);
    }

    @Override
    public boolean checkVersion(Transaction transaction) throws NoTransactionFoundException {
        for (Register r : registers) {
            if (r.getById(transaction.getId()).getVersion() != transaction.getVersion()) {
                rollback(transaction);
                System.out.println("Rollback completed");
                return false;
            }
        }
        return true;
    }

    @Override
    public void rollback(Transaction transaction) {
        for(Register r : registers) {
            r.rollback(transaction);
        }
    }
}
