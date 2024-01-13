package board.bbs.bbs.repository;

import board.bbs.bbs.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    }

