package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.Duration;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexPageController implements ErrorController {
  private static final String PATH = "/error";

  /**
   * Workaround for serving index.html file for any route that does not exist
   * in the system, probably because it's managed by the client code.
   * The client code will also be responsible for rendering error pages.
   */
  @RequestMapping({ PATH, "/" })
  public ResponseEntity<String> error(HttpServletResponse response)
      throws FileNotFoundException, IOException {
    File file = ResourceUtils.getFile("classpath:static/index.html");
    MediaType contentType = MediaType.TEXT_HTML;
    InputStream inputStream = new FileInputStream(file);
    String body = StreamUtils.copyToString(
        inputStream,
        Charset.defaultCharset()
    );
    // little hack for Cache-Control header, since Spring will add a no-cache
    // to the response if it's an error. Using ResponseEntity will end up having
    // another Cache-Control header, so we'll have to set it via HttpServletResponse
    response.setHeader(
        "Cache-Control",
        CacheControl.maxAge(Duration.ofSeconds(0)).getHeaderValue()
    );
    return ResponseEntity
        .ok()
        .contentType(contentType)
        .lastModified(file.lastModified())
        .eTag(DigestUtils.md5DigestAsHex(inputStream))
        .body(body);
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}
