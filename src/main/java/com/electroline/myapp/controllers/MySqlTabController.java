package com.electroline.myapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.electroline.myapp.domain.MySqlAdvice;
import com.electroline.myapp.service.MySqlNoteService;

@Controller
@RequestMapping(value = "/mysql")
public class MySqlTabController {
	
	@Autowired
	@Qualifier(value = "mySqlNoteService")
	private MySqlNoteService mySqlNoteService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		List<MySqlAdvice> msaList = new ArrayList<MySqlAdvice>();
		msaList = mySqlNoteService.getAllNotes();
		model.addAttribute("sqlAdvices", msaList);
		return "mysql";
	}
	
	@RequestMapping(value = "/addnote", method = RequestMethod.GET)
	public String addNote(Model model) {
		MySqlAdvice msa = new MySqlAdvice();
		model.addAttribute("newnote", msa);
		model.addAttribute("noteType", "mysql");
		return "addnote";
		
	}
	
	@RequestMapping(value = "/addfillednote", method = RequestMethod.POST)
	public String addFilledNote(ModelMap model, @RequestParam Map<String, String> newAdvice) {
		
		MySqlAdvice msa = new MySqlAdvice();
		msa.setAdviceName(newAdvice.get("adviceName"));
		msa.setAdviceDescription(newAdvice.get("adviceDescription"));
		
		try {
			@SuppressWarnings("unused")
			int newNoteId = mySqlNoteService.createNewNote(msa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<MySqlAdvice> msaList = new ArrayList<MySqlAdvice>();
		msaList = mySqlNoteService.getAllNotes();
		model.addAttribute("sqlAdvices", msaList);
		return "mysql";
		
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {	
		mySqlNoteService.deleteNote(adviceId);
		List<MySqlAdvice> msaList = new ArrayList<MySqlAdvice>();
		msaList = mySqlNoteService.getAllNotes();
		model.addAttribute("sqlAdvices", msaList);
		return "mysql";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		MySqlAdvice msa = new MySqlAdvice();
		msa = mySqlNoteService.getNote(adviceId);
		model.addAttribute("advice", msa);
		model.addAttribute("noteType", "mysql");
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam Map<String, String> advice) {
		MySqlAdvice msa = new MySqlAdvice();
		msa.setId(Integer.parseInt(advice.get("id")));
		msa.setAdviceName(advice.get("adviceName"));
		msa.setAdviceDescription(advice.get("adviceDescription"));
		mySqlNoteService.updateNote(msa);
		
		List<MySqlAdvice> msaList = new ArrayList<MySqlAdvice>();
		msaList = mySqlNoteService.getAllNotes();
		model.addAttribute("sqlAdvices", msaList);
		
		return "mysql";
		
	}
	
}

