package board.bbs.bbs.repository;

import board.bbs.bbs.domain.Product;
import board.bbs.bbs.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    }

