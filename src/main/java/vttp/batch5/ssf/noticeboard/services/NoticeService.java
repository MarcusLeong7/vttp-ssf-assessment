package vttp.batch5.ssf.noticeboard.services;

import jakarta.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

import java.io.StringReader;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepo;

    public static final String PUBLISH_URL = "https://publishing-production-d35a.up.railway.app/notice";

    // TODO: Task 3
    // You can change the signature of this method by adding any number of parameters
    // and return any type

    // Post/notice
    // Content-Type:application/json
    // Accept: application/json
    public String postJsonPayload(Notice notice) {

        // Create Json payload
        // convert categories list into Json Array
        JsonArrayBuilder categories = Json.createArrayBuilder();
        for (String category : notice.getCategories()) {
            categories.add(category);
        }

        JsonObject obj = Json.createObjectBuilder()
                .add("title", notice.getTitle())
                .add("poster", notice.getPoster())
                .add("postDate", notice.getPostDate().getTime())
                .add("categories", categories.build())
                .add("text", notice.getText())
                .build();

        //Create a request
        RequestEntity<String> req = RequestEntity
                .post(PUBLISH_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(obj.toString());

        RestTemplate template = new RestTemplate();


        ResponseEntity<String> resp = template.exchange(req, String.class);
        String payload = resp.getBody();
        if (resp.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            // Read Json payload
            JsonReader jsonReader = Json.createReader(new StringReader(payload));
            JsonObject jsonObj = jsonReader.readObject();
            String id = jsonObj.getString("id");
            return id;
        }

        // Read Json payload for error message
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = reader.readObject();
        String errormessage = jsonObj.getString("message");
        throw new RuntimeException(errormessage);

    }

   /* public void saveNotice(Notice notice) {
        noticeRepo.insertNotices(notice);
    }*/
}
