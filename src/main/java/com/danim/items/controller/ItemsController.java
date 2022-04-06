package com.danim.items.controller;

import com.danim.files.beans.FilesEntity;
import com.danim.files.service.FilesService;
import com.danim.files.util.MultipartFileUploadProcessor;
import com.danim.items.beans.ItemsDTO;
import com.danim.items.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/items")
public class ItemsController {
    private final FilesService filesService;
    private final ItemsService itemsService;

    @Autowired
    public ItemsController(FilesService filesService, ItemsService itemsService) {
        this.filesService = filesService;
        this.itemsService = itemsService;
    }

    @RequestMapping(value = "/doReg", method = RequestMethod.POST)
    public String doReg(ItemsDTO itemsDTO, MultipartHttpServletRequest multipartRequest, RedirectAttributes attributes) throws IOException {

        boolean result = itemsService.regItem(itemsDTO, multipartRequest);

        if (result){
            attributes.addFlashAttribute("regItem", "passed");
            return "redirect:/admin/items";
        } else {
            attributes.addFlashAttribute("regItem", "failed");
            return "redirect:/admin/items_reg";
        }
    }

    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public String doUpdate(ItemsDTO itemsDTO, MultipartHttpServletRequest multipartRequest, RedirectAttributes attributes) throws IOException {
        String localPics = multipartRequest.getParameter("localPics");
        String externalPics = multipartRequest.getParameter("externalPics");

        boolean result = itemsService.updateItem(itemsDTO, localPics, externalPics, multipartRequest);

        if (result){
            attributes.addFlashAttribute("updateItem", "passed");
            return "redirect:/admin/items";
        } else {
            attributes.addFlashAttribute("updateItem", "failed");
            return "redirect:/admin/items_update?itemnum="+itemsDTO.getItemnum();
        }
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    public String doDel(@RequestParam String itemnum, RedirectAttributes attributes) {
        boolean result = itemsService.delItem(itemnum);

        if (result){
            attributes.addFlashAttribute("delItem", "passed");
        } else {
            attributes.addFlashAttribute("delItem", "failed");
        }

        return "redirect:/admin/items";
    }
}
