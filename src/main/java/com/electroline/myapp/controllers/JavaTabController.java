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

import com.electroline.myapp.domain.JavaAdvice;
import com.electroline.myapp.domain.JavaAdviceAttachment;
import com.electroline.myapp.service.JavaAdviceAttachmentService;
import com.electroline.myapp.service.JavaNoteService;

@Controller
@PropertySource("classpath:META-INF/spring/fileUpload.properties")
@RequestMapping(value = "java")
public class JavaTabController {
	
	@Value("${fileUpload.javaAttachmentsUrl}")
	protected String javaAttachmentsUrl;
	
	@Autowired
	@Qualifier(value = "javaNoteService")
	JavaNoteService myJavaNoteService;
	
	@Autowired
	@Qualifier(value = "javaAdviceAttachmentService")
	JavaAdviceAttachmentService javaAdviceAttachmentService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		List<JavaAdvice> jaList = new ArrayList<JavaAdvice>();
		jaList = myJavaNoteService.getAllNotes();
		model.addAttribute("javaAdvices", jaList);
		
		List<JavaAdviceAttachment> jaaList = new ArrayList<>();
		jaaList = javaAdviceAttachmentService.getAllJavaAttachments();
		model.addAttribute("javaAdvicesAttachments", jaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = javaAttachmentsUrl.substring(0, javaAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = javaAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "java";
	}
	
	@RequestMapping(value = "/addnote", method = RequestMethod.GET)
	public String addNote(Model model) {
		JavaAdvice ja = new JavaAdvice();
		model.addAttribute("newnote", ja);
		model.addAttribute("noteType", "java");
		return "addnote";
		
	}
	
	@RequestMapping(value = "/addfillednote", method = RequestMethod.POST)
	public String addFilledNote(ModelMap model, @RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3,
			@RequestParam("file4") MultipartFile file4, @RequestParam("file5") MultipartFile file5,
			@RequestParam Map<String, String> newAdvice) {
		
		JavaAdvice ja = new JavaAdvice();
		ja.setAdviceName(newAdvice.get("adviceName"));
		ja.setAdviceDescription(newAdvice.get("adviceDescription"));
		
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
			int newNoteId = myJavaNoteService.createNewNote(ja);
			
			if(file != null)
			if(!file.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(javaAttachmentsUrl + file.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            JavaAdviceAttachment jaa = new JavaAdviceAttachment();
	            jaa.setFileName(file.getOriginalFilename());
	            jaa.setJavaAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newJavaAdviceAttachmentId = javaAdviceAttachmentService.createNewJavaAttachment(jaa);
			}
			
			if(file2 != null)
			if(!file2.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file2.getBytes();
	            Path path = Paths.get(javaAttachmentsUrl + file2.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            JavaAdviceAttachment jaa = new JavaAdviceAttachment();
	            jaa.setFileName(file2.getOriginalFilename());
	            jaa.setJavaAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newJavaAdviceAttachmentId = javaAdviceAttachmentService.createNewJavaAttachment(jaa);
			}
			
			if(file3 != null)
			if(!file3.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file3.getBytes();
	            Path path = Paths.get(javaAttachmentsUrl + file3.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            JavaAdviceAttachment jaa = new JavaAdviceAttachment();
	            jaa.setFileName(file3.getOriginalFilename());
	            jaa.setJavaAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newJavaAdviceAttachmentId = javaAdviceAttachmentService.createNewJavaAttachment(jaa);
			}
			
			if(file4 != null)
			if(!file4.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file4.getBytes();
	            Path path = Paths.get(javaAttachmentsUrl + file4.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            JavaAdviceAttachment jaa = new JavaAdviceAttachment();
	            jaa.setFileName(file4.getOriginalFilename());
	            jaa.setJavaAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newJavaAdviceAttachmentId = javaAdviceAttachmentService.createNewJavaAttachment(jaa);
			}
			if(file5 != null)
			if(!file5.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file5.getBytes();
	            Path path = Paths.get(javaAttachmentsUrl + file5.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            JavaAdviceAttachment jaa = new JavaAdviceAttachment();
	            jaa.setFileName(file5.getOriginalFilename());
	            jaa.setJavaAdviceId(newNoteId);
	            @SuppressWarnings("unused")
				int newJavaAdviceAttachmentId = javaAdviceAttachmentService.createNewJavaAttachment(jaa);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<JavaAdvice> jaList = new ArrayList<JavaAdvice>();
		jaList = myJavaNoteService.getAllNotes();
		model.addAttribute("javaAdvices", jaList);
		
		List<JavaAdviceAttachment> jaaList = new ArrayList<>();
		jaaList = javaAdviceAttachmentService.getAllJavaAttachments();
		model.addAttribute("javaAdvicesAttachments", jaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = javaAttachmentsUrl.substring(0, javaAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = javaAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "java";
		
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		List<JavaAdviceAttachment> jaaListForDelete = javaAdviceAttachmentService.getAllJavaAttachmentsForJavaNoteId(adviceId);
		
		javaAdviceAttachmentService.deleteAllJavaAttachmentsForJavaNoteId(adviceId);
		myJavaNoteService.deleteNote(adviceId);
		
		for(JavaAdviceAttachment jaa : jaaListForDelete) {
			Path path = Paths.get(javaAttachmentsUrl + jaa.getFileName());
			if(Files.exists(path))
				try {
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		List<JavaAdvice> jaList = new ArrayList<JavaAdvice>();
		jaList = myJavaNoteService.getAllNotes();
		model.addAttribute("javaAdvices", jaList);
		
		List<JavaAdviceAttachment> jaaList = new ArrayList<>();
		jaaList = javaAdviceAttachmentService.getAllJavaAttachments();
		model.addAttribute("javaAdvicesAttachments", jaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = javaAttachmentsUrl.substring(0, javaAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = javaAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "java";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		JavaAdvice ja = new JavaAdvice();
		ja = myJavaNoteService.getNote(adviceId);
		model.addAttribute("advice", ja);
		model.addAttribute("noteType", "java");
		
		List<JavaAdviceAttachment> jaaList = javaAdviceAttachmentService.getAllJavaAttachmentsForJavaNoteId(adviceId);
		model.addAttribute("adviceAttachmentsList", jaaList);
		model.addAttribute("adviceAttachmentsValue", jaaList.size());
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = javaAttachmentsUrl.substring(0, javaAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = javaAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2, @RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4, @RequestParam(value = "file5", required = false) MultipartFile file5,
			@RequestParam Map<String, String> advice) {
		
		List<JavaAdviceAttachment> jaaListForThisAdvice = javaAdviceAttachmentService.getAllJavaAttachmentsForJavaNoteId(Integer.parseInt(advice.get("id")));
		List<Integer> attachmentsListToDelete = new ArrayList<>();
		for(JavaAdviceAttachment jaa : jaaListForThisAdvice) {
			if("on".equals(advice.get("checkbox_" + jaa.getId()))) attachmentsListToDelete.add(jaa.getId());
		}
		
		List<JavaAdviceAttachment> jaaListForNote = javaAdviceAttachmentService.getAllJavaAttachmentsForJavaNoteId(Integer.parseInt(advice.get("id")));
		List<JavaAdviceAttachment> jaaListToDelete = new ArrayList<>();
		
		for(JavaAdviceAttachment jaa : jaaListForNote) {
			for(int idToDelete : attachmentsListToDelete) {
				if(jaa.getId() == idToDelete) jaaListToDelete.add(jaa);
			}
		}
		
		for(JavaAdviceAttachment jaa : jaaListToDelete) {
			Path path = Paths.get(javaAttachmentsUrl + jaa.getFileName());
			if(Files.exists(path))
				try {
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		attachmentsListToDelete.forEach(x->javaAdviceAttachmentService.deleteAttachment(x));
		
		JavaAdvice ja = new JavaAdvice();
		ja.setId(Integer.parseInt(advice.get("id")));
		ja.setAdviceName(advice.get("adviceName"));
		ja.setAdviceDescription(advice.get("adviceDescription"));
		myJavaNoteService.updateNote(ja);
		
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
	            Path path = Paths.get(javaAttachmentsUrl + file1.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            JavaAdviceAttachment jaa = new JavaAdviceAttachment();
	            jaa.setFileName(file1.getOriginalFilename());
	            jaa.setJavaAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newJavaAdviceAttachmentId = javaAdviceAttachmentService.createNewJavaAttachment(jaa);
			}
			
			if(file2 != null)
			if(!file2.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file2.getBytes();
	            Path path = Paths.get(javaAttachmentsUrl + file2.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            JavaAdviceAttachment jaa = new JavaAdviceAttachment();
	            jaa.setFileName(file2.getOriginalFilename());
	            jaa.setJavaAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newJavaAdviceAttachmentId = javaAdviceAttachmentService.createNewJavaAttachment(jaa);
			}
			
			if(file3 != null)
			if(!file3.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file3.getBytes();
	            Path path = Paths.get(javaAttachmentsUrl + file3.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            JavaAdviceAttachment jaa = new JavaAdviceAttachment();
	            jaa.setFileName(file3.getOriginalFilename());
	            jaa.setJavaAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newJavaAdviceAttachmentId = javaAdviceAttachmentService.createNewJavaAttachment(jaa);
			}
			
			if(file4 != null)
			if(!file4.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file4.getBytes();
	            Path path = Paths.get(javaAttachmentsUrl + file4.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            JavaAdviceAttachment jaa = new JavaAdviceAttachment();
	            jaa.setFileName(file4.getOriginalFilename());
	            jaa.setJavaAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newJavaAdviceAttachmentId = javaAdviceAttachmentService.createNewJavaAttachment(jaa);
			}
			
			if(file5 != null)
			if(!file5.isEmpty()) {
	            // Get the file and save it somewhere
	            byte[] bytes = file5.getBytes();
	            Path path = Paths.get(javaAttachmentsUrl + file5.getOriginalFilename());
	            Files.write(path, bytes);
	            
	            JavaAdviceAttachment jaa = new JavaAdviceAttachment();
	            jaa.setFileName(file5.getOriginalFilename());
	            jaa.setJavaAdviceId(Integer.parseInt(advice.get("id")));
	            @SuppressWarnings("unused")
				int newJavaAdviceAttachmentId = javaAdviceAttachmentService.createNewJavaAttachment(jaa);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<JavaAdvice> jaList = new ArrayList<JavaAdvice>();
		jaList = myJavaNoteService.getAllNotes();
		model.addAttribute("javaAdvices", jaList);
		
		List<JavaAdviceAttachment> jaaList = new ArrayList<>();
		jaaList = javaAdviceAttachmentService.getAllJavaAttachments();
		model.addAttribute("javaAdvicesAttachments", jaaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = javaAttachmentsUrl.substring(0, javaAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = javaAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "java";
		
	}

}
