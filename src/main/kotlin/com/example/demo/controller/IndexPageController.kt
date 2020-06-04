package com.example.demo.controller

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.core.io.ResourceLoader
import org.springframework.http.CacheControl
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.DigestUtils
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset
import java.time.Duration
import javax.servlet.http.HttpServletResponse

@RestController
class IndexPageController(private val resourceLoader: ResourceLoader) : ErrorController {
  private val response by lazy {
    val resource = resourceLoader.getResource(FILE)
    val body = resource.inputStream.use {
      StreamUtils.copyToString(it, Charset.defaultCharset())
    }
    ResponseEntity
      .ok()
      .contentType(MediaType.TEXT_HTML)
      .lastModified(resource.lastModified())
      .eTag(DigestUtils.md5DigestAsHex(body.toByteArray()))
      .body(body)
  }

  /**
   * Workaround for serving index.html file for any route that does not exist
   * in the system, probably because it's managed by the client code.
   * The client code will also be responsible for rendering error pages.
   */
  @RequestMapping(PATH, "/")
  fun error(response: HttpServletResponse): ResponseEntity<String> {
    // little hack for Cache-Control header, since Spring will add a no-cache
    // to the response if it's an error. Using ResponseEntity will end up having
    // another Cache-Control header, so we'll have to set it via HttpServletResponse
    response.setHeader(
      "Cache-Control",
      CacheControl.maxAge(Duration.ofSeconds(0)).headerValue
    )

    return this.response
  }

  override fun getErrorPath(): String {
    return PATH
  }

  companion object {
    private const val PATH = "/error"
    private const val FILE = "classpath:static/index.html"
  }
}
