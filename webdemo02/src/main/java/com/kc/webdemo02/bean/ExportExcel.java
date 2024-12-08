package com.kc.webdemo02.bean;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * @Description 通用导出excel
 * @param <T>
 */
@Data
public class ExportExcel<T> extends ExcelModel {
    /**
     *
     * @Title: exportExcel
     * @Description: 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符合一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * @param workbook 工作簿
     * @param title 标题
     * @param headers 统计之类需要用到的数据
     * @param header 表格属性列名数组
     * @param headerRow 列名需要占用多少行
     * @param dataset
     *        需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据
     *        )
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param arr 需要统计的列
     * @param mergedRegion 需要合并的单元格
     * @param headerStyle 标题列名样式
     * @param textStyle 内容样式
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     * @return: void
     */
    public void exportExcel(ExcelModel excelModel, Collection<T> dataset) {

        HSSFSheet sheet = excelModel.getSheet();
        // 产生表格标题行
        HSSFRow row = null;
        // 创建标题行
        setHeaderRows(sheet, excelModel.getHeaderRow());
        for (short cell = 0; cell < excelModel.getHeader().length; cell++) {
            // 根据数据的宽度改变excel单元格宽度
            sheet.setColumnWidth(cell, excelModel.getHeader()[cell].toString().length() * 600);
            if (excelModel.getHeaders() != null)
                if (excelModel.getHeader()[cell].toString().length() < excelModel.getHeaders()[cell]
                        .toString().length()) {
                    sheet.setColumnWidth(cell, excelModel.getHeaders()[cell].toString().length() * 600);
                }
            // 将样式,数据 插入到表格中
            setStyleValue(excelModel.getTitle(), excelModel.getTitles(), sheet, cell,
                    excelModel.getHeader()[cell], excelModel.getHeaders(), excelModel.getHeaderStyle(),
                    excelModel.getHeaderRow());
        }
        excelModel.setSheet(sheet);
        excelModel.setIndex(excelModel.getHeaderRow() - 1);
        // 遍历集合数据，产生数据行
        excelModel = insertData(excelModel, dataset);
        // 合并单元格
        addMergedRegion(sheet, excelModel.getIndex(), excelModel.getMergedRegion(),
                excelModel.getHeader().length);
        if(excelModel.getArr() != null){
            // 产生表格最后一行
            row = sheet.createRow(excelModel.getIndex() + 1);
            // 根据总列数循环创建单元格 lastHeaders
            createCell(row, excelModel.getHeaderStyle(), excelModel.getHeaders(),
                    excelModel.getTotalCell(), excelModel.getTitle());
            // 统计列字段
//      statisticalColumn(excelModel);
        }
    /*
    if (excelModel.getBase64s() != null) {
      // 画图的顶级管理器
      HSSFPatriarch patriarch = excelModel.getSheet().createDrawingPatriarch();
      // 获取图片64码
      String[] base64s = excelModel.getBase64s();
      int k = 25;
      // 保存图片到excel
      for (int i = base64s.length; i >= 1; i--) {
        String imgPath =
            ExcelUtil.generateImage(base64s[base64s.length - i],
                (base64s.length - i) + excelModel.getTitle() + ".png");
        ExcelUtil.saveMulImage(excelModel.getWorkbook(), sheet, patriarch, imgPath, 0, 0, 0, 0,
            Short.parseShort(String.valueOf((1 + excelModel.getTotalCell()))), // 哪列开始
            (base64s.length - i) * k, // 哪行开始
            (short) (excelModel.getTotalCell() + 15), // 哪列结束
            (1 + base64s.length - i) * k // 哪行结束
        );
        // k -= 5;
      }
    }
    */
    }

    /**
     *
     * @Title: insertData
     * @Description: 插入数据
     * @param excelModel
     * @param dataset
     * @return: ExcelModel
     */
    public ExcelModel insertData(ExcelModel excelModel, Collection<T> dataset) {
        Iterator<T> it = dataset.iterator();
        int index = excelModel.getIndex();
        int totalCell = excelModel.getTotalCell();
        HSSFRow row = null;
        HSSFSheet sheet = excelModel.getSheet();
        HSSFCellStyle textStyle = excelModel.getTextStyle();
        String[] headers = excelModel.getHeaders();
        Map<String, short[]> mergedRegion = excelModel.getMergedRegion();
        int flag = excelModel.getFlag();
        while (it.hasNext()) {
            index++;
            totalCell = 0;
            row = sheet.createRow(index);
            T t = it.next();
            String[] fs = excelModel.getFields();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            if(t instanceof Map) {//如果传进来的data是map类型
                for(String field : fs) {
                    Object value = ((Map) t).get(field);
                    HSSFCell cell = row.createCell(totalCell);
                    totalCell++;
                    cell.setCellStyle(textStyle);
                    boolean boo = setValue(value, excelModel, cell, totalCell, sheet, row, field);
                    if(!boo) {
                        totalCell--;
                    }
                }

            }else {
                List<Field> fieldList = new ArrayList<Field>();
                if(fs != null) {
                    for(String fieldName : fs) {
                        for(Field field : fields) {
                            if(field.getName().equals(fieldName)) {
                                fieldList.add(field);
                            }
                        }
                    }
                    fields = fieldList.toArray(new Field[0]);
                }
                f1: for (short i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    String fieldName = field.getName();
                    if (excelModel.getFilter() != null)
                        for (int y = 0; y < excelModel.getFilter().length; y++) {
                            String str = excelModel.getFilter()[y];
                            if (fieldName.equals(str)) {
                                continue f1;
                            }
                        }
                    String getMethodName =
                            "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    HSSFCell cell = row.createCell(totalCell);
                    totalCell++;
                    cell.setCellStyle(textStyle);
                    try {
                        Class<? extends Object> tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                        Object value = getMethod.invoke(t, new Object[] {});
                        boolean boo = setValue(value, excelModel, cell, totalCell, sheet, row, fieldName);
                        if(!boo) {
                            totalCell--;
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } finally {
                        // 清理资源
                    }
                }
            }
        }
        excelModel.setIndex(index);
        excelModel.setTotalCell(totalCell);
        excelModel.setFlag(flag);
        excelModel.setMergedRegion(mergedRegion);
        return excelModel;
    }
    public boolean setValue(Object value, ExcelModel excelModel, HSSFCell cell, int totalCell, HSSFSheet sheet, HSSFRow row, String fieldName ) {
        if (value != null) {
            if (excelModel.getHeaders() != null) {
                if (value.toString().length() > excelModel.getHeader()[totalCell - 1].toString()
                        .length()
                        && value.toString().length() > excelModel.getHeaders()[totalCell - 1].toString()
                        .length()) {
                    // 根据数据的宽度改变excel单元格宽度
                    sheet.setColumnWidth(totalCell - 1, (value.toString().length() * 600) > 65280 ? 20000 : value.toString().length() * 600);
                }
            } else if (value.toString().length() > excelModel.getHeader()[totalCell - 1].toString()
                    .length()) {
                sheet.setColumnWidth(totalCell - 1, (value.toString().length() * 600) > 65280 ? 20000 : value.toString().length() * 600);
            }
        } else {
            row.removeCell(cell);
            return false;
        }
        // 判断value值的类型,进行类型转换,插入到excel中
        typeConversion(excelModel.getWorkbook(), value, cell, fieldName);
        return true;
    }

}