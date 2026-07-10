package com.rohan.finance_tracker.transaction.service;

import com.rohan.finance_tracker.transaction.enums.CategoryType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MerchantCategorizer{
    private final Map<String, CategoryType> merchantCategoryMap;

    public MerchantCategorizer(){
        merchantCategoryMap = new HashMap<>();

        merchantCategoryMap.put("ZOMATO", CategoryType.FOOD);
        merchantCategoryMap.put("SWIGGY", CategoryType.FOOD);
        merchantCategoryMap.put("DOMINOS", CategoryType.FOOD);
        merchantCategoryMap.put("AKSHAY FIS", CategoryType.FOOD);
        merchantCategoryMap.put("UTSAV SWEE", CategoryType.FOOD);
        merchantCategoryMap.put("UBER", CategoryType.TRANSPORT);
        merchantCategoryMap.put("OLA", CategoryType.TRANSPORT);
        merchantCategoryMap.put("AMAZON", CategoryType.SHOPPING);
        merchantCategoryMap.put("MYNTRA", CategoryType.SHOPPING);
        merchantCategoryMap.put("R K BURGER", CategoryType.FOOD);
        merchantCategoryMap.put("AROMA TEA", CategoryType.FOOD);
        merchantCategoryMap.put("BLINKIT", CategoryType.GROCERIES);
        merchantCategoryMap.put("THYROCARE", CategoryType.HEALTH);
        merchantCategoryMap.put("KISMAT CHI", CategoryType.FOOD);
        merchantCategoryMap.put("JANATA BAZ", CategoryType.GROCERIES);
        merchantCategoryMap.put("MATOSHREE", CategoryType.HEALTH);
        merchantCategoryMap.put("RASRANG SW", CategoryType.FOOD);
        merchantCategoryMap.put("SHREE GANE", CategoryType.GROCERIES);
        merchantCategoryMap.put("MR DIY", CategoryType.SHOPPING);
        merchantCategoryMap.put("VASU VADAP", CategoryType.FOOD);
        merchantCategoryMap.put("DISTRICT", CategoryType.ENTERTAINMENT);
        merchantCategoryMap.put("CONNECT HE", CategoryType.HEALTH);
        merchantCategoryMap.put("SAI SERVIC", CategoryType.TRANSPORT);
        merchantCategoryMap.put("AROMA CAFE", CategoryType.FOOD);
        merchantCategoryMap.put("AMAZON PAY", CategoryType.BILLS);
        merchantCategoryMap.put("CAFE DESTI", CategoryType.FOOD);
        merchantCategoryMap.put("DMART READ", CategoryType.GROCERIES);
        merchantCategoryMap.put("ROHAN VIKA", CategoryType.TRANSFER);
        merchantCategoryMap.put("TEJAL RAVI", CategoryType.TRANSFER);

    }

    public CategoryType determineCategory(String merchantName){
        return merchantCategoryMap.getOrDefault(merchantName, CategoryType.OTHER);
    }
}
