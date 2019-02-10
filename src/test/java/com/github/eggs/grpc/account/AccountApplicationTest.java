package com.github.eggs.grpc.account;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.eggs.grpc.account.client.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.eggs.grpc.account.Account; 

import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountApplicationTest { 

    @Autowired
    private AccountClient client; 

    @Test
    public void test_create_account(){ 
        Account account = client.createAccount("accountId", "customerId");
        assertThat(account.getId()).isEqualTo("accountId");
        assertThat(account.getCustomerId()).isEqualTo("customerId");
        assertThat(account.getDateCreated()).isNotNull(); 
    }
}