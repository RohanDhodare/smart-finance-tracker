package com.rohan.finance_tracker.parser;

import com.rohan.finance_tracker.exception.ExcelParsingException;
import com.rohan.finance_tracker.parser.dto.ExcelRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelParserService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelParserService.class);

    public List<ExcelRow> parseExcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        List<ExcelRow> excelRows = new ArrayList<>();

        try(Workbook workbook = createWorkbook(file)){

//            we created a Sheet and got the first sheet from our file/workbook
            Sheet spreadsheet = workbook.getSheetAt(0);

//            created row iterator
            Iterator<Row> rowIterator = spreadsheet.rowIterator();

//            Hardcoded Header Row index here
            int headerRowIndex = 12;

            for(int rowIndex = headerRowIndex + 1; rowIndex <= spreadsheet.getLastRowNum(); rowIndex++){
                Row row = spreadsheet.getRow(rowIndex);
                if(row == null){
                    continue;
                }

                Cell srNoCell = row.getCell(1);
                if (srNoCell == null || srNoCell.getCellType() == CellType.BLANK ||
                        srNoCell.toString().trim().isEmpty()) {
                    continue;
                }

                ExcelRow excelRow = new ExcelRow(
                        Integer.valueOf(row.getCell(1).toString().trim()),
                        LocalDate.parse( row.getCell(2).toString().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy") ),
                        LocalDate.parse( row.getCell(3).toString().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy") ),
                        (row.getCell(4) == null) ? "" : row.getCell(4).toString().trim(),
                        row.getCell(5).toString(),
                        new BigDecimal(row.getCell(6).toString().trim()),
                        new BigDecimal(row.getCell(7).toString().trim()),
                        new BigDecimal(row.getCell(8).toString().trim())
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

