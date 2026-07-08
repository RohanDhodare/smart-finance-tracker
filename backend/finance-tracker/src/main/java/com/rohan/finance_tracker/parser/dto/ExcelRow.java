package com.rohan.finance_tracker.parser.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExcelRow(Integer srNo, LocalDate valueDate, LocalDate transactionDate, String chequeNumber, String transactionRemarks,
BigDecimal withdrawalAmt, BigDecimal depositAmt, BigDecimal balance) {}
