package board.bbs.bbs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 요청 - 뷰 연결
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/loginResult").setViewName("/loginResult");

    }

    // 정적 리소스 핸들링 (이미지 서빙)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // '/images/**' 경로로 들어오는 요청은 'file:C:/Temp/productImageUpload/' 디렉토리에서 처리됨
        registry.addResourceHandler("/productImages/**")
                .addResourceLocations("file:C:/Temp/productImageUpload/");
    }
}
