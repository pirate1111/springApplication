package com.spring.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.josephoconnell.html.HTMLInputFilter;
import com.jsp.command.SearchListCommand;
import com.jsp.dto.NoticeVO;
import com.jsp.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/main")
	public void main() {}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(SearchListCommand command,
					   HttpServletRequest request) throws Exception {
		String url="/notice/list";
		
		Map<String,Object> dataMap = noticeService.getNoticeList(command);
		
		request.setAttribute("dataMap", dataMap);
		return url;
	}
	
	@RequestMapping(value="/registForm",method=RequestMethod.GET)
	public String registForm()throws Exception{
		String url="/notice/regist";
		return url;
	}
	
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	public String regist(NoticeVO notice) throws Exception {
		String url="/notice/regist_success";
		
		notice.setTitle(HTMLInputFilter.htmlSpecialChars(notice.getTitle()));
		
		noticeService.regist(notice);
		
		return url;
	}
	
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public String detail(int nno,
						@RequestParam(defaultValue = "")String from,
						HttpServletRequest request)throws Exception{
		String url="/notice/detail";
		
		NoticeVO notice = null;
		if(from.equals("list")) {
			notice=noticeService.read(nno);
			url="redirect:/notice/detail.do?nno="+nno;
		}else {
			notice=noticeService.getNotice(nno);
		}
		
		request.setAttribute("notice", notice);
		
		return url;
	}
	
	@GetMapping("/modifyForm")
	public String modifyForm(int nno,Model model)throws Exception{
		String url="/notice/modify";
		
		NoticeVO notice = noticeService.getNotice(nno);
		
		model.addAttribute("notice",notice);
		return url;
	}
	
	@PostMapping("/modify")
	public ModelAndView modifyPost(NoticeVO notice,ModelAndView mnv)throws Exception{
		String url = "redirect:/notice/detail.do?nno="+notice.getNno();

		notice.setTitle(HTMLInputFilter.htmlSpecialChars(notice.getTitle()));
        
		noticeService.modify(notice);
		
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@GetMapping("/remove")
	public String remove(int nno)throws Exception{
		String url="/notice/remove_success";
		
		noticeService.remove(nno);
		
		return url;
	}
}







