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

import com.electroline.myapp.domain.MicroprocessorAdvice;
import com.electroline.myapp.domain.MicroprocessorAdviceAttachment;
import com.electroline.myapp.service.MicroprocessorAdviceAttachmentService;
import com.electroline.myapp.service.MicroprocessorNoteService;

@Controller
@PropertySource("classpath:META-INF/spring/fileUpload.properties")
@RequestMapping(value = "/microprocessors")
public class MicroprocessorsTabController {
	
	@Value("${fileUpload.microprocessorAttachmentsUrl}")
	protected String microprocessorAttachmentsUrl;
	
	@Autowired
	@Qualifier(value = "microprocessorNoteService")
	private MicroprocessorNoteService myMicroprocessorNewNoteService;
	
	@Autowired
	@Qualifier("microprocessorAdviceAttachmentService")
	MicroprocessorAdviceAttachmentService microprocessorAdviceAttachmentService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home( Model model) {
		
		List<MicroprocessorAdvice> maList = new ArrayList<MicroprocessorAdvice>();
		maList = myMicroprocessorNewNoteService.getAllNotes();
		model.addAttribute("microprocessorAdvices", maList);
		
		List<MicroprocessorAdviceAttachment> maaList = microprocessorAdviceAttachmentService.getAllMicroprocessorAttachments();
		model.addAttribute("microprocessorAdvicesAttachments", maaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = microprocessorAttachmentsUrl.substring(0, microprocessorAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = microprocessorAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
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
	public String addFilledNote(ModelMap model, @RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3,
			@RequestParam("file4") MultipartFile file4, @RequestParam("file5") MultipartFile file5,
			@RequestParam Map<String, String> newAdvice) {
		
		MicroprocessorAdvice ma = new MicroprocessorAdvice();
		ma.setAdviceName(newAdvice.get("adviceName"));
		ma.setAdviceDescription(newAdvice.get("adviceDescription"));
		
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
					int newNoteId = myMicroprocessorNewNoteService.createNewNote(ma);
					
					if(file != null)
					if(!file.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file.getBytes();
			            Path path = Paths.get(microprocessorAttachmentsUrl + file.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
			            maa.setFileName(file.getOriginalFilename());
			            maa.setMicroprocessorAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newMicroprocessorAdviceAttachmentId = microprocessorAdviceAttachmentService.createNewMicroprocessorAttachment(maa);
					}
					
					if(file2 != null)
					if(!file2.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file2.getBytes();
			            Path path = Paths.get(microprocessorAttachmentsUrl + file2.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
			            maa.setFileName(file2.getOriginalFilename());
			            maa.setMicroprocessorAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newmicroprocessorAdviceAttachmentId = microprocessorAdviceAttachmentService.createNewMicroprocessorAttachment(maa);
					}
					
					if(file3 != null)
					if(!file3.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file3.getBytes();
			            Path path = Paths.get(microprocessorAttachmentsUrl + file3.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
			            maa.setFileName(file3.getOriginalFilename());
			            maa.setMicroprocessorAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newMicroprocessorAdviceAttachmentId = microprocessorAdviceAttachmentService.createNewMicroprocessorAttachment(maa);
					}
					
					if(file4 != null)
					if(!file4.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file4.getBytes();
			            Path path = Paths.get(microprocessorAttachmentsUrl + file4.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
			            maa.setFileName(file4.getOriginalFilename());
			            maa.setMicroprocessorAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newMicroprocessorAdviceAttachmentId = microprocessorAdviceAttachmentService.createNewMicroprocessorAttachment(maa);
					}
					if(file5 != null)
					if(!file5.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file5.getBytes();
			            Path path = Paths.get(microprocessorAttachmentsUrl + file5.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
			            maa.setFileName(file5.getOriginalFilename());
			            maa.setMicroprocessorAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newMicroprocessorAdviceAttachmentId = microprocessorAdviceAttachmentService.createNewMicroprocessorAttachment(maa);
					}
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		List<MicroprocessorAdvice> maList = new ArrayList<MicroprocessorAdvice>();
		maList = myMicroprocessorNewNoteService.getAllNotes();
		model.addAttribute("microprocessorAdvices", maList);
		
		List<MicroprocessorAdviceAttachment> maaList = microprocessorAdviceAttachmentService.getAllMicroprocessorAttachments();
		model.addAttribute("microprocessorAdvicesAttachments", maaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = microprocessorAttachmentsUrl.substring(0, microprocessorAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = microprocessorAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "microprocessors";
		
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		List<MicroprocessorAdviceAttachment> maaListForDelete = microprocessorAdviceAttachmentService.getAllMicroprocessorAttachmentsForMicroprocessorNoteId(adviceId);
		
		microprocessorAdviceAttachmentService.deleteAllMicroprocessorAttachmentsForMicroprocessorNoteId(adviceId);
		myMicroprocessorNewNoteService.deleteNote(adviceId);
		
		for(MicroprocessorAdviceAttachment maa : maaListForDelete) {
			Path path = Paths.get(microprocessorAttachmentsUrl + maa.getFileName());
			if(Files.exists(path))
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		List<MicroprocessorAdvice> maList = new ArrayList<MicroprocessorAdvice>();
		maList = myMicroprocessorNewNoteService.getAllNotes();
		model.addAttribute("microprocessorAdvices", maList);
		
		List<MicroprocessorAdviceAttachment> maaList = microprocessorAdviceAttachmentService.getAllMicroprocessorAttachments();
		model.addAttribute("microprocessorAdvicesAttachments", maaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = microprocessorAttachmentsUrl.substring(0, microprocessorAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = microprocessorAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "microprocessors";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		MicroprocessorAdvice ma = new MicroprocessorAdvice();
		ma = myMicroprocessorNewNoteService.getNote(adviceId);
		model.addAttribute("advice", ma);
		model.addAttribute("noteType", "microprocessors");
		
		List<MicroprocessorAdviceAttachment> maaList = microprocessorAdviceAttachmentService.getAllMicroprocessorAttachmentsForMicroprocessorNoteId(adviceId);
		model.addAttribute("adviceAttachmentsList", maaList);
		model.addAttribute("adviceAttachmentsValue", maaList.size());
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = microprocessorAttachmentsUrl.substring(0, microprocessorAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = microprocessorAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2, @RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4, @RequestParam(value = "file5", required = false) MultipartFile file5,
			@RequestParam Map<String, String> advice) {
		
		List<MicroprocessorAdviceAttachment> maaListForThisAdvice = microprocessorAdviceAttachmentService.getAllMicroprocessorAttachmentsForMicroprocessorNoteId(Integer.parseInt(advice.get("id")));
		List<Integer> attachmentsListToDelete = new ArrayList<>();
		for(MicroprocessorAdviceAttachment maa : maaListForThisAdvice) {
			if("on".equals(advice.get("checkbox_" + maa.getId()))) attachmentsListToDelete.add(maa.getId());
		}
		
		List<MicroprocessorAdviceAttachment> maaListForNote = microprocessorAdviceAttachmentService.getAllMicroprocessorAttachmentsForMicroprocessorNoteId(Integer.parseInt(advice.get("id")));
		List<MicroprocessorAdviceAttachment> maaListToDelete = new ArrayList<>();
		
		for(MicroprocessorAdviceAttachment maa : maaListForNote) {
			for(int idToDelete : attachmentsListToDelete) {
				if(maa.getId() == idToDelete) maaListToDelete.add(maa);
			}
		}
		
		for(MicroprocessorAdviceAttachment maa : maaListToDelete) {
			Path path = Paths.get(microprocessorAttachmentsUrl + maa.getFileName());
			if(Files.exists(path))
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		attachmentsListToDelete.forEach(x->microprocessorAdviceAttachmentService.deleteAttachment(x));
		
		MicroprocessorAdvice ma = new MicroprocessorAdvice();
		ma.setId(Integer.parseInt(advice.get("id")));
		ma.setAdviceName(advice.get("adviceName"));
		ma.setAdviceDescription(advice.get("adviceDescription"));
		myMicroprocessorNewNoteService.updateNote(ma);
		
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
			            Path path = Paths.get(microprocessorAttachmentsUrl + file1.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
			            maa.setFileName(file1.getOriginalFilename());
			            maa.setMicroprocessorAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newMicroprocessorAdviceAttachmentId = microprocessorAdviceAttachmentService.createNewMicroprocessorAttachment(maa);
					}
					
					if(file2 != null)
					if(!file2.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file2.getBytes();
			            Path path = Paths.get(microprocessorAttachmentsUrl + file2.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
			            maa.setFileName(file2.getOriginalFilename());
			            maa.setMicroprocessorAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newMicroprocessorAdviceAttachmentId = microprocessorAdviceAttachmentService.createNewMicroprocessorAttachment(maa);
					}
					
					if(file3 != null)
					if(!file3.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file3.getBytes();
			            Path path = Paths.get(microprocessorAttachmentsUrl + file3.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
			            maa.setFileName(file3.getOriginalFilename());
			            maa.setMicroprocessorAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newMicroprocessorAdviceAttachmentId = microprocessorAdviceAttachmentService.createNewMicroprocessorAttachment(maa);
					}
					
					if(file4 != null)
					if(!file4.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file4.getBytes();
			            Path path = Paths.get(microprocessorAttachmentsUrl + file4.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
			            maa.setFileName(file4.getOriginalFilename());
			            maa.setMicroprocessorAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newMicroprocessorAdviceAttachmentId = microprocessorAdviceAttachmentService.createNewMicroprocessorAttachment(maa);
					}
					
					if(file5 != null)
					if(!file5.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file5.getBytes();
			            Path path = Paths.get(microprocessorAttachmentsUrl + file5.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
			            maa.setFileName(file5.getOriginalFilename());
			            maa.setMicroprocessorAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newMicroprocessorAdviceAttachmentId = microprocessorAdviceAttachmentService.createNewMicroprocessorAttachment(maa);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		List<MicroprocessorAdvice> maList = new ArrayList<MicroprocessorAdvice>();
		maList = myMicroprocessorNewNoteService.getAllNotes();
		model.addAttribute("microprocessorAdvices", maList);
		
		List<MicroprocessorAdviceAttachment> maaList = microprocessorAdviceAttachmentService.getAllMicroprocessorAttachments();
		model.addAttribute("microprocessorAdvicesAttachments", maaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = microprocessorAttachmentsUrl.substring(0, microprocessorAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = microprocessorAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "microprocessors";
		
	}
	
}

