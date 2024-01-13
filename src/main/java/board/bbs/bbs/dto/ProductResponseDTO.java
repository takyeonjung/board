package board.bbs.bbs.dto;

import board.bbs.bbs.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import board.bbs.bbs.domain.ProductImage;

@Getter
@NoArgsConstructor
public class ProductResponseDTO {

    private LocalDateTime createdDate;
    private Long id;
    private String productName;
    private String price;
    private List<String> imageUrls;
    @Builder
    public ProductResponseDTO(Product product) {
        this.createdDate = product.getCreatedDate();
        this.id = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.imageUrls = product.getProductImages().stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList());
    }
}
