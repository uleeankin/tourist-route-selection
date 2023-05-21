package com.uleeankin.touristrouteselection.utils.file;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;

public class FileService {
    public static String UPLOAD_FOLDER = "/resources/images";

    public static byte[] convertImageToBytes(MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadDirectory = request.getServletContext().getRealPath(UPLOAD_FOLDER);
        String fileName = file.getOriginalFilename();
        String filePath = Paths.get(uploadDirectory, fileName).toString();

        File dir = new File(uploadDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
        stream.write(file.getBytes());
        stream.close();

        return file.getBytes();

    }
}
