package com.kc.webdemo01.controller;

import com.kc.webdemo01.bean.ExportExcel;
import com.kc.webdemo01.service.invoiceOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author KCWang
 * @version 1.0
 * @date 2023/6/24 下午5:06
 */

/**
 * 发票管理
 *
 * @author issuser
 */
@Api(value = "发票", description = "发票操作 API", position = 100, protocols = "http")
@RestController
@RequestMapping(value = "/webcode")
public class InvoiceController {

    private static Logger logger = LoggerFactory.getLogger(InvoiceController.class);


    @Autowired
    invoiceOrderService InvoiceOrderService;

    @ApiOperation(value = "导出操作日志到xls文件", notes = "需要登录")
    @GetMapping("/exporInvoiceOrderXls")
    @ResponseBody
    public Object exporInvoiceOrderXls(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "invoiceOrders") @ApiParam(value = "invoiceOrders") List<String> invoiceOrders) {


        Map<String, Object> map = new HashMap<>();

        response.setContentType("octets/stream");

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("sheetTitle", "消费记录");
        map2.put("header", new String[]{"订单号", "商店名称", "发票批次", "账户名称", "商店地址", "刷卡账号", "商店电话", "信用卡开户行名称"});
        map2.put("fields", new String[]{"invoiceOrder", "companyName", "taxNumber", "accountBank", "companyAddress", "bankNumber", "companyTelephone", "accountName"});

        List<com.kc.webdemo01.bean.InvoiceOrder> invoiceOrderss = InvoiceOrderService.queryInvoiceLists(invoiceOrders, 0, 9999);
        map2.put("data", invoiceOrderss);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map2);

        String title = "日常消费刷卡发票记录";

        Map<String, short[]> mergedRegion = new HashMap<String, short[]>();
        mergedRegion.put("通用标题名称合并", new short[]{0, 0, 0, 100});

        ExportExcel<com.kc.webdemo01.bean.InvoiceOrder> ex = new ExportExcel<>();

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个标题样式
        HSSFCellStyle headerStyle = ex.cellStyle(workbook);
        // 生成一个内容样式
        HSSFCellStyle textStyle = ex.cellStyle(workbook);
        // 生成标题字体
        ex.setFont(workbook, headerStyle, (short) 10, HSSFFont.BOLDWEIGHT_NORMAL);
        // 生成内容字体
        ex.setFont(workbook, textStyle, HSSFFont.BOLDWEIGHT_NORMAL);

        try {
            OutputStream out = response.getOutputStream();
            // 导出文件名称添加当前时间
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            //防止乱码
//            response.addHeader("Access-Control-Expose-Headers", "Content-Type,Content-Disposition");
//            response.addHeader("Content-Disposition",
//                    "attachment;filename=" + URLEncoder.encode(title, "GBK") + date
//                            + ".xls");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String((title + ".xls").getBytes("GB2312"), "ISO8859-1"));


            list.forEach(m -> {
                com.kc.webdemo01.bean.ExcelModel excelModel = new com.kc.webdemo01.bean.ExcelModel();
                excelModel.setWorkbook(workbook);
                excelModel.setTitle(title);
                excelModel.setFields((String[]) m.get("fields"));
                excelModel.setHeader((String[]) m.get("header"));
                excelModel.setHeaderRow(2);
                excelModel.setMergedRegion(mergedRegion);
                excelModel.setHeaderStyle(headerStyle);
                excelModel.setTextStyle(textStyle);
                excelModel.setTitles((String) m.get("sheetTitle"));

                // 生成一页表格
                HSSFSheet sheet = workbook.createSheet((String) m.get("sheetTitle"));
                excelModel.setSheet(sheet);
                ex.exportExcel(excelModel, (Collection<com.kc.webdemo01.bean.InvoiceOrder>) m.get("data"));
            });

            try {
                workbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                out.close();
            }

            map.put("state", "0");
            map.put("message", "导出成功");
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("state", "1");
            map.put("message", e.getMessage());
            return map;
        }

    }

}