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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.electroline.myapp.domain.ElectronicsGeneralAdvice;
import com.electroline.myapp.domain.ElectronicsGeneralAdviceAttachment;
import com.electroline.myapp.service.ElectronicsGeneralAdviceAttachmentService;
import com.electroline.myapp.service.ElectronicsNoteService;

@Controller
@PropertySource("classpath:META-INF/spring/fileUpload.properties")
@RequestMapping(value = "/electronicsgeneral")
public class ElectronicsGeneralAdviceTabController {
	
	@Value("${fileUpload.electronicsGeneralAttachmentsUrl}")
	protected String electronicsGeneralAttachmentsUrl;

	@Autowired
	@Qualifier(value = "electronicsNoteService")
	private ElectronicsNoteService myElectronicsNoteService;
	
	@Autowired
	@Qualifier("electronicsGeneralAdviceAttachmentService")
	ElectronicsGeneralAdviceAttachmentService electronicsGeneralAdviceAttachmentService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		List<ElectronicsGeneralAdvice> egaList = new ArrayList<ElectronicsGeneralAdvice>();
		egaList = myElectronicsNoteService.getAllNotes();
		model.addAttribute("electronicsAdvices", egaList);
		model.addAttribute("noteType", "electronicsgeneral");
		
		List<ElectronicsGeneralAdviceAttachment> eaaList = electronicsGeneralAdviceAttachmentService.getAllElectronicsGeneralAttachments();
		model.addAttribute("electronicsAdvicesAttachments", eaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = electronicsGeneralAttachmentsUrl.substring(0, electronicsGeneralAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = electronicsGeneralAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
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
	public String addFilledNote(ModelMap model, @RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3,
			@RequestParam("file4") MultipartFile file4, @RequestParam("file5") MultipartFile file5,
			@RequestParam Map<String, String> newAdvice) {
		
		ElectronicsGeneralAdvice ega = new ElectronicsGeneralAdvice();
		ega.setAdviceName(newAdvice.get("adviceName"));
		ega.setAdviceDescription(newAdvice.get("adviceDescription"));
		
		// Sprawdzamy czy nazwa pliku file5 jest taka sama jak innych plikow
		if((file5 != null) && file5.getOriginalFilename().equals(file4.getOriginalFilename())) file5 = null;
		if((file5 != null) && file5.getOriginalFilename().equals(file3.getOriginalFilename())) file5 = null;
		if((file5 != null) && file5.getOriginalFilename().equals(file2.getOriginalFilename())) file5 = null;
		if((file5 != null) && file5.getOriginalFilename().equals(file.getOriginalFilename())) file5 = null;
		
		// Sprawdzamy czy nazwa pliku file4 jest taka sama jak innych plikow
		if((file4 != null) && file4.getOriginalFilename().equals(file3.getOriginalFilename())) file4 = null;
		if((file4 != null) && file4.getOriginalFilename().equals(file2.getOriginalFilename())) file4 = null;
		if((file4 != null) && file4.getOriginalFilename().equals(file.getOriginalFilename())) file4 = null;
		
		// Sprawdzamy czy nazwa pliku file3 jest taka sama jak innych plikow
		if((file3 != null) && file3.getOriginalFilename().equals(file2.getOriginalFilename())) file3 = null;
		if((file3 != null) && file3.getOriginalFilename().equals(file.getOriginalFilename())) file3 = null;
		
		// Sprawdzamy czy nazwa pliku file2 jest taka sama jak innych plikow
		if((file2 != null) && file2.getOriginalFilename().equals(file.getOriginalFilename())) file2 = null;
				
		try {
			@SuppressWarnings("unused")
			int newNoteId = myElectronicsNoteService.createNewNote(ega);
			
			if(file != null)
			if(!file.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(electronicsGeneralAttachmentsUrl + file.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
	            eaa.setFileName(file.getOriginalFilename());
	            eaa.setElectronicsGeneralAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newElectronicsGeneralAdviceAttachmentId = electronicsGeneralAdviceAttachmentService.createNewElectronicsGeneralAttachment(eaa);
			}
			
			if(file2 != null)
			if(!file2.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file2.getBytes();
	            Path path = Paths.get(electronicsGeneralAttachmentsUrl + file2.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
	            eaa.setFileName(file2.getOriginalFilename());
	            eaa.setElectronicsGeneralAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newElectronicsGeneralAdviceAttachmentId = electronicsGeneralAdviceAttachmentService.createNewElectronicsGeneralAttachment(eaa);
			}
			
			if(file3 != null)
			if(!file3.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file3.getBytes();
	            Path path = Paths.get(electronicsGeneralAttachmentsUrl + file3.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
	            eaa.setFileName(file3.getOriginalFilename());
	            eaa.setElectronicsGeneralAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newElectronicsGeneralAdviceAttachmentId = electronicsGeneralAdviceAttachmentService.createNewElectronicsGeneralAttachment(eaa);
			}
			
			if(file4 != null)
			if(!file4.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file4.getBytes();
	            Path path = Paths.get(electronicsGeneralAttachmentsUrl + file4.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
	            eaa.setFileName(file4.getOriginalFilename());
	            eaa.setElectronicsGeneralAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newElectronicsGeneralAdviceAttachmentId = electronicsGeneralAdviceAttachmentService.createNewElectronicsGeneralAttachment(eaa);
			}
			if(file5 != null)
			if(!file5.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file5.getBytes();
	            Path path = Paths.get(electronicsGeneralAttachmentsUrl + file5.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
	            eaa.setFileName(file5.getOriginalFilename());
	            eaa.setElectronicsGeneralAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newElectronicsGeneralAdviceAttachmentId = electronicsGeneralAdviceAttachmentService.createNewElectronicsGeneralAttachment(eaa);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<ElectronicsGeneralAdvice> egaList = new ArrayList<ElectronicsGeneralAdvice>();
		egaList = myElectronicsNoteService.getAllNotes();
		model.addAttribute("electronicsAdvices", egaList);
		
		List<ElectronicsGeneralAdviceAttachment> eaaList = electronicsGeneralAdviceAttachmentService.getAllElectronicsGeneralAttachments();
		model.addAttribute("electronicsAdvicesAttachments", eaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = electronicsGeneralAttachmentsUrl.substring(0, electronicsGeneralAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = electronicsGeneralAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "electronicsgeneral";
		
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		List<ElectronicsGeneralAdviceAttachment> eaaListForDelete = electronicsGeneralAdviceAttachmentService.getAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(adviceId);
		
		electronicsGeneralAdviceAttachmentService.deleteAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(adviceId);		
		myElectronicsNoteService.deleteNote(adviceId);
		
		for(ElectronicsGeneralAdviceAttachment eaa : eaaListForDelete) {
			Path path = Paths.get(electronicsGeneralAttachmentsUrl + eaa.getFileName());
			if(Files.exists(path))
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		List<ElectronicsGeneralAdvice> egaList = new ArrayList<ElectronicsGeneralAdvice>();
		egaList = myElectronicsNoteService.getAllNotes();
		model.addAttribute("electronicsAdvices", egaList);
		
		List<ElectronicsGeneralAdviceAttachment> eaaList = electronicsGeneralAdviceAttachmentService.getAllElectronicsGeneralAttachments();
		model.addAttribute("electronicsAdvicesAttachments", eaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = electronicsGeneralAttachmentsUrl.substring(0, electronicsGeneralAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = electronicsGeneralAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "electronicsgeneral";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		ElectronicsGeneralAdvice ega = new ElectronicsGeneralAdvice();
		ega = myElectronicsNoteService.getNote(adviceId);
		model.addAttribute("advice", ega);
		model.addAttribute("noteType", "electronicsgeneral");
		
		List<ElectronicsGeneralAdviceAttachment> eaaList = electronicsGeneralAdviceAttachmentService.getAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(adviceId);
		model.addAttribute("adviceAttachmentsList", eaaList);
		model.addAttribute("adviceAttachmentsValue", eaaList.size());
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = electronicsGeneralAttachmentsUrl.substring(0, electronicsGeneralAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = electronicsGeneralAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model,  @RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2, @RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4, @RequestParam(value = "file5", required = false) MultipartFile file5,
			@RequestParam Map<String, String> advice) {
		
		List<ElectronicsGeneralAdviceAttachment> eaaListForThisAdvice = electronicsGeneralAdviceAttachmentService.getAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(Integer.parseInt(advice.get("id")));
		List<Integer> attachmentsListToDelete = new ArrayList<Integer>();
		for(ElectronicsGeneralAdviceAttachment eaa : eaaListForThisAdvice) {
			if("on".equals(advice.get("checkbox_" + eaa.getId()))) attachmentsListToDelete.add(eaa.getId());
		}
		
		List<ElectronicsGeneralAdviceAttachment> eaaListForNote = electronicsGeneralAdviceAttachmentService.getAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(Integer.parseInt(advice.get("id")));
		List<ElectronicsGeneralAdviceAttachment> eaaListToDelete = new ArrayList<>();
		
		for(ElectronicsGeneralAdviceAttachment eaa : eaaListForNote) {
			for(int idToDelete : attachmentsListToDelete) {
				if(eaa.getId() == idToDelete) eaaListToDelete.add(eaa);
			}
		}
		
		for(ElectronicsGeneralAdviceAttachment eaa : eaaListToDelete) {
			Path path = Paths.get(electronicsGeneralAttachmentsUrl + eaa.getFileName());
			if(Files.exists(path))
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		attachmentsListToDelete.forEach(x->electronicsGeneralAdviceAttachmentService.deleteAttachment(x));
		
		ElectronicsGeneralAdvice ega = new ElectronicsGeneralAdvice();
		ega.setId(Integer.parseInt(advice.get("id")));
		ega.setAdviceName(advice.get("adviceName"));
		ega.setAdviceDescription(advice.get("adviceDescription"));
		myElectronicsNoteService.updateNote(ega);
		
		// Sprawdzamy czy nazwa pliku file5 jest taka sama jak innych plikow
				if((file5 != null) && file5.getOriginalFilename().equals(file4.getOriginalFilename())) file5 = null;
				if((file5 != null) && file5.getOriginalFilename().equals(file3.getOriginalFilename())) file5 = null;
				if((file5 != null) && file5.getOriginalFilename().equals(file2.getOriginalFilename())) file5 = null;
				if((file5 != null) && file5.getOriginalFilename().equals(file1.getOriginalFilename())) file5 = null;
				
				// Sprawdzamy czy nazwa pliku file4 jest taka sama jak innych plikow
				if((file4 != null) && file4.getOriginalFilename().equals(file3.getOriginalFilename())) file4 = null;
				if((file4 != null) && file4.getOriginalFilename().equals(file2.getOriginalFilename())) file4 = null;
				if((file4 != null) && file4.getOriginalFilename().equals(file1.getOriginalFilename())) file4 = null;
				
				// Sprawdzamy czy nazwa pliku file3 jest taka sama jak innych plikow
				if((file3 != null) && file3.getOriginalFilename().equals(file2.getOriginalFilename())) file3 = null;
				if((file3 != null) && file3.getOriginalFilename().equals(file1.getOriginalFilename())) file3 = null;
				
				// Sprawdzamy czy nazwa pliku file2 jest taka sama jak innych plikow
				if((file2 != null) && file2.getOriginalFilename().equals(file1.getOriginalFilename())) file2 = null;
				
				try {
					
					if(file1 != null)
					if(!file1.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file1.getBytes();
			            Path path = Paths.get(electronicsGeneralAttachmentsUrl + file1.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
			            eaa.setFileName(file1.getOriginalFilename());
			            eaa.setElectronicsGeneralAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newElectronicsGeneralAdviceAttachmentId = electronicsGeneralAdviceAttachmentService.createNewElectronicsGeneralAttachment(eaa);
					}
					
					if(file2 != null)
					if(!file2.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file2.getBytes();
			            Path path = Paths.get(electronicsGeneralAttachmentsUrl + file2.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
			            eaa.setFileName(file2.getOriginalFilename());
			            eaa.setElectronicsGeneralAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newWindowsAdviceAttachmentId = electronicsGeneralAdviceAttachmentService.createNewElectronicsGeneralAttachment(eaa);
					}
					
					if(file3 != null)
					if(!file3.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file3.getBytes();
			            Path path = Paths.get(electronicsGeneralAttachmentsUrl + file3.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
			            eaa.setFileName(file3.getOriginalFilename());
			            eaa.setElectronicsGeneralAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newElectronicsGeneralAdviceAttachmentId = electronicsGeneralAdviceAttachmentService.createNewElectronicsGeneralAttachment(eaa);
					}
					
					if(file4 != null)
					if(!file4.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file4.getBytes();
			            Path path = Paths.get(electronicsGeneralAttachmentsUrl + file4.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
			            eaa.setFileName(file4.getOriginalFilename());
			            eaa.setElectronicsGeneralAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newElectronicsGeneralAdviceAttachmentId = electronicsGeneralAdviceAttachmentService.createNewElectronicsGeneralAttachment(eaa);
					}
					
					if(file5 != null)
					if(!file5.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file5.getBytes();
			            Path path = Paths.get(electronicsGeneralAttachmentsUrl + file5.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
			            eaa.setFileName(file5.getOriginalFilename());
			            eaa.setElectronicsGeneralAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newElectronicsGeneralAdviceAttachmentId = electronicsGeneralAdviceAttachmentService.createNewElectronicsGeneralAttachment(eaa);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		List<ElectronicsGeneralAdvice> egaList = new ArrayList<ElectronicsGeneralAdvice>();
		egaList = myElectronicsNoteService.getAllNotes();
		model.addAttribute("electronicsAdvices", egaList);
		
		List<ElectronicsGeneralAdviceAttachment> eaaList = electronicsGeneralAdviceAttachmentService.getAllElectronicsGeneralAttachments();
		model.addAttribute("electronicsAdvicesAttachments", eaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = electronicsGeneralAttachmentsUrl.substring(0, electronicsGeneralAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = electronicsGeneralAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "electronicsgeneral";
		
	}
}
