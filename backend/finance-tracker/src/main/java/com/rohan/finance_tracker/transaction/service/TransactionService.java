package com.rohan.finance_tracker.transaction.service;

import com.rohan.finance_tracker.parser.ExcelParserService;
import com.rohan.finance_tracker.parser.dto.ExcelRow;
import com.rohan.finance_tracker.transaction.entity.Transaction;
import com.rohan.finance_tracker.transaction.enums.CategoryType;
import com.rohan.finance_tracker.transaction.mapper.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final ExcelParserService excelParserService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final MerchantExtractor merchantExtractor;
    private final MerchantCategorizer merchantCategorizer;
    private final TransactionMapper transactionMapper;


    public TransactionService(ExcelParserService excelParserService,
                              MerchantExtractor merchantExtractor,
                              MerchantCategorizer merchantCategorizer,
                              TransactionMapper transactionMapper){
        this.excelParserService = excelParserService;
        this.merchantExtractor = merchantExtractor;
        this.merchantCategorizer = merchantCategorizer;
        this.transactionMapper = transactionMapper;
    }

    public void saveFile(MultipartFile file){
        List<Transaction> transactionsList = new ArrayList<>();

        List<ExcelRow> rowsList = excelParserService.parseExcel(file);
//        Integer srNo, LocalDate valueDate, java.time.LocalDate transactionDate,
//        String chequeNumber, String transactionRemarks,
//        BigDecimal withdrawalAmt, BigDecimal depositAmt, BigDecimal balance

        if(!rowsList.isEmpty()){
            for(int i=0; i<rowsList.size(); i++){
                String merchantName = merchantExtractor.extractMerchantName( rowsList.get(i).transactionRemarks() );
                CategoryType categoryType = merchantCategorizer.determineCategory(merchantName);
                Transaction temp = transactionMapper.toTransaction(rowsList.get(i), merchantName, categoryType);
                transactionsList.add(temp);
            }
        }

        logger.info("The size of parsed rowsList is: {}", rowsList.size());
        logger.info("The size of transactionList is: {}", transactionsList.size());
        logger.info("The first record is: {}", transactionsList.get(0));
    }
}
