package board.bbs.bbs.controller;


import board.bbs.bbs.dto.*;
import board.bbs.bbs.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/register")
    public String registerForm() {
        return "product/register";
    }

    @PostMapping("/register")
    public String register(ProductRegisterRequestDTO productRegisterRequestDTO,
                           @ModelAttribute ProductImageUploadDTO productImageUploadDTO,
                                  Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        productService.saveProduct(productRegisterRequestDTO, productImageUploadDTO, userDetails.getUsername());

        return "redirect:/product/productList";
    }

    @GetMapping("/productList")
    public String productList(Model model) {
        List<ProductResponseDTO> dto = productService.productList();
        model.addAttribute("productList", dto);

        // 아래 라인을 추가하여 로그로 확인
        System.out.println("Product List DTO: " + dto);


        return "product/productList";
    }




}

