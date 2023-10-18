package com.spring.controller;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

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
import com.spring.dto.BoardVO;
import com.spring.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Resource(name="boardService")
	private BoardService service;
	
	@GetMapping("/main")
	public void main(){}
	
	@GetMapping("/list")
	public void list(SearchListCommand command, Model model)throws SQLException{
	
		Map<String,Object> dataMap = service.getBoardList(command);
		
		model.addAllAttributes(dataMap);
		
	}
	
	@GetMapping("/registForm")
	public String registForm() throws Exception{
		String url="board/regist";		
		return url;
	}
	
	@PostMapping("/regist")
	public String regist(BoardVO board)throws Exception{
		String url="/board/regist_success";	
		
		board.setTitle(HTMLInputFilter.htmlSpecialChars(board.getTitle()));
		service.regist(board);
		
		return url;
	}
	
	@RequestMapping("/detail")
	public String detail(int bno,
					   @RequestParam(defaultValue = "")String from,
					   Model model )throws SQLException{
		String url="/board/detail";
		
		BoardVO board =null;
		if(from.equals("list")) {
			board=service.read(bno);
			url="redirect:/board/detail.do?bno="+bno;
		}else {
			board=service.getBoard(bno);
		}
					
		model.addAttribute("board",board);
		
		return url;
	}
	
	@RequestMapping("/modifyForm")
	public ModelAndView modifyForm(int bno,ModelAndView mnv)throws SQLException{
		String url="/board/modify";
		
		BoardVO board = service.getBoard(bno);
		
		mnv.addObject("board",board);		
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modifyPost(BoardVO board,Model model) throws Exception{
		String url = "redirect:/board/detail.do?bno="+board.getBno();
		
		board.setTitle(HTMLInputFilter.htmlSpecialChars(board.getTitle()));
		service.modify(board);
		
		model.addAttribute("board",board); 
		return url;
	}
	
	@GetMapping(value="/remove")
	public String remove(int bno) throws Exception{
		String url = "/board/remove_success";
		service.remove(bno);		
	
		return url;		
	}
}






