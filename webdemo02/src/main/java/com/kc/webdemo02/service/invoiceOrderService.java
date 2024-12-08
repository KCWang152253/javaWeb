package com.kc.webdemo02.service;

import java.util.List;

/**
 * @author KCWang
 * @version 1.0
 * @date 2023/6/24 下午5:28
 */
public interface invoiceOrderService {

    public List queryInvoiceLists(List invoiceOrders,Integer a,Integer b);
//    List<InvoiceOrder> invoiceOrderss = queryInvoiceLists(invoiceOrders,0,9999);
}
