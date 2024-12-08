package com.kc.webdemo01.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author KCWang
 * @version 1.0
 * @date 2023/6/24 下午5:34
 */
@Builder
@Data
public class InvoiceOrder {

    private Integer id ;
    private String invoiceOrder;
    private String companyName;
    private String taxNumber;
    private String accountBank;
    private String companyAddress;
    private String bankNumber;
    private String companyTelephone;
    private String accountName;
}