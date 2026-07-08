package com.rohan.finance_tracker.transaction.mapper;

import com.rohan.finance_tracker.parser.dto.ExcelRow;
import com.rohan.finance_tracker.exception.InvalidTransactionAmountException;
import com.rohan.finance_tracker.transaction.MerchantCategorizer;
import com.rohan.finance_tracker.transaction.Transaction;
import com.rohan.finance_tracker.transaction.TransactionType;
import com.rohan.finance_tracker.transaction.service.MerchantExtractor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionMapper {
    private final MerchantExtractor merchantExtractor;
    private final MerchantCategorizer merchantCategorizer;

    public TransactionMapper(MerchantExtractor merchantExtractor, MerchantCategorizer merchantCategorizer){
        this.merchantExtractor = merchantExtractor;
        this.merchantCategorizer = merchantCategorizer;
    }

    public Transaction toTransaction(ExcelRow excelRow){

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


        String merchantName = merchantExtractor.extractMerchantName(excelRow.transactionRemarks())
                .trim().toUpperCase();

        transaction.setMerchantName(merchantName);
        transaction.setCategoryType(merchantCategorizer.determineCategory(merchantName));

        return transaction;
    }
}
