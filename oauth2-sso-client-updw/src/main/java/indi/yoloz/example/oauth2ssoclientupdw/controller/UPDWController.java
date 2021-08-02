package indi.yoloz.example.oauth2ssoclientupdw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yoloz
 */

@Controller
public class UPDWController {

    private Map<String, String> exists = new ConcurrentHashMap<>();

    @Value("${yoloz.uploadPath}")
    private String uploadPath;

    @RequestMapping("/")
    public String index(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", principal);
        model.addAttribute("fileList", exists.keySet());
        return "index";
    }

    @RequestMapping("/info")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }

    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", principal);
        String fileName = Optional.ofNullable(file.getOriginalFilename()).orElse(file.getName());
        if (exists.containsKey(fileName)) fileName = fileName + "_1";
        String path = Paths.get(uploadPath, fileName).toString();
        try (FileOutputStream out = new FileOutputStream(path)) {
            out.write(file.getBytes());
            out.flush();
            exists.put(fileName, path);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("fileList", exists.keySet());
        return "index";
    }

    @RequestMapping("/upload/api")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        String fileName = Optional.ofNullable(file.getOriginalFilename()).orElse(file.getName());
        if (exists.containsKey(fileName)) fileName = fileName + "_1";
        String path = Paths.get(uploadPath, fileName).toString();
        try (FileOutputStream out = new FileOutputStream(path)) {
            out.write(file.getBytes());
            out.flush();
            exists.put(fileName, path);
        } catch (IOException e) {
           return e.getMessage();
        }
        return fileName;
    }

    @RequestMapping("/download")
    public String downLoad(@RequestParam("filename") String fileName, HttpServletResponse response) throws IOException {
        File file = Paths.get(uploadPath, fileName).toFile();
        if (file.exists()) {
            //设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

            try (FileInputStream inputStream = new FileInputStream(file);
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int i = inputStream.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer);
                    i = inputStream.read(buffer);
                }
            }
        }
        return null;
    }

    @RequestMapping("/delete/api")
    @ResponseBody
    public String delete(@RequestParam("filename") String fileName) throws IOException {
        if (Files.deleteIfExists(Paths.get(uploadPath, fileName))) {
            exists.remove(fileName);
        }
        return "delete " + fileName;
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("filename") String fileName, Model model) throws IOException {
        if (Files.deleteIfExists(Paths.get(uploadPath, fileName))) {
            exists.remove(fileName);
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", principal);
        model.addAttribute("fileList", exists.keySet());
        return "index";
    }

}
