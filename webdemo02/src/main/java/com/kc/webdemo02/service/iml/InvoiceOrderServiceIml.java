package com.kc.webdemo02.service.iml;

import com.kc.webdemo02.bean.InvoiceOrder;
import com.kc.webdemo02.service.invoiceOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KCWang
 * @version 1.0
 * @date 2023/6/24 下午5:29
 */
@Service
public class InvoiceOrderServiceIml  implements invoiceOrderService {

    @Override
    public List queryInvoiceLists(List invoiceOrders, Integer a, Integer b) {
        ArrayList<InvoiceOrder> invoiceOrders1 = new ArrayList<>();
//        map2.put("header", new String[]{"订单号", "商店名称", "发票批次", "账户名称", "商店地址", "刷卡账号","商店电话","信用卡开户行名称"});
//        map2.put("fields", new String[]{"invoiceOrder", "companyName", "taxNumber", "accountBank", "companyAddress","bankNumber","companyTelephone","accountName"});

        InvoiceOrder invoiceOrder =  InvoiceOrder.builder()
                .invoiceOrder("1234")
                .accountName("china")
                .companyName("kc")
                .taxNumber("kok").build();
        invoiceOrders1.add(invoiceOrder);

        return invoiceOrders1;
    }
}
