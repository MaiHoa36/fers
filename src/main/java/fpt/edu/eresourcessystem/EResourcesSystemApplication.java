package fpt.edu.eresourcessystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class EResourcesSystemApplication
//        implements ServletContextInitializer, WebMvcConfigurer
{

    public static void main(String[] args) {
        SpringApplication.run(EResourcesSystemApplication.class, args);
    }

//    @Override
//    public void onStartup(ServletContext servletContext) {
//        // Register the CKFinder's servlet.
//        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("ckfinder", new CKFinderServlet());
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/ckfinder/*");
//        dispatcher.setInitParameter("scan-path", "fpt.edu.eresourcessystem.config.ckfinder");
//
//        FilterRegistration.Dynamic filter = servletContext.addFilter("x-content-options", new Filter() {
//            @Override
//            public void init(FilterConfig filterConfig) {
//            }
//
//            @Override
//            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//                ((HttpServletResponse) response).setHeader("X-Content-Type-Options", "nosniff");
//                chain.doFilter(request, response);
//            }
//
//            @Override
//            public void destroy() {
//            }
//        });
//
//        filter.addMappingForUrlPatterns(null, false, "/userfiles/*");
//
//        String tempDirectory;
//
//        try {
//            tempDirectory = Files.createTempDirectory("ckfinder").toString();
//        } catch (IOException e) {
//            tempDirectory = null;
//        }
//
//        dispatcher.setMultipartConfig(new MultipartConfigElement(tempDirectory));
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // Configure the resource handler to serve files uploaded with CKFinder.
//        String publicFilesDir = String.format("file:%s/userfiles/", System.getProperty("user.dir"));
//
//        registry.addResourceHandler("/userfiles/**")
//                .addResourceLocations(publicFilesDir);
//    }
}
