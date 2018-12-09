package representation;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class FileContent {
    private String content;

    public FileContent(){
    }

    public FileContent(java.io.File file) throws IOException{
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        this.content = new String(encoded);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] decode(){
        return Base64.decodeBase64(this.content);
    }
}
