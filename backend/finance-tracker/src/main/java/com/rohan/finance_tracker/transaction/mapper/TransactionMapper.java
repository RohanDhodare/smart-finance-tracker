package com.rohan.finance_tracker.transaction.mapper;

import com.rohan.finance_tracker.parser.dto.ExcelRow;
import com.rohan.finance_tracker.exception.InvalidTransactionAmountException;
import com.rohan.finance_tracker.transaction.enums.CategoryType;
import com.rohan.finance_tracker.transaction.entity.Transaction;
import com.rohan.finance_tracker.transaction.enums.TransactionType;
import com.rohan.finance_tracker.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionMapper {

    public Transaction toTransaction(ExcelRow excelRow,
                                     String merchantName,
                                     CategoryType categoryType,
                                     User user){

        Transaction transaction = new Transaction();

        if(excelRow.withdrawalAmt().compareTo(BigDecimal.ZERO)==0 && excelRow.depositAmt().compareTo(BigDecimal.ZERO)==0){
            throw new InvalidTransactionAmountException("Both withdrawal and deposit amount can't be zero.");
        }
         else if(excelRow.withdrawalAmt().compareTo(BigDecimal.valueOf(0)) > 0){
             transaction.setAmount(excelRow.withdrawalAmt());
             transaction.setTransactionType(TransactionType.DEBIT);
         }
         else{
             transaction.setAmount(excelRow.depositAmt());
             transaction.setTransactionType(TransactionType.CREDIT);
         }

         transaction.setBalance(excelRow.balance());

        transaction.setRemarks(excelRow.transactionRemarks());
        transaction.setTransactionDate(excelRow.transactionDate());

        transaction.setMerchantName(merchantName);
        transaction.setCategoryType(categoryType);

        transaction.setUser(user);

        return transaction;
    }
}
