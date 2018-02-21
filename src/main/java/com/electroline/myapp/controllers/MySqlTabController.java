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

import com.electroline.myapp.domain.MySqlAdvice;
import com.electroline.myapp.domain.MySqlAdviceAttachment;
import com.electroline.myapp.service.MySqlAdviceAttachmentService;
import com.electroline.myapp.service.MySqlNoteService;

@Controller
@PropertySource("classpath:META-INF/spring/fileUpload.properties")
@RequestMapping(value = "/mysql")
public class MySqlTabController {
	
	@Value("${fileUpload.mysqlAttachmentsUrl}")
	protected String mySqlAttachmentsUrl;
	
	@Autowired
	@Qualifier(value = "mySqlNoteService")
	private MySqlNoteService mySqlNoteService;
	
	@Autowired
	@Qualifier("mySqlAdviceAttachmentService")
	MySqlAdviceAttachmentService mySqlAdviceAttachmentService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		List<MySqlAdvice> msaList = new ArrayList<MySqlAdvice>();
		msaList = mySqlNoteService.getAllNotes();
		model.addAttribute("sqlAdvices", msaList);
		
		List<MySqlAdviceAttachment> maaList = mySqlAdviceAttachmentService.getAllMySqlAttachments();
		model.addAttribute("sqlAdvicesAttachments", maaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = mySqlAttachmentsUrl.substring(0, mySqlAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = mySqlAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "mysql";
	}
	
	@RequestMapping(value = "/addnote", method = RequestMethod.GET)
	public String addNote(Model model) {
		MySqlAdvice msa = new MySqlAdvice();
		model.addAttribute("newnote", msa);
		model.addAttribute("noteType", "mysql");
		return "addnote";
		
	}
	
	@RequestMapping(value = "/addfillednote", method = RequestMethod.POST)
	public String addFilledNote(ModelMap model, @RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3,
			@RequestParam("file4") MultipartFile file4, @RequestParam("file5") MultipartFile file5,
			@RequestParam Map<String, String> newAdvice) {
		
		MySqlAdvice msa = new MySqlAdvice();
		msa.setAdviceName(newAdvice.get("adviceName"));
		msa.setAdviceDescription(newAdvice.get("adviceDescription"));
		
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
					int newNoteId = mySqlNoteService.createNewNote(msa);
					
					if(file != null)
					if(!file.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file.getBytes();
			            Path path = Paths.get(mySqlAttachmentsUrl + file.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
			            maa.setFileName(file.getOriginalFilename());
			            maa.setMySqlAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newMySqlAdviceAttachmentId = mySqlAdviceAttachmentService.createNewMySqlAttachment(maa);
					}
					
					if(file2 != null)
					if(!file2.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file2.getBytes();
			            Path path = Paths.get(mySqlAttachmentsUrl + file2.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
			            maa.setFileName(file2.getOriginalFilename());
			            maa.setMySqlAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newMySqlAdviceAttachmentId = mySqlAdviceAttachmentService.createNewMySqlAttachment(maa);
					}
					
					if(file3 != null)
					if(!file3.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file3.getBytes();
			            Path path = Paths.get(mySqlAttachmentsUrl + file3.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
			            maa.setFileName(file3.getOriginalFilename());
			            maa.setMySqlAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newMySqlAdviceAttachmentId = mySqlAdviceAttachmentService.createNewMySqlAttachment(maa);
					}
					
					if(file4 != null)
					if(!file4.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file4.getBytes();
			            Path path = Paths.get(mySqlAttachmentsUrl + file4.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
			            maa.setFileName(file4.getOriginalFilename());
			            maa.setMySqlAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newMySqlAdviceAttachmentId = mySqlAdviceAttachmentService.createNewMySqlAttachment(maa);
					}
					if(file5 != null)
					if(!file5.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file5.getBytes();
			            Path path = Paths.get(mySqlAttachmentsUrl + file5.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
			            maa.setFileName(file5.getOriginalFilename());
			            maa.setMySqlAdviceId(newNoteId);
			            @SuppressWarnings("unused")
						int newMySqlAdviceAttachmentId = mySqlAdviceAttachmentService.createNewMySqlAttachment(maa);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		List<MySqlAdvice> msaList = new ArrayList<MySqlAdvice>();
		msaList = mySqlNoteService.getAllNotes();
		model.addAttribute("sqlAdvices", msaList);
		
		List<MySqlAdviceAttachment> maaList = mySqlAdviceAttachmentService.getAllMySqlAttachments();
		model.addAttribute("sqlAdvicesAttachments", maaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = mySqlAttachmentsUrl.substring(0, mySqlAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = mySqlAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		model.addAttribute("relativeUrlToFiles", relativeUrlToFiles);
		
		return "mysql";
		
	}
	
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		List<MySqlAdviceAttachment> maaListForDelete = mySqlAdviceAttachmentService.getAllMySqlAttachmentsForMySqlNoteId(adviceId);
		
		mySqlAdviceAttachmentService.deleteAllMySqlAttachmentsForMySqlNoteId(adviceId);		
		mySqlNoteService.deleteNote(adviceId);
		
		for(MySqlAdviceAttachment maa : maaListForDelete) {
			Path path = Paths.get(mySqlAttachmentsUrl + maa.getFileName());
			if(Files.exists(path))
				try {
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		List<MySqlAdvice> msaList = new ArrayList<MySqlAdvice>();
		msaList = mySqlNoteService.getAllNotes();
		model.addAttribute("sqlAdvices", msaList);
		
		List<MySqlAdviceAttachment> maaList = mySqlAdviceAttachmentService.getAllMySqlAttachments();
		model.addAttribute("sqlAdvicesAttachments", maaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = mySqlAttachmentsUrl.substring(0, mySqlAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = mySqlAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		
		return "mysql";
	}
	
	@RequestMapping(value = "/editNote")
	public String updateNote(ModelMap model, @RequestParam("adviceId") int adviceId) {
		MySqlAdvice msa = new MySqlAdvice();
		msa = mySqlNoteService.getNote(adviceId);
		model.addAttribute("advice", msa);
		model.addAttribute("noteType", "mysql");
		
		List<MySqlAdviceAttachment> maaList = mySqlAdviceAttachmentService.getAllMySqlAttachmentsForMySqlNoteId(adviceId);
		model.addAttribute("adviceAttachmentsList", maaList);
		model.addAttribute("adviceAttachmentsValue", maaList.size());
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = mySqlAttachmentsUrl.substring(0, mySqlAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = mySqlAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		
		return "editnote";
	}
	
	@RequestMapping(value = "/updateFilledNote")
	public String updateFilledNote(ModelMap model, @RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2, @RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4, @RequestParam(value = "file5", required = false) MultipartFile file5,
			@RequestParam Map<String, String> advice) {
		
		List<MySqlAdviceAttachment> maaListForThisAdvice = mySqlAdviceAttachmentService.getAllMySqlAttachmentsForMySqlNoteId(Integer.parseInt(advice.get("id")));
		List<Integer> attachmentsListToDelete = new ArrayList<>();
		for(MySqlAdviceAttachment maa : maaListForThisAdvice) {
			if("on".equals(advice.get("checkbox_" + maa.getId()))) attachmentsListToDelete.add(maa.getId());
		}
		
		List<MySqlAdviceAttachment> maaListForNote = mySqlAdviceAttachmentService.getAllMySqlAttachmentsForMySqlNoteId(Integer.parseInt(advice.get("id")));
		List<MySqlAdviceAttachment> maaListToDelete = new ArrayList<>();
		
		for(MySqlAdviceAttachment maa : maaListForNote) {
			for(int idToDelete : attachmentsListToDelete) {
				if(maa.getId() == idToDelete) maaListToDelete.add(maa);
			}
		}
		
		for(MySqlAdviceAttachment maa : maaListToDelete) {
			Path path = Paths.get(mySqlAttachmentsUrl + maa.getFileName());
			if(Files.exists(path))
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		attachmentsListToDelete.forEach(x->mySqlAdviceAttachmentService.deleteAttachment(x));
		
		MySqlAdvice msa = new MySqlAdvice();
		msa.setId(Integer.parseInt(advice.get("id")));
		msa.setAdviceName(advice.get("adviceName"));
		msa.setAdviceDescription(advice.get("adviceDescription"));
		mySqlNoteService.updateNote(msa);
		
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
			            Path path = Paths.get(mySqlAttachmentsUrl + file1.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
			            maa.setFileName(file1.getOriginalFilename());
			            maa.setMySqlAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newMySqlAdviceAttachmentId = mySqlAdviceAttachmentService.createNewMySqlAttachment(maa);
					}
					
					if(file2 != null)
					if(!file2.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file2.getBytes();
			            Path path = Paths.get(mySqlAttachmentsUrl + file2.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
			            maa.setFileName(file2.getOriginalFilename());
			            maa.setMySqlAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newMySqlAdviceAttachmentId = mySqlAdviceAttachmentService.createNewMySqlAttachment(maa);
					}
					
					if(file3 != null)
					if(!file3.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file3.getBytes();
			            Path path = Paths.get(mySqlAttachmentsUrl + file3.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
			            maa.setFileName(file3.getOriginalFilename());
			            maa.setMySqlAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newMySqlAdviceAttachmentId = mySqlAdviceAttachmentService.createNewMySqlAttachment(maa);
					}
					
					if(file4 != null)
					if(!file4.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file4.getBytes();
			            Path path = Paths.get(mySqlAttachmentsUrl + file4.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
			            maa.setFileName(file4.getOriginalFilename());
			            maa.setMySqlAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newMySqlAdviceAttachmentId = mySqlAdviceAttachmentService.createNewMySqlAttachment(maa);
					}
					
					if(file5 != null)
					if(!file5.isEmpty()) {
			            // Get the file and save it somewhere
			            byte[] bytes = file5.getBytes();
			            Path path = Paths.get(mySqlAttachmentsUrl + file5.getOriginalFilename());
			            Files.write(path, bytes);
			            
			            MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
			            maa.setFileName(file5.getOriginalFilename());
			            maa.setMySqlAdviceId(Integer.parseInt(advice.get("id")));
			            @SuppressWarnings("unused")
						int newMySqlAdviceAttachmentId = mySqlAdviceAttachmentService.createNewMySqlAttachment(maa);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		List<MySqlAdvice> msaList = new ArrayList<MySqlAdvice>();
		msaList = mySqlNoteService.getAllNotes();
		model.addAttribute("sqlAdvices", msaList);
		
		List<MySqlAdviceAttachment> maaList = mySqlAdviceAttachmentService.getAllMySqlAttachments();
		model.addAttribute("sqlAdvicesAttachments", maaList);
		
		/* Lokalizujemy adres wzgledny do zalacznikow */
		String relativeUrlToFiles = new String();
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = mySqlAttachmentsUrl.substring(0, mySqlAttachmentsUrl.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
		/* Pozbywamy sie ostatniego znaku '\' */
		relativeUrlToFiles = relativeUrlToFiles.substring(0, relativeUrlToFiles.lastIndexOf("/"));
//		System.out.println(relativeUrlToFiles);
//		System.out.println(windowsAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("\\")));
		relativeUrlToFiles = mySqlAttachmentsUrl.substring(relativeUrlToFiles.lastIndexOf("/"));
		
		return "mysql";
		
	}
	
}

