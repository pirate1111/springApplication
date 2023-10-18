package com.spring.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.dto.MemberVO;
import com.spring.dto.MenuVO;
import com.spring.exception.InvalidPasswordException;
import com.spring.exception.NotFoundIdException;
import com.spring.service.MemberService;
import com.spring.service.MenuService;

@Controller
public class CommonController {
   
   @Autowired
   private MenuService menuService;
   
   @Autowired
   private MemberService memberService;
   
   @GetMapping("/main")
   public void main() {}
   
   @GetMapping("/index")
   public String index(@RequestParam(defaultValue = "M000000") String mCode,
                   HttpSession session,
                   Model model) throws Exception{
      String url="/common/indexPage";
      
      List<MenuVO> menuList = menuService.getMainMenuList();
      MenuVO menu = menuService.getMenuByMcode(mCode);
      
      String id = (String)session.getAttribute("loginUser");
      MemberVO member = memberService.getMember(id);
      
      model.addAttribute("menuList", menuList);
      model.addAttribute("menu", menu);
      model.addAttribute("loginUser",member);
      
      return url;
   }
   
   @GetMapping("/subMenu")
   @ResponseBody
   public List<MenuVO> subMenuToJSON(String mCode) throws Exception {
      List<MenuVO> menuList = menuService.getSubMenuList(mCode);
      return menuList;
   }
   
   @GetMapping("/common/loginForm")
   public String loginForm(@ModelAttribute("retUrl") String retUrl,
                       String error,
                       HttpServletResponse response,
                       Model model) {
      String url="/common/loginForm";
      
      if(error!=null && error.equals("-1")) {
         response.setStatus(302);
         model.addAttribute("message","로그인은 필수입니다.");
      }
      
      return url;
   }
   
   @GetMapping("/security/accessDenied")
	public void accessDenied() {}
   
   @GetMapping("/common/loginTimeOut")
	public String loginTimeOut(Model model)throws Exception {
		
		String url="/security/sessionOut";
		
		model.addAttribute("message","세션이 만료되었습니다.\\n다시 로그인 하세요!");
		return url;
	}
	
//   
//   @PostMapping("/common/login")
//   public String login(String id, String pwd,
//                  String retUrl,
//                  HttpSession session,
//                  Model model) 
//                                          throws Exception {
//      String url = "redirect:/index.do";
//      
//      if(retUrl!=null && !retUrl.isEmpty()) url="redirect:"+retUrl;
//      
//      try {
//         memberService.login(id, pwd);
//         session.setAttribute("loginUser",id);
//         model.addAttribute("loginUser",memberService.getMember(id));
//         
//         //session.setMaxInactiveInterval(6*60);
//      } catch (NotFoundIdException | InvalidPasswordException e) {
//         url = "/common/login_fail";
//         model.addAttribute("retUrl",retUrl);
//
//      } catch (SQLException e) {
//         e.printStackTrace();
//         throw e;
//      }
//      return url;
//   }
   
//   @GetMapping("/common/logout")
//   public String logout(HttpSession session) {
//      String url = "redirect:/";
//      session.invalidate();
//      return url;
//   }
}




