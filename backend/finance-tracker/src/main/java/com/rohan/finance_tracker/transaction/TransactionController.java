package com.rohan.finance_tracker.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions/upload")
    public ResponseEntity<String> uploadStatementFile(@RequestParam("file")MultipartFile file){
        String fileName = file.getOriginalFilename();

        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty!!");
        }
        else if( fileName == null || fileName.isBlank() ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File name shouldn't be empty");
        }
        else if( !(fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".xlsx"))){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File format not supported!!");
        }

        transactionService.saveFile(file);

        return ResponseEntity.status(HttpStatus.OK).body("File uploaded Successfully");

    }
}
