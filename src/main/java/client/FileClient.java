package client;

import org.springframework.web.client.RestTemplate;
import representation.File;

import java.util.ArrayList;
import java.util.List;

public class FileClient {

    private static final String uri = "http://localhost:8080/files";
    //private static final String uri = "http://192.168.0.4:7878/files";
    /*
    GET /files
     */
    public static void getFiles()
    {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
    }

    /*
    POST /files
     */
    public static void updateFiles()
    {
        List<File> files = new ArrayList<>();
        files.add(new File("lol",12));
        files.add(new File("jpp",1255555555));

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject( uri, files, File[].class);

        //System.out.println(result);
    }

    public static void getFile(String fileid)
    {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri+"/"+fileid, String.class);

        System.out.println(result);
    }


}
