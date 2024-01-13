package board.bbs.bbs.domain;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code") // 외래키를 설정
    private User user;


    public void setUser(User user) {
        this.user = user;
//        user.getProductList().add(this);
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<ProductImage> productImages;



}