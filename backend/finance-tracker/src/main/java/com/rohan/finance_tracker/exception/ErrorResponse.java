package com.rohan.finance_tracker.exception;

import java.time.LocalDateTime;

//This is the DTO that we are using in out GlobalException Handler to send a standard format message for all exceptions
// as seen below:
//{
//        "timestamp": "2026-06-03T20:13:44.8235128",
//        "status": 500,
//        "message": "Something went wrong on server",
//        "path": "/register"
//        }

public record ErrorResponse(LocalDateTime timestamp, int status,
                            String message, String path) {}
