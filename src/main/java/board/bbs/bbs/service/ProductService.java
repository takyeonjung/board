package board.bbs.bbs.service;


import board.bbs.bbs.domain.Product;
import board.bbs.bbs.domain.ProductImage;
import board.bbs.bbs.domain.User;
import board.bbs.bbs.dto.ProductImageUploadDTO;
import board.bbs.bbs.dto.ProductRegisterRequestDTO;
import board.bbs.bbs.dto.ProductResponseDTO;
import board.bbs.bbs.repository.ProductImageRepository;
import board.bbs.bbs.repository.ProductRepository;
import board.bbs.bbs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Value("${file.productImagePath}")
    private String uploadFolder;

    @Transactional
    public Long saveProduct(ProductRegisterRequestDTO productRegisterRequestDTO, ProductImageUploadDTO productImageUploadDTO, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));

        Product result = Product.builder()
                .productName(productRegisterRequestDTO.getProductName())
                .price(productRegisterRequestDTO.getPrice())
                .user(user)
                .build();
        productRepository.save(result);

        if (productImageUploadDTO.getFiles() != null && !productImageUploadDTO.getFiles().isEmpty()) {
            for (MultipartFile file : productImageUploadDTO.getFiles()) {
                UUID uuid = UUID.randomUUID();
                String imageFileName = uuid + "_" + file.getOriginalFilename();

                File destinationFile = new File(uploadFolder + imageFileName);

                try {
                    file.transferTo(destinationFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ProductImage image = ProductImage.builder()
                        .url("/productImages/" + imageFileName)
                        .product(result)
                        .build();

                productImageRepository.save(image);
            }
        }

        return result.getId();
    }


    public List<ProductResponseDTO> productList() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            ProductResponseDTO result = ProductResponseDTO.builder()
                    .product(product)
                    .build();
            productDTOs.add(result);
        }

        return productDTOs;
    }

}