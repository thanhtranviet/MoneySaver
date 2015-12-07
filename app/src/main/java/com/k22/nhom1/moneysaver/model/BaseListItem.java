package com.k22.nhom1.moneysaver.model;

/**
 * Created by thanh on 07/12/2015.
 */
public class BaseListItem {
    private String itemTitle;
    private String itemValue;

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public BaseListItem(String title) {
        this.itemTitle = title;
    }

    public BaseListItem(String title, String value) {
        this.itemTitle = title;
        this.itemValue = value;
    }
}
