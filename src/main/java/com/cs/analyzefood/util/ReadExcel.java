package com.cs.analyzefood.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadExcel {
    // 总行数
    private int totalRows = 0;
    // 总条数
    private int totalCells = 0;
    // 错误信息接收器
    private String errorMsg;


    public ReadExcel() {
    }

    // 获取总行数
    public int getTotalRows() {
        return totalRows;
    }

    // 获取总列数
    public int getTotalCells() {
        return totalCells;
    }

    // 获取错误信息
    public String getErrorInfo() {
        return errorMsg;
    }

    /**
     * 读EXCEL文件，获取信息集合
     *
     * @param mFile
     * @return
     */
    public List<Map<String, Object>> getExcelInfo(MultipartFile mFile) {
        String fileName = mFile.getOriginalFilename();// 获取文件名
//		List<Map<String, Object>> userList = new LinkedList<Map<String, Object>>();
        try {
            if (!validateExcel(fileName)) {// 验证文件名是否合格
                return null;
            }
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            return createExcel(mFile.getInputStream(), isExcel2003);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据excel里面的内容读取客户信息
     * @param is      输入流
     * @param isExcel2003   excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> createExcel(InputStream is, boolean isExcel2003) {
        try {
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            return readExcelValue(wb);// 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Excel里面客户的信息
     * @param wb
     * @return
     */
    private List<Map<String, Object>> readExcelValue(Workbook wb) {
        DecimalFormat df = new DecimalFormat("0.00");
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<Map<String, Object>> foodList = new ArrayList<>();
        // 循环Excel行数
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            // 循环Excel的列
            Map<String, Object> map = new HashMap<>();
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (c == 0) {
                        // 如果是纯数字,比如你写的是25,cell.getNumericCellValue()获得是25.0,通过截取字符串去掉.0获得25
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("foodName", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("foodName", cell.getStringCellValue());// 名称
                        }
                    }
                    if (c == 1) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("eat_part",value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
//                            map.put("eat_part",df.format(Double.valueOf(cell.getStringCellValue())));// 可食部分
                            map.put("eat_part",cell.getStringCellValue());// 可食部分
                        }
                    }
                    if (c == 2) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("energy", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("energy", df.format(Double.parseDouble(cell.getStringCellValue())));// 能量
//                            map.put("energy", cell.getStringCellValue());// 能量
                        }
                    }
                    if (c == 3) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("moisture", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("moisture", df.format(Double.parseDouble(cell.getStringCellValue())));// 水分
//                            map.put("moisture", cell.getStringCellValue());// 水分
                        }
                    }
                    if (c == 4) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("protein",value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("protein",df.format(Double.parseDouble(cell.getStringCellValue())));// 蛋白质
//                            map.put("protein",cell.getStringCellValue());// 蛋白质
                        }
                    }
                    if (c == 5) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("fat", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("fat", df.format(Double.parseDouble(cell.getStringCellValue())));// 脂肪
//                            map.put("fat", cell.getStringCellValue());// 脂肪
                        }
                    }
                    if (c == 6) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("fiber", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("fiber", df.format(Double.parseDouble(cell.getStringCellValue())));// 纤维
//                            map.put("fiber", cell.getStringCellValue());// 纤维
                        }
                    }
                    if (c == 7) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("carbohydrate",value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("carbohydrate",df.format(Double.parseDouble(cell.getStringCellValue())));// 碳水化合物
//                            map.put("carbohydrate",cell.getStringCellValue());// 碳水化合物
                        }
                    }
                    if (c == 8) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("va", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("va", df.format(Double.parseDouble(cell.getStringCellValue())));
//                            map.put("va", cell.getStringCellValue());
                        }
                    }
                    if (c == 9) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("vb1", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("vb1", df.format(Double.valueOf(cell.getStringCellValue())));
//                            map.put("vb1", cell.getStringCellValue());
                        }
                    }
                    if (c == 10) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("vb2",value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("vb2",df.format(Double.parseDouble(cell.getStringCellValue())));
//                            map.put("vb2",cell.getStringCellValue());
                        }
                    }
                    if (c == 11) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("niacin", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("niacin", df.format(Double.parseDouble(cell.getStringCellValue())));// 烟酸
//                            map.put("niacin", cell.getStringCellValue());// 烟酸
                        }
                    }
                    if (c == 12) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("ve", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("ve", df.format(Double.parseDouble(cell.getStringCellValue())));
//                            map.put("ve", cell.getStringCellValue());
                        }
                    }
                    if (c == 13) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("na",value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("na",df.format(Double.parseDouble(cell.getStringCellValue())));
//                            map.put("na",cell.getStringCellValue());
                        }
                    }
                    if (c == 14) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("ca", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("ca", df.format(Double.parseDouble(cell.getStringCellValue())));
//                            map.put("ca", cell.getStringCellValue());
                        }
                    }
                    if (c == 15) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("fe", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("fe", df.format(Double.parseDouble(cell.getStringCellValue())));
//                            map.put("fe", cell.getStringCellValue());
                        }
                    }
                    if (c == 16) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("typeId",value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
//                            map.put("typeId",df.format(Double.parseDouble(cell.getStringCellValue())));
                            map.put("typeId",cell.getStringCellValue());
                        }
                    }
                    if (c == 17) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("vc", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("vc", df.format(Double.parseDouble(cell.getStringCellValue())));
//                            map.put("vc", cell.getStringCellValue());
                        }
                    }
                    if (c == 18) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            map.put("cholesterol", value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));
                        } else {
                            map.put("cholesterol", df.format(Double.parseDouble(cell.getStringCellValue())));// 胆固醇
//                            map.put("cholesterol", cell.getStringCellValue());// 胆固醇
                        }
                    }
                }
            }
            foodList.add(map);
        }
        return foodList;
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath ==  null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    // @描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }


}
