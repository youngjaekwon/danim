package com.danim.payments.dao;

import com.danim.payments.beans.Payments;

public interface IPaymentsDao {
    public int insert(Payments payments);
    public Payments select(String paymentsnum);
    public Payments search(String paymentsnum, String attribute, String keyword);
}
