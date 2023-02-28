package com.hanssem.remodeling.content.common.util;

import com.hanssem.remodeling.content.admin.controller.goods.response.GoodsListResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ContentAdminExcelUtility {

    public static File exportExcel(String path, List<String> columns, List<GoodsListResponse> dataRows) {

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        workbook.setCompressTempFiles(true);
        SXSSFSheet sheet = workbook.createSheet();
        sheet.setRandomAccessWindowSize(500);
        setHeader(columns, sheet);

        for (int i = 0; i < dataRows.size(); i++) {

            int cellNum = 0;
            Row createdRow = sheet.createRow(i + 1);
            GoodsListResponse dataRow = dataRows.get(i);

            createdRow.createCell(cellNum++).setCellValue(dataRow.getId()); // 상품 번호
            createdRow.createCell(cellNum++).setCellValue(dataRow.getGoodsName()); // 상품명
            createdRow.createCell(cellNum++).setCellValue(dataRow.getImageUrl()); // 이미지URL
            createdRow.createCell(cellNum++).setCellValue(dataRow.getGoodsStateCodeName()); // 상품상태
            createdRow.createCell(cellNum++).setCellValue(dataRow.getGoodsBadgeTypeCodeName()); // 상품뱃지유형
            createdRow.createCell(cellNum++).setCellValue(dataRow.getStandardCategoryNo()); // 규격카테고리NO
            createdRow.createCell(cellNum++).setCellValue(String.valueOf(dataRow.getEventYn())); // 이벤트여부
            createdRow.createCell(cellNum++).setCellValue(String.valueOf(dataRow.getConsultRequestYn())); // 상담신청여부
            createdRow.createCell(cellNum++).setCellValue(String.valueOf(dataRow.getDisplayYn())); // 디스플레이여부
            createdRow.createCell(cellNum++).setCellValue(String.valueOf(dataRow.getVrYn())); // VR여부
            createdRow.createCell(cellNum++).setCellValue(dataRow.getStandardCategoryName()); // 규격카테고리명
            createdRow.createCell(cellNum++).setCellValue(dataRow.getStyleCode()); // 스타일코드
        }

//        final String filePath = String.format(path.concat("/excel.xlsx"));
        final String filePath = String.format(path.concat(String.format("/excel-%d.xlsx", System.currentTimeMillis())));
        final File pathFile = new File(path);

        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {

            workbook.write(fileOutputStream);
            return new File(filePath);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public static void setHeader(List<String> columns, Sheet sheet) {

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.size(); i++) {
            headerRow.createCell(i).setCellValue(columns.get(i));
        }
    }
}
