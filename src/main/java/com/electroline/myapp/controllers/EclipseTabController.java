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

import com.electroline.myapp.domain.EclipseAdvice;
import com.electroline.myapp.domain.EclipseAdviceAttachment;
import com.electroline.myapp.service.EclipseAdviceAttachmentService;
import com.electroline.myapp.service.EclipseNoteService;

@Controller
@PropertySource("classpath:META-INF/spring/fileUpload.properties")
@RequestMapping(value = "eclipse")
public class EclipseTabController {
	
	@Value("${fileUpload.eclipseAttachmentsUrl}")
	protected String eclipseAttachmentsUrl;
	
	@Autowired
	@Qualifier(value = "eclipseNoteService")
	private EclipseNoteService myEclipseNoteService;
	
	@Autowired
	@Qualifier("eclipseAdviceAttachmentService")
	EclipseAdviceAttachmentService eclipseAdviceAttachmentService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		List<EclipseAdvice> eaList = new ArrayList<EclipseAdvice>();
		eaList = myEclipseNoteService.getAllNotes();
		model.addAttribute("eclipseAdvices", eaList);
		
		List<EclipseAdviceAttachment> eaaList = new ArrayList<>();
		eaaList = eclipseAdviceAttachmentService.getAllEclipseAttachments();
		model.addAttribute("eclipseAdvicesAttachments", eaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = eclipseAttachmentsUrl.substring(0, eclipseAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = eclipseAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
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
	public String addFilledNote(ModelMap model, @RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3,
			@RequestParam("file4") MultipartFile file4, @RequestParam("file5") MultipartFile file5,
			@RequestParam Map<String, String> newAdvice) {
		
		EclipseAdvice ea = new EclipseAdvice();
		ea.setAdviceName(newAdvice.get("adviceName"));
		ea.setAdviceDescription(newAdvice.get("adviceDescription"));
		
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
					int newNoteId = myEclipseNoteService.createNewNote(ea);
					
					if(file != null)
					if(!file.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file.getBytes();
			            Path path = Paths.get(eclipseAttachmentsUrl + file.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
			            eaa.setFileName(file.getOriginalFilename());
			            eaa.setEclipseAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newEclipseAdviceAttachmentId = eclipseAdviceAttachmentService.createNewEclipseAttachment(eaa);
					}
					
					if(file2 != null)
					if(!file2.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file2.getBytes();
			            Path path = Paths.get(eclipseAttachmentsUrl + file2.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
			            eaa.setFileName(file2.getOriginalFilename());
			            eaa.setEclipseAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newEclipseAdviceAttachmentId = eclipseAdviceAttachmentService.createNewEclipseAttachment(eaa);
					}
					
					if(file3 != null)
					if(!file3.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file3.getBytes();
			            Path path = Paths.get(eclipseAttachmentsUrl + file3.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
			            eaa.setFileName(file3.getOriginalFilename());
			            eaa.setEclipseAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newEclipseAdviceAttachmentId = eclipseAdviceAttachmentService.createNewEclipseAttachment(eaa);
					}
					
					if(file4 != null)
					if(!file4.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file4.getBytes();
			            Path path = Paths.get(eclipseAttachmentsUrl + file4.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
			            eaa.setFileName(file4.getOriginalFilename());
			            eaa.setEclipseAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newEclipseAdviceAttachmentId = eclipseAdviceAttachmentService.createNewEclipseAttachment(eaa);
					}
					if(file5 != null)
					if(!file5.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file5.getBytes();
			            Path path = Paths.get(eclipseAttachmentsUrl + file5.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
			            eaa.setFileName(file5.getOriginalFilename());
			            eaa.setEclipseAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newEclipseAdviceAttachmentId = eclipseAdviceAttachmentService.createNewEclipseAttachment(eaa);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		List<EclipseAdvice> eaList = new ArrayList<EclipseAdvice>();
		eaList = myEclipseNoteService.getAllNotes();
		model.addAttribute("eclipseAdvices", eaList);
		
		List<EclipseAdviceAttachment> eaaList = new ArrayList<>();
		eaaList = eclipseAdviceAttachmentService.getAllEclipseAttachments();
		model.addAttribute("eclipseAdvicesAttachments", eaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = eclipseAttachmentsUrl.substring(0, eclipseAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = eclipseAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "eclipse";
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		List<EclipseAdviceAttachment> eaaListForDelete = eclipseAdviceAttachmentService.getAllEclipseAttachmentsForEclipseNoteId(adviceId);
		
		eclipseAdviceAttachmentService.deleteAllEclipseAttachmentsForEclipseNoteId(adviceId);
		myEclipseNoteService.deleteNote(adviceId);
		
		for(EclipseAdviceAttachment eaa : eaaListForDelete) {
			Path path = Paths.get(eclipseAttachmentsUrl + eaa.getFileName());
			if(Files.exists(path))
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		List<EclipseAdvice> eaList = new ArrayList<EclipseAdvice>();
		eaList = myEclipseNoteService.getAllNotes();
		model.addAttribute("eclipseAdvices", eaList);
		
		List<EclipseAdviceAttachment> eaaList = new ArrayList<>();
		eaaList = eclipseAdviceAttachmentService.getAllEclipseAttachments();
		model.addAttribute("eclipseAdvicesAttachments", eaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = eclipseAttachmentsUrl.substring(0, eclipseAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = eclipseAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "eclipse";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		EclipseAdvice ea = new EclipseAdvice();
		ea = myEclipseNoteService.getNote(adviceId);
		model.addAttribute("advice", ea);
		model.addAttribute("noteType", "eclipse");
		
		List<EclipseAdviceAttachment> eaaList = eclipseAdviceAttachmentService.getAllEclipseAttachmentsForEclipseNoteId(adviceId);
		model.addAttribute("adviceAttachmentsList", eaaList);
		model.addAttribute("adviceAttachmentsValue", eaaList.size());
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = eclipseAttachmentsUrl.substring(0, eclipseAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = eclipseAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2, @RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4, @RequestParam(value = "file5", required = false) MultipartFile file5,
			@RequestParam Map<String, String> advice) {
		
		List<EclipseAdviceAttachment> eaaListForThisAdvice = eclipseAdviceAttachmentService.getAllEclipseAttachmentsForEclipseNoteId(Integer.parseInt(advice.get("id")));
		List<Integer> attachmentsListToDelete = new ArrayList<>();
		for(EclipseAdviceAttachment eaa : eaaListForThisAdvice) {
			if("on".equals(advice.get("checkbox_" + eaa.getId()))) attachmentsListToDelete.add(eaa.getId());
		}
		
		List<EclipseAdviceAttachment> eaaListForNote = eclipseAdviceAttachmentService.getAllEclipseAttachmentsForEclipseNoteId(Integer.parseInt(advice.get("id")));
		List<EclipseAdviceAttachment> eaaListToDelete = new ArrayList<>();
		
		for(EclipseAdviceAttachment eaa : eaaListForNote) {
			for(int idToDelete : attachmentsListToDelete) {
				if(eaa.getId() == idToDelete) eaaListToDelete.add(eaa);
			}
		}
		
		for(EclipseAdviceAttachment eaa : eaaListToDelete) {
			Path path = Paths.get(eclipseAttachmentsUrl + eaa.getFileName());
			if(Files.exists(path))
				try {
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		attachmentsListToDelete.forEach(x->eclipseAdviceAttachmentService.deleteAttachment(x));
		
		EclipseAdvice ea = new EclipseAdvice();
		ea.setId(Integer.parseInt(advice.get("id")));
		ea.setAdviceName(advice.get("adviceName"));
		ea.setAdviceDescription(advice.get("adviceDescription"));
		myEclipseNoteService.updateNote(ea);
		
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
			            Path path = Paths.get(eclipseAttachmentsUrl + file1.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
			            eaa.setFileName(file1.getOriginalFilename());
			            eaa.setEclipseAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newEclipseAdviceAttachmentId = eclipseAdviceAttachmentService.createNewEclipseAttachment(eaa);
					}
					
					if(file2 != null)
					if(!file2.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file2.getBytes();
			            Path path = Paths.get(eclipseAttachmentsUrl + file2.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
			            eaa.setFileName(file2.getOriginalFilename());
			            eaa.setEclipseAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newEclipseAdviceAttachmentId = eclipseAdviceAttachmentService.createNewEclipseAttachment(eaa);
					}
					
					if(file3 != null)
					if(!file3.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file3.getBytes();
			            Path path = Paths.get(eclipseAttachmentsUrl + file3.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
			            eaa.setFileName(file3.getOriginalFilename());
			            eaa.setEclipseAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newEclipseAdviceAttachmentId = eclipseAdviceAttachmentService.createNewEclipseAttachment(eaa);
					}
					
					if(file4 != null)
					if(!file4.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file4.getBytes();
			            Path path = Paths.get(eclipseAttachmentsUrl + file4.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
			            eaa.setFileName(file4.getOriginalFilename());
			            eaa.setEclipseAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newEclipseAdviceAttachmentId = eclipseAdviceAttachmentService.createNewEclipseAttachment(eaa);
					}
					
					if(file5 != null)
					if(!file5.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file5.getBytes();
			            Path path = Paths.get(eclipseAttachmentsUrl + file5.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
			            eaa.setFileName(file5.getOriginalFilename());
			            eaa.setEclipseAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newEclipseAdviceAttachmentId = eclipseAdviceAttachmentService.createNewEclipseAttachment(eaa);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		List<EclipseAdvice> eaList = new ArrayList<EclipseAdvice>();
		eaList = myEclipseNoteService.getAllNotes();
		model.addAttribute("eclipseAdvices", eaList);
		
		List<EclipseAdviceAttachment> eaaList = new ArrayList<>();
		eaaList = eclipseAdviceAttachmentService.getAllEclipseAttachments();
		model.addAttribute("eclipseAdvicesAttachments", eaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = eclipseAttachmentsUrl.substring(0, eclipseAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = eclipseAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "eclipse";
	}
	
}
