package com.surveymoney.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebApplication implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        //Annotatin기반의 설정파일을 주입한다.
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        //설정파일은 등록한다.
        context.register(WebConfig.class);

        context.setServletContext(servletContext);
        //리스너를 등록한다.
        servletContext.addListener(new ContextLoaderListener(context));
        //context.refresh();

        //dispatcher 등록
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));

        //servlet mapping
        dispatcher.addMapping("/app/*");
        //filter 등록
        FilterRegistration.Dynamic filter
                = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        filter.setInitParameter("encoding", "UTF-8");
        filter.addMappingForServletNames(null, false, "dispatcher");

        
    }
}
