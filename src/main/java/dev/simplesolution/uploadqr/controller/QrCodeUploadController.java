package dev.simplesolution.uploadqr.controller;

import dev.simplesolution.uploadqr.service.QRCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class QrCodeUploadController {

    private Logger logger = LoggerFactory.getLogger(QrCodeUploadController.class);

    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping("/")
    public String index() {

        return "index";
    }

    @PostMapping("/uploadQrCode")
    public String uploadQrCode(@RequestParam("qrCodeFile") MultipartFile qrCodeFile, RedirectAttributes redirectAttributes) {
        if(qrCodeFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please choose file to upload.");
            return "redirect:/";
        }

        try {
            String qrContent = qrCodeService.decodeQR(qrCodeFile.getBytes());
            redirectAttributes.addFlashAttribute("successMessage", "File upload successfully.");
            redirectAttributes.addFlashAttribute("qrContent", "Your QR Code Content:" + qrContent);
            return "redirect:/";
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/";
    }

}
