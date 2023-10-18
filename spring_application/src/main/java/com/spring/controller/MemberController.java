package com.spring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.josephoconnell.html.HTMLInputFilter;
import com.jsp.command.SearchListCommand;
import com.spring.command.MemberModifyCommand;
import com.spring.dto.MemberVO;
import com.spring.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

   @Resource(name = "memberService")
   private MemberService memberService;
   
   @GetMapping("/main")
   public void main() {
   }

   @GetMapping("/list")
   public void list(SearchListCommand command, Model model) throws Exception {
      Map<String, Object> dataMap = memberService.getMemberList(command);
      model.addAllAttributes(dataMap);
   }
   

   @GetMapping("/registForm")
   public String registForm() {
      String url = "/member/regist";
      return url;
   }
   
   @PostMapping("/regist")
   public String regist(MemberVO member, @RequestParam("phone")String phones[])throws Exception{
      String url = "/member/regist_success";
      
      member.setName(HTMLInputFilter.htmlSpecialChars(member.getName()));
      member.setPhone("");
      for(String phone : phones) {
         member.setPhone(member.getPhone()+phone);
      }
      
      memberService.regist(member);
      
      return url;
   }
   
   @GetMapping("/detail")
   public String detail(String id, Model model) throws Exception {
      
      String url = "/member/detail";
      
      MemberVO member = memberService.getMember(id);
      model.addAttribute("member",member);
      
      return url;
   }
   
   @GetMapping("/modifyForm")
   public String modify(String id, Model model)throws Exception {
      
      String url = "/member/modify";
      
      MemberVO member = memberService.getMember(id);
      
      if(member.getPicture().split("\\$\\$").length > 1) {
         member.setPicture(member.getPicture().split("\\$\\$")[1]);
      }
      
      model.addAttribute("member", member);
      
      return url;
   }
   
   @PostMapping(value = "/modify", produces = "text/plain;charset=utf-8")
   public String modify(MemberModifyCommand modifyReq) throws Exception {
      
      String url = "redirect:/member/detail.do?id="+modifyReq.getId();
      
      MemberVO member = modifyReq.toMember();
      member.setName(HTMLInputFilter.htmlSpecialChars(member.getName()));
      
      // 신규 파일 변경 및 기존 파일 삭제
      String oldPicture = memberService.getMember(member.getId()).getPicture();
      
      if(modifyReq.getPicture() != null && modifyReq.getPicture().getSize() > 0) {
         String fileName = savePicture(oldPicture, modifyReq.getPicture());
         member.setPicture(fileName);
      }else {
         member.setPicture(oldPicture);
      }
      
      // DB 내용 수정
      memberService.modify(member);
      
      return url;
   }
   @GetMapping(value ="/remove")
   public String remove(String id)throws Exception{
	   String url="/member/remove_success";
	   
	   //이미지 파일을 삭제
	   MemberVO member = memberService.getMember(id);
	   String savePath = this.picturePath;
	   File imageFile = new File(savePath, member.getPicture());
	   if(imageFile.exists()) {
		   imageFile.delete();
	   }
	   //db삭제
	   memberService.remove(id);
	   
	   return url;
   }
   
   
   @GetMapping("/idCheck")
   @ResponseBody
   public ResponseEntity<String> idCheck(String id) throws Exception {
      ResponseEntity<String> entity = null;

      MemberVO member = memberService.getMember(id);

      if (member != null) {
         entity = new ResponseEntity<String>("duplicated", HttpStatus.OK);
      } else {
         entity = new ResponseEntity<String>("", HttpStatus.OK);
      }

      return entity;
   }
   

   @Resource(name = "picturePath")
   private String picturePath;
   
   @PostMapping(value = "/picture", produces = "text/plain;charset=utf-8")
   @ResponseBody
   public ResponseEntity<String> pictureUpload(@RequestParam("pictureFile") MultipartFile multi, 
                                    String oldPicture)
                                             throws Exception {
      ResponseEntity<String> entity = null;
      String result = "";
      HttpStatus status = null;
      
      /* 파일저장확인 */
      result = savePicture(oldPicture, multi);
      if (result == null) {
         result = "파일이 없습니다.!";
         status = HttpStatus.BAD_REQUEST;
      } else {
         status = HttpStatus.OK;

      }
      
      entity = new ResponseEntity<String>(result, status);
      return entity;
   }
   
   public String savePicture(String oldPicture, MultipartFile multi) throws Exception {
      // 저장 파일명
      String fileName = null;
      
      // 파일저장폴더설정 
      String uploadPath = this.picturePath;
      
      // 파일유무확인 
      if (!(multi == null || multi.isEmpty() || multi.getSize() > 1024 * 1024 * 1)) {
         String uuid=UUID.randomUUID().toString().replace("-","");
         fileName = uuid+"$$"+multi.getOriginalFilename();
         File storeFile = new File(uploadPath, fileName);

         // 파일경로 생성
         storeFile.mkdirs();

         // local HDD 에 저장.
         multi.transferTo(storeFile);
      }

      // 기존파일 삭제
      if (oldPicture != null && !oldPicture.isEmpty()) {
         File oldFile = new File(uploadPath, oldPicture);
         if (oldFile.exists()) {
            oldFile.delete();
         }
      }
      return fileName;
   }
   
   @GetMapping("/getPicture")
   public ResponseEntity<byte[]> getPicture(String id) throws Exception{
      MemberVO member = memberService.getMember(id);
      
      if(member==null) return new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
      
      String picture = member.getPicture();
      String imgPath = this.picturePath;
      
      InputStream in = null;
      ResponseEntity<byte[]> entity = null;
      
      try {
         in = new FileInputStream(new File(imgPath, picture));
         entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), HttpStatus.OK);
      }finally {
         if(in != null)
            in.close();
      }
      return entity;
   }
}




