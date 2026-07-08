package com.rohan.finance_tracker.transaction;

import com.rohan.finance_tracker.parser.ExcelParserService;
import com.rohan.finance_tracker.parser.dto.ExcelRow;
import com.rohan.finance_tracker.transaction.service.MerchantExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TransactionService {

    private ExcelParserService excelParserService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public TransactionService(ExcelParserService excelParserService){
        this.excelParserService = excelParserService;
    }

    public void saveFile(MultipartFile file){
        List<ExcelRow> list = excelParserService.parseExcel(file);
        logger.info("The listofParsed rows is: {}", list.size());
    }
}
