package ru.ravnasybullin.DoiReg.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;
import ru.ravnasybullin.DoiReg.dto.Metadata;
import ru.ravnasybullin.DoiReg.models.Article;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    List<Article> getArticles();

    Article checkMetadataAndSaveArticle(Article article, MultipartFile file) throws IOException, InvalidFormatException;

    Metadata getMetadata(MultipartFile file) throws IOException, InvalidFormatException;
}
