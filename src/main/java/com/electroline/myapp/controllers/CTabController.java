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

import com.electroline.myapp.domain.CAdvice;
import com.electroline.myapp.domain.CAdviceAttachment;
import com.electroline.myapp.service.CAdviceAttachmentService;
import com.electroline.myapp.service.CNoteService;

@Controller
@PropertySource("classpath:META-INF/spring/fileUpload.properties")
@RequestMapping(value = "c")
public class CTabController {
	
	@Value("${fileUpload.cAttachmentsUrl}")
	protected String cAttachmentsUrl;

	@Autowired
	@Qualifier(value = "cNoteService")
	CNoteService myCNoteService;
	
	@Autowired
	@Qualifier(value = "cAdviceAttachmentService")
	CAdviceAttachmentService cAdviceAttachmentService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		List<CAdvice> caList = new ArrayList<CAdvice>();
		caList = myCNoteService.getAllNotes();
		model.addAttribute("cAdvices", caList);
		
		List<CAdviceAttachment> caaList = new ArrayList<>();
		caaList = cAdviceAttachmentService.getAllCAttachments();
		model.addAttribute("cAdvicesAttachments", caaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = cAttachmentsUrl.substring(0, cAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = cAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "c";
	}
	
	@RequestMapping(value = "/addnote", method = RequestMethod.GET)
	public String addNote(Model model) {
		CAdvice ca = new CAdvice();
		model.addAttribute("newnote", ca);
		model.addAttribute("noteType", "c");
		return "addnote";
		
	}
	
	@RequestMapping(value = "/addfillednote", method = RequestMethod.POST)
	public String addFilledNote(ModelMap model, @RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3,
			@RequestParam("file4") MultipartFile file4, @RequestParam("file5") MultipartFile file5,
			@RequestParam Map<String, String> newAdvice) {
		
		CAdvice ca = new CAdvice();
		ca.setAdviceName(newAdvice.get("adviceName"));
		ca.setAdviceDescription(newAdvice.get("adviceDescription"));
		
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
			int newNoteId = myCNoteService.createNewNote(ca);
			
			if(file != null)
			if(!file.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(cAttachmentsUrl + file.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            CAdviceAttachment caa = new CAdviceAttachment();
	            caa.setFileName(file.getOriginalFilename());
	            caa.setcAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newCAdviceAttachmentId = cAdviceAttachmentService.createNewCAttachment(caa);
			}
			
			if(file2 != null)
			if(!file2.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file2.getBytes();
	            Path path = Paths.get(cAttachmentsUrl + file2.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            CAdviceAttachment caa = new CAdviceAttachment();
	            caa.setFileName(file2.getOriginalFilename());
	            caa.setcAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newCAdviceAttachmentId = cAdviceAttachmentService.createNewCAttachment(caa);
			}
			
			if(file3 != null)
			if(!file3.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file3.getBytes();
	            Path path = Paths.get(cAttachmentsUrl + file3.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            CAdviceAttachment caa = new CAdviceAttachment();
	            caa.setFileName(file3.getOriginalFilename());
	            caa.setcAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newCAdviceAttachmentId = cAdviceAttachmentService.createNewCAttachment(caa);
			}
			
			if(file4 != null)
			if(!file4.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file4.getBytes();
	            Path path = Paths.get(cAttachmentsUrl + file4.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            CAdviceAttachment caa = new CAdviceAttachment();
	            caa.setFileName(file4.getOriginalFilename());
	            caa.setcAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newCAdviceAttachmentId = cAdviceAttachmentService.createNewCAttachment(caa);
			}
			if(file5 != null)
			if(!file5.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file5.getBytes();
	            Path path = Paths.get(cAttachmentsUrl + file5.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            CAdviceAttachment caa = new CAdviceAttachment();
	            caa.setFileName(file5.getOriginalFilename());
	            caa.setcAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newCAdviceAttachmentId = cAdviceAttachmentService.createNewCAttachment(caa);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<CAdvice> caList = new ArrayList<CAdvice>();
		caList = myCNoteService.getAllNotes();
		model.addAttribute("cAdvices", caList);
		
		List<CAdviceAttachment> caaList = new ArrayList<>();
		caaList = cAdviceAttachmentService.getAllCAttachments();
		model.addAttribute("cAdvicesAttachments", caaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = cAttachmentsUrl.substring(0, cAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = cAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "c";
		
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {	
		List<CAdviceAttachment> caaListForDelete = cAdviceAttachmentService.getAllCAttachmentsForCNoteId(adviceId);
		
		cAdviceAttachmentService.deleteAllCAttachmentsForCNoteId(adviceId);
		myCNoteService.deleteNote(adviceId);
		
		for(CAdviceAttachment caa : caaListForDelete) {
			Path path = Paths.get(cAttachmentsUrl + caa.getFileName());
			if(Files.exists(path))
				try {
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		List<CAdvice> caList = new ArrayList<CAdvice>();
		caList = myCNoteService.getAllNotes();
		model.addAttribute("cAdvices", caList);
		
		List<CAdviceAttachment> caaList = new ArrayList<>();
		caaList = cAdviceAttachmentService.getAllCAttachments();
		model.addAttribute("cAdvicesAttachments", caaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = cAttachmentsUrl.substring(0, cAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = cAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "c";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		CAdvice ca = new CAdvice();
		ca = myCNoteService.getNote(adviceId);
		model.addAttribute("advice", ca);
		model.addAttribute("noteType", "c");
		
		List<CAdviceAttachment> caaList = cAdviceAttachmentService.getAllCAttachmentsForCNoteId(adviceId);
		model.addAttribute("adviceAttachmentsList", caaList);
		model.addAttribute("adviceAttachmentsValue", caaList.size());
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = cAttachmentsUrl.substring(0, cAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = cAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2, @RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4, @RequestParam(value = "file5", required = false) MultipartFile file5,
			@RequestParam Map<String, String> advice) {
		
		List<CAdviceAttachment> caaListForThisAdvice = cAdviceAttachmentService.getAllCAttachmentsForCNoteId(Integer.parseInt(advice.get("id")));
		List<Integer> attachmentsListToDelete = new ArrayList<>();
		for(CAdviceAttachment caa : caaListForThisAdvice) {
			if("on".equals(advice.get("checkbox_" + caa.getId()))) attachmentsListToDelete.add(caa.getId());
		}
		
		List<CAdviceAttachment> caaListForNote = cAdviceAttachmentService.getAllCAttachmentsForCNoteId(Integer.parseInt(advice.get("id")));
		List<CAdviceAttachment> caaListToDelete = new ArrayList<>();
		
		for(CAdviceAttachment caa : caaListForNote) {
			for(int idToDelete : attachmentsListToDelete) {
				if(caa.getId() == idToDelete) caaListToDelete.add(caa);
			}
		}
		
		for(CAdviceAttachment caa : caaListToDelete) {
			Path path = Paths.get(cAttachmentsUrl + caa.getFileName());
			if(Files.exists(path))
				try {
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		attachmentsListToDelete.forEach(x->cAdviceAttachmentService.deleteAttachment(x));
		
		CAdvice ca = new CAdvice();
		ca.setId(Integer.parseInt(advice.get("id")));
		ca.setAdviceName(advice.get("adviceName"));
		ca.setAdviceDescription(advice.get("adviceDescription"));
		myCNoteService.updateNote(ca);

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
			            Path path = Paths.get(cAttachmentsUrl + file1.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            CAdviceAttachment caa = new CAdviceAttachment();
			            caa.setFileName(file1.getOriginalFilename());
			            caa.setcAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newCAdviceAttachmentId = cAdviceAttachmentService.createNewCAttachment(caa);
					}
					
					if(file2 != null)
					if(!file2.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file2.getBytes();
			            Path path = Paths.get(cAttachmentsUrl + file2.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            CAdviceAttachment caa = new CAdviceAttachment();
			            caa.setFileName(file2.getOriginalFilename());
			            caa.setcAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newCAdviceAttachmentId = cAdviceAttachmentService.createNewCAttachment(caa);
					}
					
					if(file3 != null)
					if(!file3.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file3.getBytes();
			            Path path = Paths.get(cAttachmentsUrl + file3.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            CAdviceAttachment caa = new CAdviceAttachment();
			            caa.setFileName(file3.getOriginalFilename());
			            caa.setcAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newCAdviceAttachmentId = cAdviceAttachmentService.createNewCAttachment(caa);
					}
					
					if(file4 != null)
					if(!file4.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file4.getBytes();
			            Path path = Paths.get(cAttachmentsUrl + file4.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            CAdviceAttachment caa = new CAdviceAttachment();
			            caa.setFileName(file4.getOriginalFilename());
			            caa.setcAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newCAdviceAttachmentId = cAdviceAttachmentService.createNewCAttachment(caa);
					}
					
					if(file5 != null)
					if(!file5.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file5.getBytes();
			            Path path = Paths.get(cAttachmentsUrl + file5.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            CAdviceAttachment caa = new CAdviceAttachment();
			            caa.setFileName(file5.getOriginalFilename());
			            caa.setcAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newCAdviceAttachmentId = cAdviceAttachmentService.createNewCAttachment(caa);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		List<CAdvice> caList = new ArrayList<CAdvice>();
		caList = myCNoteService.getAllNotes();
		model.addAttribute("cAdvices", caList);
		
		List<CAdviceAttachment> caaList = new ArrayList<>();
		caaList = cAdviceAttachmentService.getAllCAttachments();
		model.addAttribute("cAdvicesAttachments", caaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = cAttachmentsUrl.substring(0, cAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = cAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "c";
	}
}
