package com.blair.blairspring.controllers;

import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.model.userschema.User;
import com.blair.blairspring.util.ITestBean;
import com.blair.blairspring.util.LazyBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerErrorException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("test")
@Slf4j
public class TestController {

    private final LazyBean lazyBean;

    private final ITestBean testBean;
    private final List<ITestBean> testBeans;

    public TestController(@Lazy LazyBean lazyBean,
                          ITestBean testBean,
                          List<ITestBean> testBeans) {
        this.lazyBean = lazyBean;
        this.testBean = testBean;
        this.testBeans = testBeans;
    }

    private int attempts = 0;

    @GetMapping("home")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> home() {
        attempts++;
        if (attempts < 5) throw new RuntimeException();

        return ResponseEntity.ok().body("home test");
    }

    @GetMapping(value = "test-principal", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<User> testPrincipal(@AuthenticationPrincipal User user) {
        log.info("User: {}", user);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("role-tester")
    @RolesAllowed({"ROLE_TESTER", "ROLE_ADMIN"})
    @ResponseBody
    public ResponseEntity<String> testRolesAllowed(@RequestParam(defaultValue = "los perros") String something,
                                                   @Valid @RequestBody Job job,
                                                   BindingResult bindingResult,
                                                   /*@CookieValue("JSESSIONID") String jSessionId,*/
                                                   Locale locale,
                                                   HttpEntity<Job> httpEntity) {
        // throw new TestException("Hooop");
        return ResponseEntity.ok().body("Role has been allowed");
    }

    @GetMapping(value = "path-variable/{var1}/{var2}/{var3}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> pathVariable(@PathVariable Map<String, String> pathVariables) {
        log.info("PathVariables: {}", pathVariables);
        return ResponseEntity.ok().body(pathVariables.toString());
    }

    @GetMapping("void")
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ResponseBody
    public void voidReturn() {

    }

    @GetMapping(value = "getLazyBean", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<LazyBean> getLazyBean() {
        log.info("lazyBean: {}", lazyBean);
        return ResponseEntity.ok().body(lazyBean);
    }

    @GetMapping("test-exception")
    private @ResponseBody
    ResponseEntity<LazyBean> testException(HttpServletRequest request) {
        log.info("request: {}", request);
        throw new ServerErrorException("internal error", new Object() {
        }.getClass().getEnclosingMethod(), new NullPointerException("null"));
        // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "THE reason");
    }

    @GetMapping("testBeanTest")
    public @ResponseBody
    ResponseEntity<List<ITestBean>> getTestBean() {
        log.info("testBean: {}", testBean);
        testBeans.stream().forEach(testBean -> log.info("TestBean class: {}", testBean.getClass()));
        return ResponseEntity.ok().body(testBeans);
    }
}
