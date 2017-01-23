package com.example.pat.aapkatrade.Home.navigation.entity;

import com.example.pat.aapkatrade.general.Validation;

import java.util.ArrayList;

/**
 * Created by PPC09 on 20-Jan-17.
 */

public class CategoryHome {
    private String categoryId;
    private String categoryName;
    private String categoryIconName;
    private String categoryIconPath;
    private ArrayList<SubCategory> subCategoryList;
    private String basePath = "http://aapkatrade.com/laraveldemo/public/appicon/";
    private String iconExtention = ".png";
    public CategoryHome(String categoryId, String categoryName, String categoryIconName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryIconName = categoryIconName;
        setCategoryIconPath(createIconPath(categoryName));
    }

    private String createIconPath(String categoryName){
        return Validation.isNonEmptyStr(categoryName)? basePath+categoryName.replaceAll(" |/", "")+iconExtention: "";
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryIconName() {
        return categoryIconName;
    }

    public String getCategoryIconPath() {
        return categoryIconPath;
    }

    public void setCategoryIconPath(String categoryIconPath) {
        this.categoryIconPath = categoryIconPath;
    }

    public ArrayList<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(ArrayList<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}