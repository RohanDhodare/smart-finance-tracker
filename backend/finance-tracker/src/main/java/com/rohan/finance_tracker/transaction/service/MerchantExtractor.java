package com.rohan.finance_tracker.transaction.service;

import org.springframework.stereotype.Service;

@Service
public class MerchantExtractor {

    private static final String UNKNOWN_MERCHANT = "Unknown Merchant";

    public String extractMerchantName(String remarks){
        if(remarks == null || remarks.isBlank() || !remarks.startsWith("UPI/")){
            return UNKNOWN_MERCHANT;
        }

//        UPI/AKSHAY FIS/paytmqr5a6pek@/Paid via S/YES BANK L/648722199759/SMY2605011055X6EILMHPQ4KRLB4NIIAFNW
//        UPI/UTSAV SWEE/q531273290@ybl/Paid via S/YES BANK L/648722237033/SMY2605011057372686S9HQRPC54LG3L5J4

        String[] remarkSplitArray = remarks.split("/",3);
        String merchantName = remarkSplitArray[1];
        return merchantName;
    }
}
