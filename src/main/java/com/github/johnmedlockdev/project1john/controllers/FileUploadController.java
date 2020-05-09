package com.github.johnmedlockdev.project1john.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class FileUploadController
{
	@RequestMapping(value = "/upload",
			method = RequestMethod.POST,
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

	public RedirectView fileUpload(@RequestParam("file") MultipartFile file) throws IOException
	{
		File convertFile = new File("C:\\Storage\\" + file.getOriginalFilename());

		try (FileOutputStream fout = new FileOutputStream(convertFile))
		{
			fout.write(file.getBytes());
		}
		catch (Exception exe)
		{
			exe.printStackTrace();
		}
		return new RedirectView("uploadForm");
	}

}
// // TODO: 5/9/2020 add a buffer to multipart
// // TODO: 5/9/2020 route user based off success or failure of upload.
