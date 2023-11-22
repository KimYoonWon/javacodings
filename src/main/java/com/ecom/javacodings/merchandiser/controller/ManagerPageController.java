package com.ecom.javacodings.merchandiser.controller;

import com.ecom.javacodings.common.PageConstructor;
import com.ecom.javacodings.common.transfer.table.ItemDTO;
import com.ecom.javacodings.common.transfer.PageDTO;
import com.ecom.javacodings.merchandiser.service.ManagerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/")
public class ManagerPageController {

	private static final Logger logger = LoggerFactory.getLogger(ManagerPageController.class);
	
    @Autowired
    ManagerService  managerService;
    PageConstructor pageConstructor = new PageConstructor();

    @RequestMapping("/products")
    public String products(HttpServletRequest request, HttpServletResponse response,
                           Model model) {
        Map<String, Object> pageMap = pageConstructor.getPages(
                (PageDTO pageSet) -> Collections.singletonList(managerService.listItem(pageSet)),
                request.getParameter("page"),
                request.getParameter("row"),
                managerService.countItems()
        );

        if((Integer) pageMap.get("currentPage") > (Integer) pageMap.get("totalPages")) {
            return "redirect:/admin/products?page=" + pageMap.get("totalPages") + "&row=" + pageMap.get("row");
        }

        model.addAllAttributes(pageMap);
        model.addAttribute("categoryList", managerService.listCategory());
        model.addAttribute("tagList", managerService.listTags());

        return "/merchandiser/products";
    }

    @RequestMapping("/orders")
    public String orders(HttpServletRequest request, HttpServletResponse response,
                          Model model) {
        Map<String, Object> pageMap = pageConstructor.getPages(
                (PageDTO pageSet) -> Collections.singletonList(managerService.listOrder(pageSet)),
                request.getParameter("page"),
                request.getParameter("row"),
                managerService.countOrders()
        );

        if((Integer) pageMap.get("currentPage") > (Integer) pageMap.get("totalPages")) {
            return "redirect:/admin/orders?page=" + pageMap.get("totalPages") + "&row=" + pageMap.get("row");
        }

        model.addAllAttributes(pageMap);
        model.addAttribute("stateList", managerService.countOrderState());

        return "/merchandiser/orders";
    }
    
    @RequestMapping("/members")
	public String members(HttpServletRequest request, HttpServletResponse response, 
							Model model) { 
    	Map<String, Object> pageMap = pageConstructor.getPages(
                (PageDTO pageSet) -> Collections.singletonList(managerService.listMember(pageSet)),
                request.getParameter("page"),
                request.getParameter("row"),
                managerService.countMembers()
        );
        if((Integer) pageMap.get("currentPage") > (Integer) pageMap.get("totalPages")) {
            return "redirect:/admin/members?page=" + pageMap.get("totalPages") + "&row=" + pageMap.get("row");
        }
        
        model.addAllAttributes(pageMap);
        
		return "merchandiser/members";
	}
}