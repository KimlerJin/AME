//package com.ame.util.excel;
//
//import com.ame.meperframework.core.exception.PlatformException;
//import org.apache.poi.EncryptedDocumentException;
//import org.apache.poi.ss.usermodel.*;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ExcelParser {
//    private List<Object> data;
//
//    private Map<String, List<Object>> dataMap;
//
//    private Class<?> type;
//    private String[] colKeys;
//    private BaseRowPacker<?> packer;
//    private Workbook book;
//    private InputStream in;
//    private int offset;
//
//    public ExcelParser(InputStream in, Class<?> type, String[] colKeys, BaseRowPacker<?> packer) {
//        this.in = in;
//        this.type = type;
//        this.colKeys = colKeys;
//        this.packer = packer;
//        try {
//            this.book = WorkbookFactory.create(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (EncryptedDocumentException e) {
//            e.printStackTrace();
//        }
//        this.offset = 0;
//    }
//
//    public ExcelParser(InputStream in, Class<?> type, String[] colKeys, BaseRowPacker<?> packer, int offset) {
//        this.in = in;
//        this.type = type;
//        this.colKeys = colKeys;
//        this.packer = packer;
//        dataMap = new HashMap<>();
//        try {
//            this.book = WorkbookFactory.create(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (EncryptedDocumentException e) {
//            e.printStackTrace();
//        }
//        this.offset = offset;
//    }
//
//    public ExcelParser(InputStream in) {
//        this.in = in;
//        try {
//            this.book = WorkbookFactory.create(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (EncryptedDocumentException e) {
//            e.printStackTrace();
//        }
//        this.offset = 0;
//    }
//
//    public static String getJavaValue(Cell cell) {
//        if (cell == null) {
//            return null;
//        }
//        String o;
//        CellType cellType = cell.getCellType();
//        switch (cellType) {
//            case BLANK:
//                o = "";
//                break;
//            case BOOLEAN:
//                o = cell.getBooleanCellValue() + "";
//                break;
//            case ERROR:
//                o = "Bad value!";
//                break;
//            case NUMERIC:
//                o = cell.getNumericCellValue() + "";
//                break;
//            case FORMULA:
//                o = cell.getNumericCellValue() + "";
//                break;
//            default:
//                o = cell.getStringCellValue() + "";
//        }
//        return o;
//    }
//
//    public Map<String, List<Object>> getDataMap() {
//        return dataMap;
//    }
//
//    public Object instanceObj() {
//        if (type == null || type.equals(Map.class)) {
//            return new HashMap<String, Object>();
//        } else {
//            try {
//                return type.newInstance();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public Workbook getBook() {
//        return book;
//    }
//
//    public List<?> parserSheets(int sheetsLength) {
//        List<Object> list = new ArrayList<>();
//        for (int i = 0; i < sheetsLength; i++) {
//            list.addAll(this.parser(i));
//        }
//        return list;
//    }
//
//    public Map<String, List<Object>> parserMap(int sheetsLength) {
//        for (int i = 0; i < sheetsLength; i++) {
//            this.parserToMap(i);
//        }
//        return dataMap;
//    }
//
//    @SuppressWarnings("unchecked")
//    public Map<String, List<Object>> parserToMap(int sheetNum) {
//        List<Object> list = (List<Object>)this.parser(sheetNum);
//        Integer key = new Integer(sheetNum);
//        dataMap.put(key.toString(), list);
//        return dataMap;
//    }
//
//    public List<?> parser(int sheetNum) {
//
//        Sheet sht0 = book.getSheetAt(sheetNum);
//
//        data = new ArrayList<Object>();
//        //
//        if (packer == null) {
//            packer = initMapPacker();
//        }
//        int rowNum = 0;
//        for (Row r : sht0) {
//            rowNum++;
//            if (rowNum > offset) {
//                try {
//                    // 创建实体类
//                    Object obj = packer.getInstance();
//                    boolean success = true;
//                    // 取出当前行第i个单元格数据，并封装在info实体stuName属性上
//                    for (int i = 0; i < colKeys.length; i++) {
//                        success = packer.packing(obj, colKeys[i], getJavaValue(r.getCell(i)), rowNum);
//                        if (success == false)
//                            break;
//                    }
//                    if (success)
//                        data.add(obj);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    if (e instanceof java.time.format.DateTimeParseException) {
//                        // throw new PlatformException(ErrorConstants.UTIL_TIME_FORMAT_ERROR, "time format error");
//                    }
//                    throw new PlatformException(e.getMessage());
//                }
//            }
//        }
//        return data;
//    }
//
//    public BaseRowPacker<Map<String, Object>> initMapPacker() {
//        return new BaseRowPacker<Map<String, Object>>() {
//            @Override
//            public Map<String, Object> getInstance() {
//                return new HashMap<String, Object>();
//            }
//
//            @SuppressWarnings("unchecked")
//            @Override
//            public boolean packing(Object t, String key, String text, int row) {
//                ((Map<String, Object>)t).put(key, text);
//                return true;
//            }
//        };
//    }
//
//    public String[] getColKeys() {
//        return colKeys;
//    }
//
//    public void setColKeys(String[] colKeys) {
//        this.colKeys = colKeys;
//    }
//
//    public BaseRowPacker<?> getPacker() {
//        return packer;
//    }
//
//    public void setPacker(BaseRowPacker<?> packer) {
//        this.packer = packer;
//    }
//
//    public InputStream getIn() {
//        return in;
//    }
//
//    public void setIn(InputStream in) {
//        this.in = in;
//    }
//
//    public List<Object> getData() {
//        return data;
//    }
//
//    public void setData(List<Object> data) {
//        this.data = data;
//    }
//
//    public Class<?> getType() {
//        return type;
//    }
//
//    public void setType(Class<?> type) {
//        this.type = type;
//    }
//}
