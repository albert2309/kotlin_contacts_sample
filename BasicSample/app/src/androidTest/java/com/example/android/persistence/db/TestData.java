package com.example.android.persistence.db;

import com.example.android.persistence.db.entity.ProductEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Utility class that holds values to be used for testing.
 */
public class TestData {

    static final ProductEntity PRODUCT_ENTITY = new ProductEntity(1, "name", "desc",
            3);
    static final ProductEntity PRODUCT_ENTITY2 = new ProductEntity(2, "name2", "desc2",
            20);

    static final List<ProductEntity> PRODUCTS = Arrays.asList(PRODUCT_ENTITY, PRODUCT_ENTITY2);

}
