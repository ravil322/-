package ru.ravnasybullin.DoiReg.controllers;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import ru.ravnasybullin.DoiReg.PageConstants.PageConstants;
import ru.ravnasybullin.DoiReg.dto.Metadata;

import javax.transaction.Transactional;

@Controller
public class RegistrarController {

    @GetMapping(PageConstants.REGISTER_ARTICLE_PAGE)
    public String getRegisterPage() {
        return PageConstants.REGISTER_ARTICLE;
    }

    @Transactional
    @PostMapping(PageConstants.GET_METADATA)
    public ResponseEntity getMetadata(
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws JSONException {

        Metadata metadata= new Metadata("Даутов Мурат, Насыбуллин Равиль", "Наша статья", 2019);
        return ResponseEntity.ok(metadata.toJson().toString());    }
}

