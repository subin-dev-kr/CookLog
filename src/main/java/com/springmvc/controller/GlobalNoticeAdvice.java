package com.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springmvc.domain.Notice;
import com.springmvc.service.NoticeService;

@ControllerAdvice
public class GlobalNoticeAdvice {
	@Autowired
	NoticeService noticeService;
	
	@ModelAttribute("top5List")
    public List<Notice> addTop5Notice() {
		
        return noticeService.findTop5();
    }

}
