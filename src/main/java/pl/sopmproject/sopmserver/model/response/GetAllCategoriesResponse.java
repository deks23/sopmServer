package pl.sopmproject.sopmserver.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import pl.sopmproject.sopmserver.model.entity.Category;

import java.util.List;

@Getter
@Setter
public class GetAllCategoriesResponse extends Response{
    private List<Category> categoryList;

    @Builder(builderMethodName = "categoriesResponseBuilder")
    public GetAllCategoriesResponse(boolean status, String responseMessage, HttpStatus httpStatus, List<Category> categoryList) {
        super(status, responseMessage, httpStatus);
        this.categoryList = categoryList;
    }
}
