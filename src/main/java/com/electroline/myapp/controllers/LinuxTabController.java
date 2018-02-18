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

import com.electroline.myapp.domain.LinuxAdvice;
import com.electroline.myapp.domain.LinuxAdviceAttachment;
import com.electroline.myapp.service.LinuxAdviceAttachmentService;
import com.electroline.myapp.service.LinuxNoteService;

@Controller
@PropertySource("classpath:META-INF/spring/fileUpload.properties")
@RequestMapping(value = "linux")
public class LinuxTabController {
	
	@Value("${fileUpload.linuxAttachmentsUrl}")
	protected String linuxAttachmentsUrl;

	@Autowired
	@Qualifier(value = "linuxNoteService")
	LinuxNoteService myLinuxNoteService;
	
	@Autowired
	@Qualifier(value = "linuxAdviceAttachmentService")
	LinuxAdviceAttachmentService linuxAdviceAttachmentService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		List<LinuxAdvice> laList = new ArrayList<LinuxAdvice>();
		laList = myLinuxNoteService.getAllNotes();
		model.addAttribute("linuxAdvices", laList);
		
		List<LinuxAdviceAttachment> laaList = new ArrayList<>();
		laaList = linuxAdviceAttachmentService.getAllLinuxAttachments();
		model.addAttribute("linuxAdvicesAttachments", laaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = linuxAttachmentsUrl.substring(0, linuxAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = linuxAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "linux";
	}
	
	@RequestMapping(value = "/addnote", method = RequestMethod.GET)
	public String addNote(Model model) {
		LinuxAdvice la = new LinuxAdvice();
		model.addAttribute("newnote", la);
		model.addAttribute("noteType", "linux");
		return "addnote";
		
	}
	
	@RequestMapping(value = "/addfillednote", method = RequestMethod.POST)
	public String addFilledNote(ModelMap model, @RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3,
			@RequestParam("file4") MultipartFile file4, @RequestParam("file5") MultipartFile file5,
			@RequestParam Map<String, String> newAdvice) {
		
		LinuxAdvice la = new LinuxAdvice();
		la.setAdviceName(newAdvice.get("adviceName"));
		la.setAdviceDescription(newAdvice.get("adviceDescription"));
		
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
			int newNoteId = myLinuxNoteService.createNewNote(la);
			
			if(file != null)
			if(!file.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(linuxAttachmentsUrl + file.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
	            laa.setFileName(file.getOriginalFilename());
	            laa.setLinuxAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newLinuxAdviceAttachmentId = linuxAdviceAttachmentService.createNewLinuxAttachment(laa);
			}
			
			if(file2 != null)
			if(!file2.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file2.getBytes();
	            Path path = Paths.get(linuxAttachmentsUrl + file2.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
	            laa.setFileName(file2.getOriginalFilename());
	            laa.setLinuxAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newLinuxAdviceAttachmentId = linuxAdviceAttachmentService.createNewLinuxAttachment(laa);
			}
			
			if(file3 != null)
			if(!file3.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file3.getBytes();
	            Path path = Paths.get(linuxAttachmentsUrl + file3.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
	            laa.setFileName(file3.getOriginalFilename());
	            laa.setLinuxAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newLinuxAdviceAttachmentId = linuxAdviceAttachmentService.createNewLinuxAttachment(laa);
			}
			
			if(file4 != null)
			if(!file4.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file4.getBytes();
	            Path path = Paths.get(linuxAttachmentsUrl + file4.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
	            laa.setFileName(file4.getOriginalFilename());
	            laa.setLinuxAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newLinuxAdviceAttachmentId = linuxAdviceAttachmentService.createNewLinuxAttachment(laa);
			}
			
			if(file5 != null)
			if(!file5.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file5.getBytes();
	            Path path = Paths.get(linuxAttachmentsUrl + file5.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
	            laa.setFileName(file5.getOriginalFilename());
	            laa.setLinuxAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newLinuxAdviceAttachmentId = linuxAdviceAttachmentService.createNewLinuxAttachment(laa);
			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<LinuxAdvice> laList = new ArrayList<LinuxAdvice>();
		laList = myLinuxNoteService.getAllNotes();
		model.addAttribute("linuxAdvices", laList);
		
		List<LinuxAdviceAttachment> laaList = new ArrayList<>();
		laaList = linuxAdviceAttachmentService.getAllLinuxAttachments();
		model.addAttribute("linuxAdvicesAttachments", laaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = linuxAttachmentsUrl.substring(0, linuxAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = linuxAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "linux";
		
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {	
		List<LinuxAdviceAttachment> laaListForDelete = linuxAdviceAttachmentService.getAllLinuxAttachmentsForLinuxNoteId(adviceId);
		
		linuxAdviceAttachmentService.deleteAllLinuxAttachmentsForLinuxNoteId(adviceId);
		myLinuxNoteService.deleteNote(adviceId);
		
		for(LinuxAdviceAttachment laa : laaListForDelete) {
			Path path = Paths.get(linuxAttachmentsUrl + laa.getFileName());
			if(Files.exists(path))
			try {
				Files.delete(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<LinuxAdvice> laList = new ArrayList<LinuxAdvice>();
		laList = myLinuxNoteService.getAllNotes();
		model.addAttribute("linuxAdvices", laList);
		
		List<LinuxAdviceAttachment> laaList = new ArrayList<>();
		laaList = linuxAdviceAttachmentService.getAllLinuxAttachments();
		model.addAttribute("linuxAdvicesAttachments", laaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = linuxAttachmentsUrl.substring(0, linuxAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = linuxAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "linux";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		LinuxAdvice la = new LinuxAdvice();
		la = myLinuxNoteService.getNote(adviceId);
		model.addAttribute("advice", la);
		model.addAttribute("noteType", "linux");
		
		List<LinuxAdviceAttachment> laaList = new ArrayList<>();
		laaList = linuxAdviceAttachmentService.getAllLinuxAttachments();
		model.addAttribute("adviceAttachmentsList", laaList);
		model.addAttribute("adviceAttachmentsValue", laaList.size());
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = linuxAttachmentsUrl.substring(0, linuxAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = linuxAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2, @RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4, @RequestParam(value = "file5", required = false) MultipartFile file5,
			@RequestParam Map<String, String> advice) {
		
		List<LinuxAdviceAttachment> laaListForThisAdvice = linuxAdviceAttachmentService.getAllLinuxAttachmentsForLinuxNoteId(Integer.parseInt(advice.get("id")));
		List<Integer> attachmentsListToDelete = new ArrayList<>();
		for(LinuxAdviceAttachment laa : laaListForThisAdvice) {
			if("on".equals(advice.get("checkbox_" + laa.getId()))) attachmentsListToDelete.add(laa.getId());
		}
		
		List<LinuxAdviceAttachment> laaListForNote = linuxAdviceAttachmentService.getAllLinuxAttachmentsForLinuxNoteId(Integer.parseInt(advice.get("id")));
		List<LinuxAdviceAttachment> laaListToDelete = new ArrayList<>();
		
		for(LinuxAdviceAttachment laa : laaListForNote) {
			for(int idToDelete : attachmentsListToDelete) {
				if(laa.getId() == idToDelete) laaListToDelete.add(laa);
			}
		}
		
		for(LinuxAdviceAttachment laa : laaListToDelete) {
			Path path = Paths.get(linuxAttachmentsUrl + laa.getFileName());
			if(Files.exists(path))
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		attachmentsListToDelete.forEach(x->linuxAdviceAttachmentService.deleteAttachment(x));
		
		LinuxAdvice la = new LinuxAdvice();
		la.setId(Integer.parseInt(advice.get("id")));
		la.setAdviceName(advice.get("adviceName"));
		la.setAdviceDescription(advice.get("adviceDescription"));
		myLinuxNoteService.updateNote(la);
		
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
	            Path path = Paths.get(linuxAttachmentsUrl + file1.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
	            laa.setFileName(file1.getOriginalFilename());
	            laa.setLinuxAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newLinuxAdviceAttachmentId = linuxAdviceAttachmentService.createNewLinuxAttachment(laa);
			}
			
			if(file2 != null)
			if(!file2.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file2.getBytes();
	            Path path = Paths.get(linuxAttachmentsUrl + file2.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
	            laa.setFileName(file2.getOriginalFilename());
	            laa.setLinuxAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newLinuxAdviceAttachmentId = linuxAdviceAttachmentService.createNewLinuxAttachment(laa);
			}
			
			if(file3 != null)
			if(!file3.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file3.getBytes();
	            Path path = Paths.get(linuxAttachmentsUrl + file3.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
	            laa.setFileName(file3.getOriginalFilename());
	            laa.setLinuxAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newLinuxAdviceAttachmentId = linuxAdviceAttachmentService.createNewLinuxAttachment(laa);
			}
			
			if(file4 != null)
			if(!file4.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file4.getBytes();
	            Path path = Paths.get(linuxAttachmentsUrl + file4.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
	            laa.setFileName(file4.getOriginalFilename());
	            laa.setLinuxAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newLinuxAdviceAttachmentId = linuxAdviceAttachmentService.createNewLinuxAttachment(laa);
			}
			
			if(file5 != null)
			if(!file5.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file5.getBytes();
	            Path path = Paths.get(linuxAttachmentsUrl + file5.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
	            laa.setFileName(file5.getOriginalFilename());
	            laa.setLinuxAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newLinuxAdviceAttachmentId = linuxAdviceAttachmentService.createNewLinuxAttachment(laa);
			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<LinuxAdvice> laList = new ArrayList<LinuxAdvice>();
		laList = myLinuxNoteService.getAllNotes();
		model.addAttribute("linuxAdvices", laList);
		
		List<LinuxAdviceAttachment> laaList = new ArrayList<>();
		laaList = linuxAdviceAttachmentService.getAllLinuxAttachments();
		model.addAttribute("linuxAdvicesAttachments", laaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = linuxAttachmentsUrl.substring(0, linuxAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = linuxAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "linux";
		
	}
}
