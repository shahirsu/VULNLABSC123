package com.vulnsc.social.Comment;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Map;
import java.util.function.Function;


@RestController
public class Comments {
    private static final String PARAMETER_NAME = "comment";
    //private static final Pattern IMG_INPUT_TAG_PATTERN = Pattern.compile("(<img)|(<input)+");
    //private static final Pattern IMG_INPUT_TAG_CASE_INSENSITIVE_PATTERN =
    // Pattern.compile("((?i)<img)|((?i)<script)+");

    private final PostRepository postRepository;

    public Comments(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private void savePost(String content) {
        Post post = new Post();
        post.setContent(content);
        this.postRepository.save(post);
    }

    private String getCommentsPayload(Function<Post, String> function) {
        StringBuilder posts = new StringBuilder();
        this.postRepository.findAll().forEach(post -> {
            posts.append("<div id=\"comments\">")
                    .append(function.apply(post))
                    .append("</div>");
        });
        return posts.toString();
    }

    @PostMapping("/comments")
    public ResponseEntity<String> getVulnerablePayload(@RequestParam Map<String, String> queryParams) {
        if (queryParams.containsKey(PARAMETER_NAME)) {
            String content = queryParams.get(PARAMETER_NAME);
            String encodedInput = Encoder.encodeHTML(content);
            savePost(encodedInput);
        }
        String payload = getCommentsPayload(Post::getContent);
        return ResponseEntity.ok(payload);
    }

    @GetMapping("/comments")
    public ModelAndView storedPage() {
        return new ModelAndView("stored");
    }

    @GetMapping("/links")
    @ResponseBody
    public String getCommentsPage() {
        String payload = getLinksPayload(Post::getContent);
        // Call the getCommentsPayload method to get the HTML content
        String commentsPayload = getCommentsPayload(post -> {
            // Replace this lambda function with your actual implementation
            // to generate the comments HTML for each post
            return "<p>" + payload + "</p>";
        });

        // Return the HTML as the response
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>Comments Page</title>"
                + "</head>"
                + "<body>"
                + payload
                + "</body>"
                + "</html>";
    }

    private String getLinksPayload(Function<Post, String> function) {
        StringBuilder posts = new StringBuilder();
        this.postRepository.findAll().forEach(post -> {
            posts.append("<a href=\"").append(function.apply(post)).append("\">")
                    .append(function.apply(post)).append("</a><br>");
        });
        return posts.toString();

    }
}


