package com.electroline.myapp.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;

import com.electroline.myapp.domain.ElectronicsGeneralAdvice;
import com.electroline.myapp.service.ElectronicsNoteService;

@Controller
@RequestMapping(value = "/electronicsgeneral")
public class ElectronicsGeneralAdviceTabController {
	
	private static String UPLOADED_FOLDER = "C:\\Users\\Dawid\\Documents\\workspace-sts-3.9.0.RELEASE\\SpringMVCProject\\src\\main\\webapp\\resources\\attachments\\electronicsGeneral\\";

	@Autowired
	@Qualifier(value = "electronicsNoteService")
	private ElectronicsNoteService myElectronicsNoteService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		List<ElectronicsGeneralAdvice> egaList = new ArrayList<ElectronicsGeneralAdvice>();
		egaList = myElectronicsNoteService.getAllNotes();
		model.addAttribute("electronicsAdvices", egaList);
		model.addAttribute("noteType", "electronicsgeneral");
		return "electronicsgeneral";
		
	}
	
	@RequestMapping(value = "/addnote", method = RequestMethod.GET)
	public String addNote(Model model) {
		ElectronicsGeneralAdvice newAdvice = new ElectronicsGeneralAdvice();
		model.addAttribute("newnote", newAdvice);
		model.addAttribute("noteType", "electronicsgeneral");
		return "addnote";
		
	}
	
	@RequestMapping(value = "/addfillednote", method = RequestMethod.POST)
	public String addFilledNote(ModelMap model, @RequestParam Map<String, String> newAdvice, @RequestParam("file") MultipartFile file) {
		
		ElectronicsGeneralAdvice ega = new ElectronicsGeneralAdvice();
		ega.setAdviceName(newAdvice.get("adviceName"));
		ega.setAdviceDescription(newAdvice.get("adviceDescription"));
		
		try {
			@SuppressWarnings("unused")
			int newNoteId = myElectronicsNoteService.createNewNote(ega);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		List<ElectronicsGeneralAdvice> egaList = new ArrayList<ElectronicsGeneralAdvice>();
		egaList = myElectronicsNoteService.getAllNotes();
		model.addAttribute("electronicsAdvices", egaList);
		return "electronicsgeneral";
		
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {	
		myElectronicsNoteService.deleteNote(adviceId);
		List<ElectronicsGeneralAdvice> egaList = new ArrayList<ElectronicsGeneralAdvice>();
		egaList = myElectronicsNoteService.getAllNotes();
		model.addAttribute("electronicsAdvices", egaList);
		return "electronicsgeneral";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		ElectronicsGeneralAdvice ega = new ElectronicsGeneralAdvice();
		ega = myElectronicsNoteService.getNote(adviceId);
		model.addAttribute("advice", ega);
		model.addAttribute("noteType", "electronicsgeneral");
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam Map<String, String> advice) {
		ElectronicsGeneralAdvice ega = new ElectronicsGeneralAdvice();
		ega.setId(Integer.parseInt(advice.get("id")));
		ega.setAdviceName(advice.get("adviceName"));
		ega.setAdviceDescription(advice.get("adviceDescription"));
		myElectronicsNoteService.updateNote(ega);
		
		List<ElectronicsGeneralAdvice> egaList = new ArrayList<ElectronicsGeneralAdvice>();
		egaList = myElectronicsNoteService.getAllNotes();
		model.addAttribute("electronicsAdvices", egaList);
		
		return "electronicsgeneral";
		
	}
}
