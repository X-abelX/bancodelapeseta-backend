package com.banco.services;

import com.banco.dtos.CreateNewAccountDto;
import com.banco.dtos.VerificationCodeDto;
import com.banco.entities.EntityContract;
import com.banco.exceptions.CustomException;

import java.util.List;

public interface AccountService {

    List<EntityContract> getAccounts() throws CustomException;

    void createAccount(CreateNewAccountDto createNewAccountDto) throws CustomException;

    void deactivateAccount(String accountNumber, VerificationCodeDto verificationCode) throws CustomException;
}
