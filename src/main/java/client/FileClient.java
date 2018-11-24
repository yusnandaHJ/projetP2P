package client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import representation.File;
import representation.FileList;

import java.util.ArrayList;
import java.util.List;

public class FileClient {

    /*
    GET /files
     */
    public static void getFiles()
    {
        final String uri = "http://localhost:8080/files";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
    }

    /*
    POST /files
     */
    public static void updateFiles()
    {

        final String uri = "http://localhost:8080/files";

        List<File> files = new ArrayList<>();
        files.add(new File("0001","lol",12));
        files.add(new File("0002","jpp",1255555555));

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject( uri, files, File[].class);

        //System.out.println(result);
    }

    public static void getFile(String fileid)
    {
        final String uri = "http://localhost:8080/files/"+fileid;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
    }
}
