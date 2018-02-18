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

import com.electroline.myapp.domain.EclipseAdvice;
import com.electroline.myapp.service.EclipseNoteService;

@Controller
@RequestMapping(value = "eclipse")
public class EclipseTabController {
	
	@Autowired
	@Qualifier(value = "eclipseNoteService")
	private EclipseNoteService myEclipseNoteService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		List<EclipseAdvice> eaList = new ArrayList<EclipseAdvice>();
		eaList = myEclipseNoteService.getAllNotes();
		model.addAttribute("eclipseAdvices", eaList);
		model.addAttribute("noteType", "eclipse");
		return "eclipse";
	}
	
	@RequestMapping(value = "/addnote", method = RequestMethod.GET)
	public String addNote(Model model) {
		EclipseAdvice newAdvice = new EclipseAdvice();
		model.addAttribute("newnote", newAdvice);
		model.addAttribute("noteType", "eclipse");
		return "addnote";
	}
	
	@RequestMapping(value = "/addfillednote", method = RequestMethod.POST)
	public String addFilledNote(ModelMap model, @RequestParam Map<String, String> newAdvice) {
		
		EclipseAdvice ea = new EclipseAdvice();
		ea.setAdviceName(newAdvice.get("adviceName"));
		ea.setAdviceDescription(newAdvice.get("adviceDescription"));
		
		try {
			@SuppressWarnings("unused")
			int newNoteId = myEclipseNoteService.createNewNote(ea);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<EclipseAdvice> eaList = new ArrayList<EclipseAdvice>();
		eaList = myEclipseNoteService.getAllNotes();
		model.addAttribute("eclipseAdvices", eaList);
		return "eclipse";
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {	
		myEclipseNoteService.deleteNote(adviceId);
		List<EclipseAdvice> eaList = new ArrayList<EclipseAdvice>();
		eaList = myEclipseNoteService.getAllNotes();
		model.addAttribute("eclipseAdvices", eaList);
		return "eclipse";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		EclipseAdvice ea = new EclipseAdvice();
		ea = myEclipseNoteService.getNote(adviceId);
		model.addAttribute("advice", ea);
		model.addAttribute("noteType", "eclipse");
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam Map<String, String> advice) {
		EclipseAdvice ea = new EclipseAdvice();
		ea.setId(Integer.parseInt(advice.get("id")));
		ea.setAdviceName(advice.get("adviceName"));
		ea.setAdviceDescription(advice.get("adviceDescription"));
		myEclipseNoteService.updateNote(ea);
		
		List<EclipseAdvice> eaList = new ArrayList<EclipseAdvice>();
		eaList = myEclipseNoteService.getAllNotes();
		model.addAttribute("eclipseAdvices", eaList);
		return "eclipse";
	}
	
}
