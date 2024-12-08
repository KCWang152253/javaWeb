package com.kc.webdemo01.bean;

import lombok.Data;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @Description 通用导出excel
 */
@Data
public class ExcelModel {
    /**
     * 工作簿
     */
    private HSSFWorkbook workbook;
    /**
     * 显示的标题
     */
    private String titles;
    /**
     * 标题
     */
    private String title;
    /**
     * 统计之类需要用到的数据
     */
    private String[] headers;
    /**
     * 表格属性列名
     */
    private String[] header;
    /**
     * 列名需要占用多少行
     */
    private Integer headerRow;
    /**
     * 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     */
    private OutputStream out;
    /**
     * 需要统计的列
     */
    private String[] arr;
    /**
     * 从哪列开始统计
     */
    private Integer beginArr;
    /**
     * 需要合并的单元格
     */
    private Map<String, short[]> mergedRegion;
    /**
     * 列名样式
     */
    private HSSFCellStyle headerStyle;
    /**
     * 内容样式
     */
    private HSSFCellStyle textStyle;
    /**
     * 行索引
     */
    private Integer index;
    /**
     * 总列数
     */
    private Integer totalCell = 0;
    /**
     * 行对象
     */
    private HSSFRow row;
    /**
     * 表格对象
     */
    private HSSFSheet sheet;
    /**
     * 标记
     */
    private Integer flag = 1;
    /**
     * 图片64码
     */
    private String[] base64s;
    /**
     * 过滤字段
     */
    private String[] filter;
    /**
     * 自定义字段顺序
     */
    private String[] fields;

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public ExcelModel() {}

    public ExcelModel(HSSFWorkbook workbook, String title, String[] fields, String[] headers, String[] header,
                      Integer headerRow, OutputStream out, String[] arr, Integer beginArr,
                      Map<String, short[]> mergedRegion, HSSFCellStyle headerStyle, HSSFCellStyle textStyle) {
        this.workbook = workbook;
        this.title = title;
        this.fields = fields;
        this.headers = headers;
        this.header = header;
        this.headerRow = headerRow;
        this.out = out;
        this.arr = arr;
        this.beginArr = beginArr;
        this.mergedRegion = mergedRegion;
        this.headerStyle = headerStyle;
        this.textStyle = textStyle;
    }

    public ExcelModel(HSSFWorkbook workbook, String title, String[] fields, String[] headers, String[] header,
                      Integer headerRow, OutputStream out, String[] arr, Map<String, short[]> mergedRegion,
                      HSSFCellStyle headerStyle, HSSFCellStyle textStyle) {
        this.workbook = workbook;
        this.title = title;
        this.fields = fields;
        this.headers = headers;
        this.header = header;
        this.headerRow = headerRow;
        this.out = out;
        this.arr = arr;
        this.mergedRegion = mergedRegion;
        this.headerStyle = headerStyle;
        this.textStyle = textStyle;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public Integer getHeaderRow() {
        return headerRow;
    }

    public void setHeaderRow(Integer headerRow) {
        this.headerRow = headerRow;
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    public Map<String, short[]> getMergedRegion() {
        return mergedRegion;
    }

    public void setMergedRegion(Map<String, short[]> mergedRegion) {
        this.mergedRegion = mergedRegion;
    }

    public HSSFCellStyle getHeaderStyle() {
        return headerStyle;
    }

    public void setHeaderStyle(HSSFCellStyle headerStyle) {
        this.headerStyle = headerStyle;
    }

    public HSSFCellStyle getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(HSSFCellStyle textStyle) {
        this.textStyle = textStyle;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getTotalCell() {
        return totalCell;
    }

    public void setTotalCell(Integer totalCell) {
        this.totalCell = totalCell;
    }

    public HSSFRow getRow() {
        return row;
    }

    public void setRow(HSSFRow row) {
        this.row = row;
    }

    public HSSFSheet getSheet() {
        return sheet;
    }

    public void setSheet(HSSFSheet sheet) {
        this.sheet = sheet;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String[] getBase64s() {
        return base64s;
    }

    public void setBase64s(String[] base64s) {
        this.base64s = base64s;
    }

    public Integer getBeginArr() {
        return beginArr;
    }

    public void setBeginArr(Integer beginArr) {
        this.beginArr = beginArr;
    }

    public String[] getFilter() {
        return filter;
    }

    public void setFilter(String[] filter) {
        this.filter = filter;
    }

    /**
     *
     * @Title: setStyleValue
     * @Description: TODO
     * @param title 文件名称
     * @param sheet 表格对象
     * @param cell 单元格位置
     * @param header 表格属性列名数组
     * @param headers 统计之类需要用到的信息
     * @param style 样式
     * @param headerRow 列名需要占用多少行
     * @return: void
     */
    public void setStyleValue(String title, String titles, HSSFSheet sheet, int cell, String header,
                              String[] headers, HSSFCellStyle style, int headerRow) {
        for (int row = 0; row < headerRow; row++) {
            sheet.getRow(row).createCell(cell).setCellStyle(style);
            sheet.getRow(row).getCell(cell).setCellValue(header);
            if(row == 0)
                if(titles != null && titles.length() > 1){
                    sheet.getRow(row).getCell(cell).setCellValue(titles);
                }
            sheet.getRow(row).setHeight((short) 400);
        }
    }

    /**
     *
     * @Title: setComment
     * @Description: 生成注释内容
     * @param sheet 表格对象
     * @param format 定义注释的大小和位置
     * @param note 注释内容
     * @param author 作者
     * @return: void
     */
    public void setComment(HSSFSheet sheet, HSSFClientAnchor format, String note, String author) {
        // 创建HSSFPatriarch对象,HSSFPatriarch是所有注释的容器.
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(format);
        // 设置注释内容
        comment.setString(new HSSFRichTextString(note));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor(author);
    }

    // 生成标题样式
    public HSSFCellStyle initStyle(HSSFWorkbook workbook, short fillForegroundColor,
                                   short fillPattern, short borderBottom, short borderLeft, short borderRight, short borderTop,
                                   short alignment) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(fillForegroundColor);
        style.setFillPattern(fillPattern);
        style.setBorderBottom(borderBottom);
        style.setBorderLeft(borderLeft);
        style.setBorderRight(borderRight);
        style.setBorderTop(borderTop);
        style.setAlignment(alignment);
        return style;
    }

    public HSSFCellStyle initStyle(HSSFWorkbook workbook, short fillPattern, short borderBottom,
                                   short borderLeft, short borderRight, short borderTop, short alignment) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(fillPattern);
        style.setBorderBottom(borderBottom);
        style.setBorderLeft(borderLeft);
        style.setBorderRight(borderRight);
        style.setBorderTop(borderTop);
        style.setAlignment(alignment);
        return style;
    }

    // 生成内容样式
    public HSSFCellStyle initStyle(HSSFWorkbook workbook, short fillForegroundColor,
                                   short fillPattern, short borderBottom, short borderLeft, short borderRight, short borderTop,
                                   short alignment, short verticalAlignment) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(fillForegroundColor);
        style.setFillPattern(fillPattern);
        style.setBorderBottom(borderBottom);
        style.setBorderLeft(borderLeft);
        style.setBorderRight(borderRight);
        style.setBorderTop(borderTop);
        style.setAlignment(alignment);
        style.setVerticalAlignment(verticalAlignment);
        return style;
    }

    public HSSFCellStyle cellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle textStyle = workbook.createCellStyle();
        textStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        textStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        textStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        textStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        textStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        textStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        textStyle.setWrapText(true);
        return textStyle;
    }

    // 生成标题字体
    public void setFont(HSSFWorkbook workbook, HSSFCellStyle style, short color, short fontHeight,
                        short boldweight) {
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(color);
        font.setFontHeightInPoints(fontHeight);
        font.setBoldweight(boldweight);
        // 把字体应用到当前的样式
        style.setFont(font);
    }

    public void setFont(HSSFWorkbook workbook, HSSFCellStyle style, short fontHeight, short boldweight) {
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontHeight);
        font.setBoldweight(boldweight);
        // 把字体应用到当前的样式
        style.setFont(font);
    }

    // 生成内容字体
    public void setFont(HSSFWorkbook workbook, HSSFCellStyle style, short boldweight) {
        // 生成另一个字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(boldweight);
        // 把字体应用到当前的样式
        style.setFont(font);
    }

    /**
     *
     * @Title: dataCast
     * @Description: 将上一行的数据移到新创建的行
     * @param totalCell 单元格位置
     * @param sheet 表格对象
     * @param index 行位置
     * @param i 需要添加headers的位置
     * @param headers 统计之类需要用到的数据
     * @param textStyle 内容样式
     * @return: int
     */
    public int dataCast(int totalCell, HSSFSheet sheet, int index, int i, String headers,
                        HSSFCellStyle textStyle) {
        index++;
        // 创建一行
        HSSFRow row = sheet.createRow(index);
        // 遍历数据
        for (int k = 0; k < totalCell; k++) {
            // 取得上一行单元格数据
            HSSFCell h = sheet.getRow(index - 1).getCell(k);
            // 创建单元格
            HSSFCell hs = row.createCell(k);
            // 判断数据是否numeric类型
            if (h.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                // 返回lang类型 长度过大会变成科学计数法形式,所以先返回lang类型
                long longVal = Math.round(h.getNumericCellValue());
                // 强转为int类型添加到单元格中
                hs.setCellValue(new Long(longVal).intValue());
            } else {
                // 否则都将数据转为string类型添加到单元格中
                hs.setCellValue(h.toString());
            }
            // 添加内容样式
            hs.setCellStyle(textStyle);
            // 清空上一行数据
            h.setCellValue("");
            // i: 在什么单元格位置添加,headers: 如果需要在上一行添加统计之类需要用到的数据
            h.setCellValue(k == i ? headers == null ? "" : headers : "");
        }
        return index;
    }

    /**
     *
     * @Title: deleteCharAt
     * @Description: 将需要求和的列名+行数,添加到excel 格式 A1+A2+A3
     * @param sheet 表格对象
     * @param row 行位置
     * @param sheetCell 单元格
     * @param field 需要求和的字符串
     * @return: void
     */
    public void deleteCharAt(HSSFSheet sheet, int row, int sheetCell, StringBuffer field) {
        if (field.length() > 1) {
            field.deleteCharAt(0);
            sheet.getRow((row)).getCell(sheetCell).setCellFormula(field.toString());
        }
    }

    /**
     *
     * @Title: setSubtotal
     * @Description: 将上一次的行位置和当前的行位置之间的行数遍历添加成需要求和的字符串,列名+行数
     * @param sheet 表格对象
     * @param row 上一次的行位置
     * @param k 当前的行位置
     * @param p 列位置
     * @param sheetCell 单元格
     * @param str 列名
     * @param headers 统计之类需要用到的数据
     * @return: void
     */
    public void setSubtotal(HSSFSheet sheet, Integer row, int k, int p, int sheetCell, String str,
                            String headers) {
        StringBuffer field = new StringBuffer("");
        if (row != null) {
            for (int i = row - 1; i > k; i--) {
                if (headers.equals(sheet.getRow(i - 1).getCell(sheetCell).toString())) {
                    continue;
                }
                field.append("+" + str + i).toString();
            }
            deleteCharAt(sheet, row - 1, p, field);
        }
    }

    /**
     *
     * @Title: setInsideRow
     * @Description: TODO
     * @param insideRow
     * @param str 列名
     * @return: StringBuffer
     */
    public StringBuffer setInsideRow(Integer insideRow, String str) {
        StringBuffer field = new StringBuffer("");
        for (int i = insideRow - 1; i > 1; i--) {
            field.append("+" + str + i).toString();
        }
        return field;
    }

    /**
     *
     * @Title: createCell
     * @Description: 根据总列数循环创建单元格
     * @param row
     * @param headerStyle
     * @param headers
     * @param totalCell
     * @return: void
     */
    public void createCell(HSSFRow row, HSSFCellStyle headerStyle, String[] headers, int totalCell,
                           String title) {
        for (int i = 0; i < totalCell; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            if (headers != null && headers.length > 1 && i == 0) {
                cell.setCellValue(headers[2]);
            }
        }
    }

    /**
     *
     * @Title: typeConversion
     * @Description: 判断值的类型,进行类型转换,插入到excel中
     * @param workbook
     * @param value
     * @param cell
     * @return: void
     */
    public void typeConversion(HSSFWorkbook workbook, Object value, HSSFCell cell, String fieldName) {
        String textValue = null;
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            cell.setCellValue(intValue);
        } else if (value instanceof Float) {
            float fValue = (Float) value;
            cell.setCellValue(fValue);
        } else if (value instanceof Double) {
            double dValue = (Double) value / 100;
            cell.setCellValue(dValue);
            if (fieldName.indexOf("Rate") != -1) {
                HSSFCellStyle cellStyle = cellStyle(workbook);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
                cell.setCellStyle(cellStyle);
            }
        } else if (value instanceof Long) {
            long longValue = (Long) value;
            cell.setCellValue(longValue);
        } else {
            // 其它数据类型都当作字符串简单处理
            textValue = value.toString();
            // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
            if (textValue != null) {
                Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                Matcher matcher = p.matcher(textValue);
                if (matcher.matches()) {
                    // 是数字当作double处理
                    cell.setCellValue(Double.parseDouble(textValue));
                } else {
                    HSSFRichTextString richString = new HSSFRichTextString(textValue);
                    HSSFFont font3 = workbook.createFont();
                    font3.setColor(HSSFColor.BLACK.index);
                    richString.applyFont(font3);
                    cell.setCellValue(richString);
                }
            }
        }
    }

    /**
     *
     * @Title: addMergedRegion
     * @Description: 合并单元格
     * @param excelModel
     * @return: void
     */
    public void addMergedRegion(HSSFSheet sheet, int index, Map<String, short[]> mergedRegion,
                                int totalCell) {
        // 合并单元格    [0]:起始行号,[1]:起始列号,[2]:结束行号,[3]:终止列号
        if (!mergedRegion.isEmpty()) {
            for (short[] s : mergedRegion.values()) {
                if (mergedRegion.get("TailCell") != null && mergedRegion.get("TailCell") == s) {
                    CellRangeAddress region = new CellRangeAddress(index + 1, s[0], index + 1, s[1]);
                    sheet.addMergedRegion(region);
                } else {
                    // 如果需要合并所有行,改为100
                    if(s[2] == 100) {
                        sheet.addMergedRegion(new CellRangeAddress(s[0], s[1], index, s[3]));
                        continue;
                    };
                    // 如果需要合并所有列,改为100
                    if (s[3] == 100){
                        sheet.addMergedRegion(new CellRangeAddress(s[0], s[1], s[2], (short) (totalCell - 1)));
                        continue;
                    }
                    sheet.addMergedRegion(new CellRangeAddress(s[0], s[1], s[2], s[3]));

                }
            }
        }
    }

    /**
     *
     * @Title: statisticalColumn
     * @Description: 统计列
     * @param excelModel
     * @return: void
     */
    public void statisticalColumn(ExcelModel excelModel) {
        HSSFSheet sheet = excelModel.getSheet();
        int index = excelModel.getIndex();
        int totalCell = excelModel.getTotalCell();
        // 定义一个空的Excel的列字段
        StringBuffer field = null;
        // 从哪列开始统计
        int p = excelModel.getBeginArr() == null ? 1 : excelModel.getBeginArr();
        // arr:需要统计的列,通过遍历将每列的数据进行统计
        for (String str : excelModel.getArr()) {
            field = new StringBuffer("");
            // 查找打印的数据有多少行
            for (int i = index + 1; i > 2; i--) {
                field.append("+" + str + (i)).toString();
            }
            // 删除前面多出的一个+号
            field.deleteCharAt(0);
            // 如果当前的cell < 总数cell
            if (p < totalCell) {
                // sheet.getRow(index+1).getCell(p).setCellType(HSSFCell.CELL_TYPE_FORMULA);
                sheet.getRow(index + 1).getCell(p).setCellFormula(field.toString());
            }
            p++;
        }


    }

    /**
     *
     * @Title: setHeaderRows
     * @Description: 创建标题行
     * @param sheet
     * @param row
     * @return: void
     */
    public void setHeaderRows(HSSFSheet sheet, int row) {
        for (int i = 0; i < row; i++) {
            sheet.createRow(i);
        }
    }

    public int getbegin(String str) {
        Map<String, Integer> beginArrs = new HashMap<String, Integer>();
        beginArrs.put("A", 1);
        beginArrs.put("B", 2);
        beginArrs.put("C", 3);
        beginArrs.put("D", 4);
        beginArrs.put("E", 5);
        beginArrs.put("F", 6);
        beginArrs.put("G", 7);
        beginArrs.put("H", 8);
        beginArrs.put("I", 9);
        beginArrs.put("J", 10);
        beginArrs.put("K", 11);
        beginArrs.put("L", 12);
        beginArrs.put("M", 13);
        beginArrs.put("N", 14);
        beginArrs.put("O", 15);
        beginArrs.put("P", 16);
        beginArrs.put("Q", 17);
        beginArrs.put("R", 18);
        beginArrs.put("S", 19);
        beginArrs.put("T", 20);
        beginArrs.put("U", 21);
        beginArrs.put("V", 22);
        beginArrs.put("W", 23);
        beginArrs.put("X", 24);
        beginArrs.put("Y", 25);
        beginArrs.put("Z", 26);
        return beginArrs.get(str);
    }
}