package com.hanssem.remodeling.content.admin.service.favorite;

import com.hanssem.remodeling.content.admin.repository.favorite.FavoriteImageRepository;
import com.hanssem.remodeling.content.admin.repository.favorite.FavoriteStyleRepository;
import com.hanssem.remodeling.content.admin.repository.favorite.FavoriteTagRepository;
import com.hanssem.remodeling.content.admin.repository.favorite.FavoriteTagStyleRepository;
import com.hanssem.remodeling.content.admin.service.ExcelImportAbstract;
import com.hanssem.remodeling.content.admin.service.favorite.dto.AddFavoriteImageTagDto;
import com.hanssem.remodeling.content.admin.service.favorite.dto.AddFavoriteStyleDto;
import com.hanssem.remodeling.content.admin.service.favorite.dto.AddFavoriteTagDto;
import com.hanssem.remodeling.content.admin.service.favorite.mapper.FavoriteAdminMapper;
import com.hanssem.remodeling.content.common.util.S3Uploader;
import com.hanssem.remodeling.content.constant.FavoriteTagType;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteImage;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteImageTag;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteStyle;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteStyleImage;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteStyleTag;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteTag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteImportService extends ExcelImportAbstract {

    private static String ORIGIN_DOMAIN = "https://dev-static.remodeling.hanssem.com/";

    private final S3Uploader s3Uploader;
    private final FavoriteTagRepository favoriteTagRepository;
    private final FavoriteTagStyleRepository favoriteTagStyleRepository;
    private final FavoriteStyleRepository favoriteStyleRepository;
    private final FavoriteImageRepository favoriteImageRepository;

    public void importFavorite(MultipartFile file, String extension) throws IOException {
        importFavoriteTag(file, extension);
        importFavoriteStyle(file, extension);
        importFavoriteImageTagMapping(file, extension);
    }

    public void importImage(MultipartFile file, String extension) throws Exception {
        List<AddFavoriteImageTagDto> addFavoriteImageTagDtos = readFavoriteImageTagDto(file, extension);

        for (AddFavoriteImageTagDto imageDto : addFavoriteImageTagDtos) {
            String s3Key = imageDto.getImageName().replace(ORIGIN_DOMAIN, "");
            log.debug("--> {} : {}", imageDto.getImageName(), s3Key);
            s3Uploader.putS3ForUrlImage(imageDto.getImageName(), s3Key);
        }

    }

    /**
     *
     */
    private void importFavoriteTag(MultipartFile file, String extension) throws IOException {

        List<AddFavoriteTagDto> addFavoriteTagDtos = readFavoriteTagDto(file, extension);

        for (AddFavoriteTagDto favoriteTagDto : addFavoriteTagDtos) {
            if (favoriteTagDto.getTagType().equals("STYLE")) {
                FavoriteTag entity = FavoriteTag.builder()
                    .tagType(FavoriteTagType.STYLE)
                    .tagName(favoriteTagDto.getTagName())
                    .imagePath(favoriteTagDto.getImagePath())
                    .etc(favoriteTagDto.getEtc())
                    .build();
                favoriteTagRepository.save(entity);
            } else if (favoriteTagDto.getTagType().equals("COLOR")) {
                FavoriteTag entity = FavoriteTag.builder()
                    .tagType(FavoriteTagType.COLOR)
                    .tagName(favoriteTagDto.getTagName())
                    .imagePath(favoriteTagDto.getImagePath())
                    .etc(favoriteTagDto.getEtc())
                    .build();
                favoriteTagRepository.save(entity);
            } else if (favoriteTagDto.getTagType().equals("KEYWORD")) {
                FavoriteTag entity = FavoriteTag.builder()
                    .tagType(FavoriteTagType.KEYWORD)
                    .tagName(favoriteTagDto.getTagName())
                    .imagePath(favoriteTagDto.getImagePath())
                    .etc(favoriteTagDto.getEtc())
                    .build();
                favoriteTagRepository.save(entity);
            } else if (favoriteTagDto.getTagType().equals("MATERIAL")) {
                FavoriteTag entity = FavoriteTag.builder()
                    .tagType(FavoriteTagType.MATERIAL)
                    .tagName(favoriteTagDto.getTagName())
                    .imagePath(favoriteTagDto.getImagePath())
                    .etc(favoriteTagDto.getEtc())
                    .build();
                favoriteTagRepository.save(entity);
            }
        }
    }

    private void importFavoriteStyle(MultipartFile file, String extension) throws IOException {

        List<AddFavoriteStyleDto> addFavoriteStyleDtos = readFavoriteDto(file, extension);

        List<FavoriteTag> tags = favoriteTagRepository.findAll();

        for (AddFavoriteStyleDto favoriteStyleDto : addFavoriteStyleDtos) {

            FavoriteStyle favoriteStyle = FavoriteAdminMapper.INSTANCE.toFavoriteStyle(favoriteStyleDto);

            //image
            List<FavoriteStyleImage> favoriteStyleImages = new ArrayList<>();
            List<String> images = favoriteStyleDto.getImages();
            for (String image : images) {
                FavoriteStyleImage entity = FavoriteStyleImage.builder().imagePath(image).build();
                favoriteStyleImages.add(entity);
            }
            favoriteStyle.setStyleImages(favoriteStyleImages);

            //tag
            List<FavoriteStyleTag> favoriteStyleTags = new ArrayList<>();
            List<String> strTags = favoriteStyleDto.getTags();
            for (String tag : strTags) {
                Long tagId = getTagId(tags, tag);
                FavoriteStyleTag entity = FavoriteStyleTag.builder().favoriteTagId(tagId).build();
                favoriteStyleTags.add(entity);
            }
            favoriteStyle.setStyleTags(favoriteStyleTags);

            favoriteStyle.association();
            favoriteStyleRepository.save(favoriteStyle);
        }

    }

    private void importFavoriteImageTagMapping(MultipartFile file, String extension) throws IOException {

        List<AddFavoriteImageTagDto> addFavoriteImageTagDtos = readFavoriteImageTagDto(file, extension);

        List<FavoriteTag> tags = favoriteTagRepository.findAll();

        for (AddFavoriteImageTagDto favoriteImageTagDto : addFavoriteImageTagDtos) {
            FavoriteImage favoriteImage = FavoriteImage.builder().imagePath(favoriteImageTagDto.getImageName()).build();
            favoriteImageRepository.save(favoriteImage);

            //style
            for (String item : favoriteImageTagDto.getStyles()) {
                Long tagId = getTagId(tags, item);

//                FavoriteTag favoriteTag = tags.stream()
//                    .filter(x -> x.getTagName().equals(item)).findFirst().get();

                FavoriteImageTag favoriteImageTag = FavoriteImageTag.builder()
                    .favoriteTagId(tagId)
                    .favoriteImageId(favoriteImage.getId())
                    .build();
                favoriteTagStyleRepository.save(favoriteImageTag);
            }

            //color
            for (String item : favoriteImageTagDto.getColors()) {
                FavoriteTag favoriteTag = tags.stream()
                    .filter(x -> x.getTagName().equals(item)).findFirst().get();

                FavoriteImageTag favoriteImageTag = FavoriteImageTag.builder()
                    .favoriteTagId(favoriteTag.getId())
                    .favoriteImageId(favoriteImage.getId())
                    .build();
                favoriteTagStyleRepository.save(favoriteImageTag);
            }

            //keyword
            for (String item : favoriteImageTagDto.getKeywords()) {
                FavoriteTag favoriteTag = tags.stream()
                    .filter(x -> x.getTagName().equals(item)).findFirst().get();

                FavoriteImageTag favoriteImageTag = FavoriteImageTag.builder()
                    .favoriteTagId(favoriteTag.getId())
                    .favoriteImageId(favoriteImage.getId())
                    .build();
                favoriteTagStyleRepository.save(favoriteImageTag);
            }
        }

    }

    private Long getTagId(List<FavoriteTag> tags, String item) {
        Long tagId = 0L;

        for (FavoriteTag tag : tags) {
            if (tag.getTagName().equals(item)) {
                log.debug("--> {}", tag.getId());
                tagId = tag.getId();
                break;
            }
        }
        return tagId;
    }

    /**
     * 0.인테리어취향테스트결과데이터 시트
     */
    private List<AddFavoriteStyleDto> readFavoriteDto(MultipartFile file, String extension) throws IOException {
        List<AddFavoriteStyleDto> dataList = new ArrayList<>();
        Workbook workbook = null;

        if (extension.equals("xlsx") || extension.equals("tsv")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet;
        worksheet = workbook.getSheetAt(0);

        for (int i = 2; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
            Row row = worksheet.getRow(i);
            AddFavoriteStyleDto data = new AddFavoriteStyleDto();

            if (readString(row, 0).isEmpty()) continue;

            data.setManageName(readString(row, 0));
            data.setStyleName(readString(row, 1));
            data.setStyleCode(readString(row, 2));
            data.setDescription(readString(row, 3));

            //image
            List<String> images = new ArrayList<>();
            for (int y = 4; y <= 7; y++) {
                String imagePath = readString(row, y).replace("\n", "").replace(ORIGIN_DOMAIN, "");
                images.add(imagePath);
            }
            data.setImages(images);

            //tag
            List<String> tags = new ArrayList<>();
            for (int y = 12; y <= 17; y++) {
                tags.add(readString(row, y));
            }
            data.setTags(tags);

            dataList.add(data);
        }

        return dataList;
    }

    /**
     * 2.스타일태그 시트
     */
    private List<AddFavoriteTagDto> readFavoriteTagDto(MultipartFile file, String extension) throws IOException {
        List<AddFavoriteTagDto> dataList = new ArrayList<>();
        Workbook workbook = null;

        if (extension.equals("xlsx") || extension.equals("tsv")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet;
        worksheet = workbook.getSheetAt(2);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
            Row row = worksheet.getRow(i);
            AddFavoriteTagDto data = new AddFavoriteTagDto();

            data.setTagName(readString(row, 0));
            data.setTagType(readString(row, 1));
            String imagePath = readString(row, 2).replace("'", "").replace(ORIGIN_DOMAIN, "");
            data.setImagePath(imagePath);
            data.setEtc(readString(row, 3));

            dataList.add(data);
        }

        return dataList;
    }

    /**
     * 1.이미지데이터 시트
     */
    private List<AddFavoriteImageTagDto> readFavoriteImageTagDto(MultipartFile file, String extension) throws IOException {
        List<AddFavoriteImageTagDto> dataList = new ArrayList<>();
        Workbook workbook = null;

        if (extension.equals("xlsx") || extension.equals("tsv")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet;
        worksheet = workbook.getSheetAt(1);

        for (int i = 2; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
            Row row = worksheet.getRow(i);
            AddFavoriteImageTagDto data = new AddFavoriteImageTagDto();

//            if (readString(row, 0).isEmpty()) continue;

            String imagePath = readString(row, 1).replace("'", "").replace(ORIGIN_DOMAIN, "");
            data.setImageName(imagePath);

            int j = 0;
            List<String> styles = new ArrayList<>();
            for(j = 2; j <= 6; j++) {
                if (readString(row, j).isEmpty()) break;
                styles.add(readString(row, j));
            }
            data.setStyles(styles);

            List<String> colors = new ArrayList<>();
            for(j = 7; j <= 11; j++) {
                if (readString(row, j).isEmpty()) break;
                colors.add(readString(row, j));
            }
            data.setColors(colors);

            List<String> keywords = new ArrayList<>();
            for(j = 12; j <= 16; j++) {
                if (readString(row, j).isEmpty()) break;
                keywords.add(readString(row, j));
            }
            data.setKeywords(keywords);

            dataList.add(data);
        }

        return dataList;
    }


}
