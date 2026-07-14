package com.rohan.finance_tracker.transaction.service;

import com.rohan.finance_tracker.parser.ExcelParserService;
import com.rohan.finance_tracker.parser.dto.ExcelRow;
import com.rohan.finance_tracker.transaction.entity.Transaction;
import com.rohan.finance_tracker.transaction.enums.CategoryType;
import com.rohan.finance_tracker.transaction.mapper.TransactionMapper;
import com.rohan.finance_tracker.transaction.repository.TransactionRepository;
import com.rohan.finance_tracker.user.User;
import com.rohan.finance_tracker.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final ExcelParserService excelParserService;
    private final MerchantExtractor merchantExtractor;
    private final MerchantCategorizer merchantCategorizer;
    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;


    public TransactionService(ExcelParserService excelParserService,
                              MerchantExtractor merchantExtractor,
                              MerchantCategorizer merchantCategorizer,
                              TransactionMapper transactionMapper,
                              UserRepository userRepository,
                              TransactionRepository transactionRepository){
        this.excelParserService = excelParserService;
        this.merchantExtractor = merchantExtractor;
        this.merchantCategorizer = merchantCategorizer;
        this.transactionMapper = transactionMapper;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }


    public void saveFile(MultipartFile file){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        List<Transaction> transactionsList = new ArrayList<>();

        List<ExcelRow> rowsList = excelParserService.parseExcel(file);
//        Integer srNo, LocalDate valueDate, java.time.LocalDate transactionDate,
//        String chequeNumber, String transactionRemarks,
//        BigDecimal withdrawalAmt, BigDecimal depositAmt, BigDecimal balance

        if(!rowsList.isEmpty()){
            for(int i=0; i<rowsList.size(); i++){
                String merchantName = merchantExtractor.extractMerchantName( rowsList.get(i).transactionRemarks() );
                CategoryType categoryType = merchantCategorizer.determineCategory(merchantName);
                Transaction temp = transactionMapper.toTransaction(rowsList.get(i), merchantName, categoryType, user);
                transactionsList.add(temp);
            }
        }

        logger.info("The size of parsed rowsList is: {}", rowsList.size());
        logger.info("The size of transactionList is: {}", transactionsList.size());
        logger.info("The first record before SAVE is: {}", transactionsList.get(0));

        List<Transaction> savedTransactions = transactionRepository.saveAll(transactionsList);

        logger.info("The first record after SAVE is: {}", transactionsList.get(0));


    }
}
