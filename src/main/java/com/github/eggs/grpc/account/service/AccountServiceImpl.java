package com.github.eggs.grpc.account.service; 

import com.github.eggs.grpc.account.AccountServiceGrpc;
import com.google.protobuf.Timestamp;
import com.github.eggs.grpc.account.AccountRequest; 
import com.github.eggs.grpc.account.AccountIdRequest;
import com.github.eggs.grpc.account.FindAccountByCustomerRequest; 
import com.github.eggs.grpc.account.Accounts; 

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import com.github.eggs.grpc.account.Account; 
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class AccountServiceImpl extends AccountServiceGrpc.AccountServiceImplBase{
    
    private static final Logger LOGGER = 
        LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public void createAccount(AccountRequest request, StreamObserver<Account> responseObserver) { 
        LOGGER.info("server received {}", request);
        String accountId = request.getId();
        Account account = request.getAccount();

        Account createdAccount = Account.newBuilder().setId(accountId).setCustomerId(account.getCustomerId()).setDateCreated(getTimestamp(LocalDate.now())).build();
// TODO replace with Database Access.
        responseObserver.onNext(createdAccount);
        responseObserver.onCompleted(); 
    }
    
    @Override
    public void updateAccount(AccountRequest request, StreamObserver<Account> responseObserver) { 
        LOGGER.info("server received {}", request);
        String accountId = request.getId();
        Account account = request.getAccount();
// TODO replace with Database Access.
        responseObserver.onNext(account);
        responseObserver.onCompleted(); 
    }

    @Override
    public void deleteAccount(AccountIdRequest request, StreamObserver<Account> responseObserver){
        LOGGER.info("server received {}", request);
        String accountId = request.getId(); 
        //Todo get Account from database 
        Timestamp timestamp = getTimestamp(LocalDate.now()); 

        Account account = Account.newBuilder().setId(accountId).setDateClosed(timestamp).build();
        responseObserver.onNext(account); 
        responseObserver.onCompleted(); 
    }

    @Override
    public void getAccountById(AccountIdRequest request, StreamObserver<Account> responseObserver) {
        LOGGER.info("server received {}", request);
        String accountId = request.getId(); 
        //TODO
        Account account = Account.newBuilder().setId(accountId).build(); 
        responseObserver.onNext(account); 
        responseObserver.onCompleted(); 
    }

    @Override
    public void findAccountByCustomer(FindAccountByCustomerRequest request, StreamObserver<Accounts> responseObserver){
        LOGGER.info("server received {}", request);
        String customerId = request.getCustomerId();
        Account account = Account.newBuilder().setId("ABCD").setCustomerId(customerId).build();
        Accounts accounts = Accounts.newBuilder().addAccount(account).build();
        responseObserver.onNext(accounts);
        responseObserver.onCompleted(); 
    }

    @Override
    public void findOpenAccountsByCustomer(FindAccountByCustomerRequest request, StreamObserver<Accounts> responseObserver){
        LOGGER.info("server received {}", request);
        String customerId = request.getCustomerId();
        Account account = Account.newBuilder().setId("ABCD").setCustomerId(customerId).build();
        Accounts accounts = Accounts.newBuilder().addAccount(account).build();
        responseObserver.onNext(accounts);
        responseObserver.onCompleted(); 
    }

    private Timestamp getTimestamp(final LocalDate localDate) { 
        Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        return Timestamp.newBuilder().setSeconds(instant.getEpochSecond()).setNanos(instant.getNano()).build();
    }
}