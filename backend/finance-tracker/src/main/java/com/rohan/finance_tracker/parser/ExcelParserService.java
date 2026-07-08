package com.rohan.finance_tracker.parser;

import com.rohan.finance_tracker.exception.ExcelParsingException;
import com.rohan.finance_tracker.parser.dto.ExcelRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelParserService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelParserService.class);

    private static final int SR_NO = 1;
    private static final int VALUE_DATE = 2;
    private static final int TRANSACTION_DATE = 3;
    private static final int CHEQUE_NUMBER = 4;
    private static final int REMARKS = 5;
    private static final int WITHDRAWAL = 6;
    private static final int DEPOSIT = 7;
    private static final int BALANCE = 8;

    public List<ExcelRow> parseExcel(MultipartFile file) {
        List<ExcelRow> excelRows = new ArrayList<>();

        try(Workbook workbook = createWorkbook(file)){

//            we created a Sheet and got the first sheet from our file/workbook
            Sheet spreadsheet = workbook.getSheetAt(0);

//            Hardcoded Header Row index here
            int headerRowIndex = 12;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for(int rowIndex = headerRowIndex + 1; rowIndex <= spreadsheet.getLastRowNum(); rowIndex++){
                Row row = spreadsheet.getRow(rowIndex);
                if(row == null){
                    continue;
                }

                Cell srNoCell = row.getCell(SR_NO);
                if (srNoCell == null || srNoCell.getCellType() == CellType.BLANK ||
                        srNoCell.toString().trim().isEmpty()) {
                    continue;
                }

                String srNo = srNoCell.toString().trim();
                try{
                    Integer.parseInt(srNo);
                }
                catch(NumberFormatException e){
                    break; // here we are breaking when footer or legends is reached
                }

                ExcelRow excelRow = new ExcelRow(
                        Integer.valueOf(srNo),
                        LocalDate.parse( row.getCell(VALUE_DATE).toString().trim(), formatter),
                        LocalDate.parse( row.getCell(TRANSACTION_DATE).toString().trim(), formatter),
                        (row.getCell(CHEQUE_NUMBER) == null) ? "" : row.getCell(CHEQUE_NUMBER).toString().trim(),
                        row.getCell(REMARKS).toString(),
                        new BigDecimal(row.getCell(WITHDRAWAL).toString().trim()),
                        new BigDecimal(row.getCell(DEPOSIT).toString().trim()),
                        new BigDecimal(row.getCell(BALANCE).toString().trim())
                );
                    excelRows.add(excelRow);
            }
            return excelRows;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcelParsingException("Fail to read excel file", e);
        }
    }

    private Workbook createWorkbook(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if(filename.endsWith(".xlsx"))
        {
            return new XSSFWorkbook(file.getInputStream());
        }
        return new HSSFWorkbook(file.getInputStream());
    }

}

