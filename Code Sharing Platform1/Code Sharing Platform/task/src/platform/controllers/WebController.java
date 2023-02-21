package platform.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import platform.entities.Code;
import platform.repositories.CodeRepository;
import platform.services.CodeServiceImpl;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:8889")
@RequestMapping(path = "/code")
public class WebController {

    private static final Logger LOGGER =  LoggerFactory.getLogger(CodeRepository.class);
    private final CodeServiceImpl codeService;

    public WebController(CodeServiceImpl codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/{id}")
    public String getTextHtmlCode(@PathVariable("id") String id, Model model) {
        Code code = codeService.findById(id);
        model.addAttribute("title", "Code");
        model.addAttribute("codes", code);
        return "code-view";
    }

    @GetMapping("/new")
    public String postCode() {
        return "new-code";
    }

    @PostMapping("/new")
    public void postCode(@RequestBody Code code) {
        codeService.saveCode(code);
        LOGGER.info("Saved code with ID: " + code.getId());
    }

    @GetMapping("/latest")
    public String getLatestCodes(Model model) {
        List<Code> codes = codeService.getLastTenCodes();
        LOGGER.info(codes.toString());
        model.addAttribute("title", "Latest");
        model.addAttribute("codes", codes);
        return "code-view";
    }


//    @GetMapping("/all")
//    public String getCode(Model model) {
//
//        List<Code> codes = codeRepository.findAll();
//        model.addAttribute("code", codes);
//
//        LOGGER.info("Get code");
//        return "code";
//    }

}



