package com.github.eggs.grpc.account.client;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.github.eggs.grpc.account.AccountServiceGrpc;
import com.google.protobuf.Timestamp;
import com.github.eggs.grpc.account.AccountRequest; 
import com.github.eggs.grpc.account.AccountIdRequest;
import com.github.eggs.grpc.account.FindAccountByCustomerRequest; 
import com.github.eggs.grpc.account.Accounts; 
import com.github.eggs.grpc.account.Account; 

@Component
public class AccountClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountClient.class);

    private AccountServiceGrpc.AccountServiceBlockingStub blockingStub; 

    @PostConstruct
    private void init() { 
        ManagedChannel managedChannel = ManagedChannelBuilder
            .forAddress("localhost", 6565).usePlaintext().build(); 
        blockingStub = AccountServiceGrpc.newBlockingStub(managedChannel);
    }

    public Account createAccount(final String accountId, final String customerId) { 
        
        Account account = Account.newBuilder().setCustomerId(customerId).build(); 
        AccountRequest request = AccountRequest.newBuilder().setId(accountId)
            .setAccount(account).build(); 
        LOGGER.info("client sending {}", request);
        Account createdAccount = blockingStub.createAccount(request); 
        LOGGER.info("created account {}", account);
        return createdAccount; 
    }
}