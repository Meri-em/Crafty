package com.crafty.web.rest;

import com.crafty.dto.UploadItemDTO;
import com.crafty.service.AuthorService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class AuthorResource {

	private final AuthorService authorService;

	public AuthorResource(AuthorService authorService) {
		this.authorService = authorService;
	}

	@PostMapping("/upload1") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file,
								   RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}

		try {
			File file1 = new File("src/main/resources/static/xx/"+ file.getOriginalFilename());
			file1.getParentFile().mkdirs();

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get("src/main/resources/static/xx/" + file.getOriginalFilename());

			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/uploadStatus";
	}

	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public String adminAddProductSubmit(final @ModelAttribute(value = "product") @Valid UploadItemDTO item,
										BindingResult bindingResult, final @RequestParam(value = "file") MultipartFile[] files){
		return authorService.addNewItem("387927a1-f131-4a45-a7b7-b50969ff3cba",
			item, files);
	}

}