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

import com.electroline.myapp.domain.WindowsAdvice;
import com.electroline.myapp.domain.WindowsAdviceAttachment;
import com.electroline.myapp.service.WindowsAdviceAttachmentService;
import com.electroline.myapp.service.WindowsNoteService;
import com.electroline.myapp.util.FileLogger;

@Controller
@PropertySource("classpath:META-INF/spring/fileUpload.properties")
@RequestMapping(value = "/windows")
public class WindowsTabController {
	
    @Value("${fileUpload.windowsAttachmentsUrl}")
    protected String windowsAttachmentsUrl;
	
	@Autowired
	@Qualifier(value = "windowsNoteService")
	WindowsNoteService myWindowsNoteService;
	
	@Autowired
	@Qualifier(value = "windowsAdviceAttachmentService")
	WindowsAdviceAttachmentService myWindowsAdviceAttachmentService;
	
	@Autowired
	@Qualifier(value = "fileLogger")
	FileLogger fileLogger;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
//		fileLogger.writeLogMessage("WindowsTabController.home()");
		
		List<WindowsAdvice> waList = new ArrayList<WindowsAdvice>();
		waList = myWindowsNoteService.getAllNotes();
		model.addAttribute("windowsAdvices", waList);
		
		List<WindowsAdviceAttachment> waaList = new ArrayList<WindowsAdviceAttachment>();
		waaList = myWindowsAdviceAttachmentService.getAllWindowsAttachments();
		model.addAttribute("windowsAdvicesAttachments", waaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = windowsAttachmentsUrl.substring(0, windowsAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "windows";
	}
	
	@RequestMapping(value = "/addnote", method = RequestMethod.GET)
	public String addNote(Model model) {
		WindowsAdvice wa = new WindowsAdvice();
		model.addAttribute("newnote", wa);
		model.addAttribute("noteType", "windows");
		return "addnote";
		
	}
	
	@RequestMapping(value = "/addfillednote", method = RequestMethod.POST)
	public String addFilledNote(ModelMap model, @RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3,
			@RequestParam("file4") MultipartFile file4, @RequestParam("file5") MultipartFile file5,
			@RequestParam Map<String, String> newAdvice) {
		
		WindowsAdvice wa = new WindowsAdvice();
		wa.setAdviceName(newAdvice.get("adviceName"));
		wa.setAdviceDescription(newAdvice.get("adviceDescription"));
		
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
			int newNoteId = myWindowsNoteService.createNewNote(wa);
			
			if(file != null)
			if(!file.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(windowsAttachmentsUrl + file.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
	            waa.setFileName(file.getOriginalFilename());
	            waa.setWindowsAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newWindowsAdviceAttachmentId = myWindowsAdviceAttachmentService.createNewWindowsAttachment(waa);
			}
			
			if(file2 != null)
			if(!file2.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file2.getBytes();
	            Path path = Paths.get(windowsAttachmentsUrl + file2.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
	            waa.setFileName(file2.getOriginalFilename());
	            waa.setWindowsAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newWindowsAdviceAttachmentId = myWindowsAdviceAttachmentService.createNewWindowsAttachment(waa);
			}
			
			if(file3 != null)
			if(!file3.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file3.getBytes();
	            Path path = Paths.get(windowsAttachmentsUrl + file3.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
	            waa.setFileName(file3.getOriginalFilename());
	            waa.setWindowsAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newWindowsAdviceAttachmentId = myWindowsAdviceAttachmentService.createNewWindowsAttachment(waa);
			}
			
			if(file4 != null)
			if(!file4.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file4.getBytes();
	            Path path = Paths.get(windowsAttachmentsUrl + file4.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
	            waa.setFileName(file4.getOriginalFilename());
	            waa.setWindowsAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newWindowsAdviceAttachmentId = myWindowsAdviceAttachmentService.createNewWindowsAttachment(waa);
			}
			if(file5 != null)
			if(!file5.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file5.getBytes();
	            Path path = Paths.get(windowsAttachmentsUrl + file5.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
	            waa.setFileName(file5.getOriginalFilename());
	            waa.setWindowsAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newWindowsAdviceAttachmentId = myWindowsAdviceAttachmentService.createNewWindowsAttachment(waa);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WindowsAdvice> waList = new ArrayList<WindowsAdvice>();
		waList = myWindowsNoteService.getAllNotes();
		model.addAttribute("windowsAdvices", waList);
		
		List<WindowsAdviceAttachment> waaList = new ArrayList<WindowsAdviceAttachment>();
		waaList = myWindowsAdviceAttachmentService.getAllWindowsAttachments();
		model.addAttribute("windowsAdvicesAttachments", waaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = windowsAttachmentsUrl.substring(0, windowsAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "windows";
		
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		List<WindowsAdviceAttachment> waaListForDelete = myWindowsAdviceAttachmentService.getAllWindowsAttachmentsForWindowsNoteId(adviceId);
		
		myWindowsAdviceAttachmentService.deleteAllWindowsAttachmentsForWindowsNoteId(adviceId);
		myWindowsNoteService.deleteNote(adviceId);
		
		for(WindowsAdviceAttachment waa : waaListForDelete) {
			Path path = Paths.get(windowsAttachmentsUrl + waa.getFileName());
			if(Files.exists(path))
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		List<WindowsAdvice> waList = new ArrayList<WindowsAdvice>();
		waList = myWindowsNoteService.getAllNotes();
		model.addAttribute("windowsAdvices", waList);
		
		List<WindowsAdviceAttachment> waaList = new ArrayList<WindowsAdviceAttachment>();
		waaList = myWindowsAdviceAttachmentService.getAllWindowsAttachments();
		model.addAttribute("windowsAdvicesAttachments", waaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = windowsAttachmentsUrl.substring(0, windowsAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "windows";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		WindowsAdvice wa = new WindowsAdvice();
		wa = myWindowsNoteService.getNote(adviceId);
		model.addAttribute("advice", wa);
		model.addAttribute("noteType", "windows");
		
		List<WindowsAdviceAttachment> waaList = myWindowsAdviceAttachmentService.getAllWindowsAttachmentsForWindowsNoteId(adviceId);
		model.addAttribute("adviceAttachmentsList", waaList);
		model.addAttribute("adviceAttachmentsValue", waaList.size());
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = windowsAttachmentsUrl.substring(0, windowsAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2, @RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4, @RequestParam(value = "file5", required = false) MultipartFile file5,
			@RequestParam Map<String, String> advice) {
		
		List<WindowsAdviceAttachment> waaListForThisAdvice = myWindowsAdviceAttachmentService.getAllWindowsAttachmentsForWindowsNoteId(Integer.parseInt(advice.get("id")));
		List<Integer> attachmentsListToDelete = new ArrayList<Integer>();
		for(WindowsAdviceAttachment waa : waaListForThisAdvice) {
			if("on".equals(advice.get("checkbox_" + waa.getId()))) attachmentsListToDelete.add(waa.getId());
		}
		
		List<WindowsAdviceAttachment> waaListForNote = myWindowsAdviceAttachmentService.getAllWindowsAttachmentsForWindowsNoteId(Integer.parseInt(advice.get("id")));	
		List<WindowsAdviceAttachment> waaListToDelete = new ArrayList<>();
		
		for(WindowsAdviceAttachment waa : waaListForNote) {
			for(int idToDelete : attachmentsListToDelete) {
				if(waa.getId() == idToDelete) waaListToDelete.add(waa);
			}
		}
		
		for(WindowsAdviceAttachment waa : waaListToDelete) {
			Path path = Paths.get(windowsAttachmentsUrl + waa.getFileName());
			if(Files.exists(path))
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		attachmentsListToDelete.forEach(x->myWindowsAdviceAttachmentService.deleteAttachment(x));
		
		WindowsAdvice wa = new WindowsAdvice();
		wa.setId(Integer.parseInt(advice.get("id")));
		wa.setAdviceName(advice.get("adviceName"));
		wa.setAdviceDescription(advice.get("adviceDescription"));
		myWindowsNoteService.updateNote(wa);
		
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
	            Path path = Paths.get(windowsAttachmentsUrl + file1.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
	            waa.setFileName(file1.getOriginalFilename());
	            waa.setWindowsAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newWindowsAdviceAttachmentId = myWindowsAdviceAttachmentService.createNewWindowsAttachment(waa);
			}
			
			if(file2 != null)
			if(!file2.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file2.getBytes();
	            Path path = Paths.get(windowsAttachmentsUrl + file2.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
	            waa.setFileName(file2.getOriginalFilename());
	            waa.setWindowsAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newWindowsAdviceAttachmentId = myWindowsAdviceAttachmentService.createNewWindowsAttachment(waa);
			}
			
			if(file3 != null)
			if(!file3.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file3.getBytes();
	            Path path = Paths.get(windowsAttachmentsUrl + file3.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
	            waa.setFileName(file3.getOriginalFilename());
	            waa.setWindowsAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newWindowsAdviceAttachmentId = myWindowsAdviceAttachmentService.createNewWindowsAttachment(waa);
			}
			
			if(file4 != null)
			if(!file4.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file4.getBytes();
	            Path path = Paths.get(windowsAttachmentsUrl + file4.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
	            waa.setFileName(file4.getOriginalFilename());
	            waa.setWindowsAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newWindowsAdviceAttachmentId = myWindowsAdviceAttachmentService.createNewWindowsAttachment(waa);
			}
			
			if(file5 != null)
			if(!file5.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file5.getBytes();
	            Path path = Paths.get(windowsAttachmentsUrl + file5.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
	            waa.setFileName(file5.getOriginalFilename());
	            waa.setWindowsAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newWindowsAdviceAttachmentId = myWindowsAdviceAttachmentService.createNewWindowsAttachment(waa);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WindowsAdvice> waList = new ArrayList<WindowsAdvice>();
		waList = myWindowsNoteService.getAllNotes();
		model.addAttribute("windowsAdvices", waList);
		
		List<WindowsAdviceAttachment> waaList = new ArrayList<WindowsAdviceAttachment>();
		waaList = myWindowsAdviceAttachmentService.getAllWindowsAttachments();
		model.addAttribute("windowsAdvicesAttachments", waaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = windowsAttachmentsUrl.substring(0, windowsAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "windows";
		
	}
}
