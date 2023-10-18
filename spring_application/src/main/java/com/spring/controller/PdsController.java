package com.spring.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.josephoconnell.html.HTMLInputFilter;
import com.jsp.command.SearchListCommand;
import com.spring.command.PdsModifyCommand;
import com.spring.command.PdsRegistCommand;
import com.spring.dto.AttachVO;
import com.spring.dto.PdsVO;
import com.spring.service.PdsService;

@Controller
@RequestMapping("/pds")
public class PdsController {

	@Resource(name = "pdsService")
	private PdsService service;

	@GetMapping("/main")
	public void main() throws Exception {
	}

	@GetMapping("/list")
	public ModelAndView list(SearchListCommand command, ModelAndView mnv) throws Exception {
		String url = "/pds/list";

		Map<String, Object> dataMap = service.getList(command);

		mnv.addObject("dataMap", dataMap);
		mnv.setViewName(url);

		return mnv;
	}

	@RequestMapping("/registForm")
	public String registForm() throws Exception {
		String url = "pds/regist";
		return url;
	}

	@Resource(name = "fileUploadPath")
	private String fileUploadPath;

	private List<AttachVO> savedMultipartFileToAttaches(List<MultipartFile> multiFiles, String savePath)
			throws Exception {

		if (multiFiles == null)
			return null;

		// 저장 > attachVO > list.add
		List<AttachVO> attachList = new ArrayList<AttachVO>();
		for (MultipartFile multi : multiFiles) {
			// 파일저장
			String fileName = UUID.randomUUID().toString() + "$$" + multi.getOriginalFilename();

			File target = new File(savePath, fileName);
			target.mkdirs();
			multi.transferTo(target);

			// 저장 > attachVO
			AttachVO attach = new AttachVO();
			attach.setUploadPath(savePath);
			attach.setFileName(fileName);
			attach.setFileType(fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase());

			attachList.add(attach);
		}

		return attachList;

	}

	@PostMapping(value = "/regist", produces = "text/plain;charset=utf-8")
	public String regist(PdsRegistCommand command, RedirectAttributes rttr) throws Exception {
		String url = "redirect:/pds/list.do";

		List<MultipartFile> multiFiles = command.getUploadFile();
		String savePath = this.fileUploadPath;

		// file 저장 > attachVO > list.add
		List<AttachVO> acttachList = savedMultipartFileToAttaches(multiFiles, savePath);

		// DB
		PdsVO pds = command.toPdsVO();
		pds.setTitle(HTMLInputFilter.htmlSpecialChars(pds.getTitle()));
		pds.setAttachList(acttachList);

		service.regist(pds);

		rttr.addFlashAttribute("from", "regist");

		return url;

	}

	@GetMapping("/detail")
	public ModelAndView detail(int pno, @RequestParam(defaultValue = "") String from, RedirectAttributes rttr, ModelAndView mnv) throws Exception {
		String url = "/pds/detail";

		PdsVO pds = null;
		if (from.equals("list")) {
			pds = service.read(pno);
			url = "redirect:/pds/detail.do";

			rttr.addAttribute("pno", pno);
			mnv.setViewName(url);
			return mnv;
		}

		pds = service.getPds(pno);

		// 파일명 재정의
		if (pds != null) {
			List<AttachVO> attachList = pds.getAttachList();
			if (attachList != null) {
				for (AttachVO attach : attachList) {
					String fileName = attach.getFileName().split("\\$\\$")[1];
					attach.setFileName(fileName);
				}
			}
		}

		mnv.addObject("pds", pds);
		mnv.setViewName(url);

		return mnv;
	}

	@GetMapping("/getFile")
	public String getFile(int ano, Model model)throws Exception{
		String url = "downloadFile";
		
		AttachVO attach = service.getAttachByAno(ano);
		
		model.addAttribute("savedPath", attach.getUploadPath());
		model.addAttribute("fileName", attach.getFileName());
		
		return url;
	}
	
	@GetMapping("/modifyForm")
	public ModelAndView ModifyForm(int pno, ModelAndView mnv)throws Exception{
		String url = "/pds/modify";
		
		PdsVO pds = service.getPds(pno);
		
		// 파일명 재정의
		if (pds != null) {
			List<AttachVO> attachList = pds.getAttachList();
			if (attachList != null) {
				for (AttachVO attach : attachList) {
					String fileName = attach.getFileName().split("\\$\\$")[1];
					attach.setFileName(fileName);
				}
			}
		}
		
		mnv.addObject("pds",pds);
		mnv.setViewName(url);
		return mnv;
	}
	
	@PostMapping(value="/modify", produces="text/plain;charset=utf-8")
	public String modifyPOST(PdsModifyCommand modifyReq, RedirectAttributes rttr)throws Exception{
		String url = "redirect:/pds/detail.do";
		
		//파일삭제
		if(modifyReq.getDeleteFile() != null && modifyReq.getDeleteFile().length > 0) {
			for(int ano : modifyReq.getDeleteFile()) {
				AttachVO attach = service.getAttachByAno(ano);
				
				String savedPath = attach.getUploadPath().replace("/", File.separator);
				
				File deleteFile = new File(savedPath, attach.getFileName());
				
				if(deleteFile.exists()) {
					deleteFile.delete(); //파일삭제
				}
				service.removeAttachByAno(ano); //DB삭제
			}
		}
		
		//파일저장
		List<AttachVO> attachList = savedMultipartFileToAttaches(modifyReq.getUploadFile(), fileUploadPath);
		
		//PdsVO setting
		PdsVO pds = modifyReq.toPdsVO();
		pds.setTitle(HTMLInputFilter.htmlSpecialChars(pds.getTitle()));
		pds.setAttachList(attachList);
		
		//DB 저장
		service.modify(pds);
		
		rttr.addFlashAttribute("from","modify");
		rttr.addAttribute("pno", pds.getPno());
		
		return url;
	}
	
	@GetMapping("/remove")
	public String remove(int pno, RedirectAttributes rttr) throws Exception{
		String url = "redirect:/pds/detail.do";
		
		//첨부파일 삭제
		List<AttachVO> attachList = service.getPds(pno).getAttachList();
		if(attachList != null) {
			for(AttachVO attach : attachList) {
				File target = new File(attach.getUploadPath(), attach.getFileName());
				if(target.exists()) {
					target.delete();
				}
			}
		}
		service.remove(pno);
		
		rttr.addFlashAttribute("from", "remove");
		rttr.addAttribute("pno", pno);
		
		return url;
	}
}













