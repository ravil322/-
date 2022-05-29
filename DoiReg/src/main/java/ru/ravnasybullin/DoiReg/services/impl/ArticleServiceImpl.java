package ru.ravnasybullin.DoiReg.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.ravnasybullin.DoiReg.dto.Metadata;
import ru.ravnasybullin.DoiReg.models.Article;
import ru.ravnasybullin.DoiReg.repositories.ArticleRepository;
import ru.ravnasybullin.DoiReg.services.ArticleService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor

public class ArticleServiceImpl implements ArticleService {

    private static final String[] keyWordsKeyWords = {"key words", "ключевые слова", "keywords"};
    private final ArticleRepository articleRepository;

    @Override
    public List<Article> getArticles() {
        List<Article> articles = articleRepository.findAll();

        return articles;
    }

    @Override
    public Article checkMetadataAndSaveArticle(Article article, MultipartFile file) throws IOException, InvalidFormatException {
        if (!checkArticleMetadata(article)) {
            Metadata metadata = getMetadata(file);
            setMetadataToArticle(article, metadata);
        }
        // todo save file somewhere/ send to crosreff
        return articleRepository.save(article);
    }

    @Override
    public Metadata getMetadata(MultipartFile file) throws IOException, InvalidFormatException {

        InputStream inputStream = file.getInputStream();
        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(inputStream));
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(xdoc);
        XWPFHeader header = policy.getDefaultHeader();
        if (header != null) {
            System.out.println(header.getText());
        }

        XWPFFooter footer = policy.getDefaultFooter();
        if (footer != null) {
            System.out.println(footer.getText());
        }
        List<XWPFParagraph> paragraphList = xdoc.getParagraphs();

        for (int i = 0; i < paragraphList.size(); i++) {
            System.out.println(i);
            XWPFParagraph paragraph = paragraphList.get(i);
            System.out.println(paragraph.getParagraphText());
            System.out.println("********************************************************************");
        }
        return new Metadata(parseAuthors(paragraphList), parseTitle(paragraphList), parseKeyWords(paragraphList), 2019);
    }

    private String parseTitle(List<XWPFParagraph> paragraphList) {
        return paragraphList.get(2).getParagraphText();
    }

    private String parseAuthors(List<XWPFParagraph> paragraphList) {
        return paragraphList.get(4).getParagraphText();
    }

    private String parseKeyWords(List<XWPFParagraph> paragraphList) {
        for (XWPFParagraph paragraph : paragraphList) {
            for (String keyWordsKeyWord : keyWordsKeyWords) {
                boolean matches = paragraph.getParagraphText().toLowerCase(Locale.ROOT).startsWith(keyWordsKeyWord);
                if (matches) {
                    return paragraph.getParagraphText().split(":")[1];
                }
            }
        }
        return null;
    }

    /**
     * @param article article to check
     * @return {@code true} if article contains metadata info, {@code false} otherwise
     */
    private boolean checkArticleMetadata(Article article) {
        return !StringUtils.isEmpty(article.getAuthors()) || !StringUtils.isEmpty(article.getTitle()) || !StringUtils.isEmpty(article.getKeyWords()) || !(article.getPublicationYear() == null || article.getPublicationYear() == 0);
    }

    private void setMetadataToArticle(Article article, Metadata metadata) {
        article.setTitle(metadata.getTitle());
        article.setAuthors(metadata.getAuthors());
        article.setKeyWords(metadata.getKeyWords());
        article.setPublicationYear(metadata.getPublicationYear());
    }
}
