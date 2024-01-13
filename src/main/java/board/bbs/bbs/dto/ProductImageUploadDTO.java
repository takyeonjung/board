package board.bbs.bbs.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductImageUploadDTO {

    private List<MultipartFile> files;
}
