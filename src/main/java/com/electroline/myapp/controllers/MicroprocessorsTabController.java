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

import com.electroline.myapp.domain.MicroprocessorAdvice;
import com.electroline.myapp.service.MicroprocessorNoteService;

@Controller
@RequestMapping(value = "/microprocessors")
public class MicroprocessorsTabController {
	
	@Autowired
	@Qualifier(value = "microprocessorNoteService")
	private MicroprocessorNoteService myNewNoteService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home( Model model) {
		
		List<MicroprocessorAdvice> maList = new ArrayList<MicroprocessorAdvice>();
		maList = myNewNoteService.getAllNotes();
		model.addAttribute("microprocessorAdvices", maList);
		return "microprocessors";
	}
	
	@RequestMapping(value = "/addnote", method = RequestMethod.GET)
	public String addNote(Model model) {
		MicroprocessorAdvice newAdvice = new MicroprocessorAdvice();
		model.addAttribute("newnote", newAdvice);
		model.addAttribute("noteType", "microprocessors");
		return "addnote";
		
	}
	
	@RequestMapping(value = "/addfillednote", method = RequestMethod.POST)
	public String addFilledNote(ModelMap model, @RequestParam Map<String, String> newAdvice) {
		
		MicroprocessorAdvice ma = new MicroprocessorAdvice();
		ma.setAdviceName(newAdvice.get("adviceName"));
		ma.setAdviceDescription(newAdvice.get("adviceDescription"));
		
		try {
			@SuppressWarnings("unused")
			int newNoteId = myNewNoteService.createNewNote(ma);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<MicroprocessorAdvice> maList = new ArrayList<MicroprocessorAdvice>();
		maList = myNewNoteService.getAllNotes();
		model.addAttribute("microprocessorAdvices", maList);
		return "microprocessors";
		
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {	
		myNewNoteService.deleteNote(adviceId);
		List<MicroprocessorAdvice> maList = new ArrayList<MicroprocessorAdvice>();
		maList = myNewNoteService.getAllNotes();
		model.addAttribute("microprocessorAdvices", maList);
		return "microprocessors";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		MicroprocessorAdvice ma = new MicroprocessorAdvice();
		ma = myNewNoteService.getNote(adviceId);
		model.addAttribute("advice", ma);
		model.addAttribute("noteType", "microprocessors");
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam Map<String, String> advice) {
		MicroprocessorAdvice ma = new MicroprocessorAdvice();
		ma.setId(Integer.parseInt(advice.get("id")));
		ma.setAdviceName(advice.get("adviceName"));
		ma.setAdviceDescription(advice.get("adviceDescription"));
		myNewNoteService.updateNote(ma);
		
		List<MicroprocessorAdvice> maList = new ArrayList<MicroprocessorAdvice>();
		maList = myNewNoteService.getAllNotes();
		model.addAttribute("microprocessorAdvices", maList);
		
		return "microprocessors";
		
	}
	
}

