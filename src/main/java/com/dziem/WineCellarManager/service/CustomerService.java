package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.CustomerProfile;

public interface CustomerService {
    CustomerProfile getCustomer(String nickname);
    boolean customerExist(String nickname);
}
