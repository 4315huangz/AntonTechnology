package org.antontech.dto;

import org.antontech.model.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getUser().getCompanyName(),
                product.getUser().getIndustry(),
                product.getPictureUrl()
            );
    }
}
