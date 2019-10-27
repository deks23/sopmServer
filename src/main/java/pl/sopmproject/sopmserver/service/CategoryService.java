package pl.sopmproject.sopmserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.sopmproject.sopmserver.model.entity.Category;
import pl.sopmproject.sopmserver.model.response.GetAllCategoriesResponse;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Response getCategories(){
        List<Category> categoryList = categoryRepository.findAll();
        if(categoryList.isEmpty()){
            return GetAllCategoriesResponse.categoriesResponseBuilder().httpStatus(HttpStatus.NOT_FOUND).status(false).build();
        }else{
            return GetAllCategoriesResponse.categoriesResponseBuilder().httpStatus(HttpStatus.OK).status(true).categoryList(categoryList).build();
        }
    }
}
