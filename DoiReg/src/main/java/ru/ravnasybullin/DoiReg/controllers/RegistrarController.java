package ru.ravnasybullin.DoiReg.controllers;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ravnasybullin.DoiReg.PageConstants.PageConstants;
import ru.ravnasybullin.DoiReg.dto.Metadata;
import ru.ravnasybullin.DoiReg.models.Article;
import ru.ravnasybullin.DoiReg.services.ArticleService;

import javax.transaction.Transactional;
import java.io.IOException;

@Controller
@SessionAttributes("article")
public class RegistrarController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(PageConstants.REGISTER_ARTICLE_PAGE)
    public String getRegisterPage(Model model) {
        model.addAttribute("title", "Добавление новой статьи");
        model.addAttribute("article", new Article());
        return PageConstants.REGISTER_ARTICLE;
    }

    @Transactional
    @PostMapping(PageConstants.GET_METADATA)
    public ResponseEntity getMetadata(@RequestParam(value = "docx-file", required = false) MultipartFile file) throws JSONException, IOException, InvalidFormatException {
        Metadata metadata = articleService.getMetadata(file);
        System.out.println(metadata.toJson().toString());
        return ResponseEntity.ok(metadata.toJson().toString());
    }


    @PostMapping(PageConstants.REGISTER_ARTICLE_PAGE)
    @Transactional
    public String registerArticle(@ModelAttribute("article") Article article,
                                  @RequestParam(value = "docx-file", required = false) MultipartFile file) throws IOException, InvalidFormatException {
        articleService.checkMetadataAndSaveArticle(article, file);
        return "redirect:" + PageConstants.VIEW_ARTICLES_PAGE;
    }

    @ModelAttribute("article")
    public Article getArticle() {
        return new Article();
    }

}

