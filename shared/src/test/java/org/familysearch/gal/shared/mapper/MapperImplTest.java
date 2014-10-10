package org.familysearch.gal.shared.mapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.familysearch.gal.shared.mapper.model.Product;
import org.familysearch.gal.shared.mapper.model.ProductModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link org.familysearch.gal.shared.mapper.MapperImpl}
 */
public class MapperImplTest {

    private Product product;
    private ProductModel productModel;
    private UUID productId = UUID.randomUUID();
    private Mapper<Product, ProductModel> mapper;

    @Before
    public void setUp() throws Exception {

        mapper = MapperFactory.instance(Product.class, ProductModel.class);

        product = new Product();
        productModel = new ProductModel();

        product.setName("testProduct");
        product.setLocale(Locale.ENGLISH);
        product.setQuantity(1);
        product.setUuid(productId);

        productModel.setName("testProduct");
        productModel.setLocale(Locale.ENGLISH);
        productModel.setQuantity(1);
        productModel.setUuid(productId);
    }

    @Test
    public void testTo() throws Exception {
        Product product = mapper.to(productModel);
        assertProduct(product, productModel);
    }

    @Test
    public void testTo_WithObject() throws Exception {
        Product product = mapper.to(productModel, new Product());
        assertProduct(product, productModel);
    }

    @Test
    public void testFrom() throws Exception {
        ProductModel productModel = mapper.from(product);
        assertProductModel(productModel, product);
    }

    @Test
    public void testFrom_WithObject() throws Exception {
        ProductModel productModel = mapper.from(product, new ProductModel());
        assertProductModel(productModel, product);
    }

    @Test
    public void testTo_Collection() throws Exception {
        List<ProductModel> productModels = new ArrayList<ProductModel>();
        productModels.add(productModel);
        List<Product> products = mapper.to(productModels);
        assertThat(products, notNullValue());
        assertThat(products.size(), is(1));
        assertProduct(products.get(0), productModel);
    }

    @Test
    public void testFrom_Collection() throws Exception {
        List<Product> products = new ArrayList<Product>();
        products.add(product);
        List<ProductModel> productModels = mapper.from(products);
        assertThat(productModels, notNullValue());
        assertThat(productModels.size(), is(1));
        assertProductModel(productModels.get(0), product);
    }

    private void assertProductModel(ProductModel productModel, Product product) {
        assertThat(productModel, notNullValue());
        assertThat(productModel.getName(), is(product.getName()));
        assertThat(productModel.getLocale(), is(product.getLocale()));
        assertThat(productModel.getQuantity(), is(product.getQuantity()));
        assertThat(productModel.getUuid(), is(product.getUuid()));
    }

    private void assertProduct(Product product, ProductModel productModel) {
        assertThat(product, notNullValue());
        assertThat(product.getName(), is(productModel.getName()));
        assertThat(product.getLocale(), is(productModel.getLocale()));
        assertThat(product.getQuantity(), is(productModel.getQuantity()));
        assertThat(product.getUuid(), is(productModel.getUuid()));
    }
}
