package services;

import java.util.Collection;

import beans.Product;
import beans.Products;

public class ProductService {
	
	private Products products = new Products();
	
	public Collection<Product> getProducts() {
		return this.products.getValues();
	}
	
	public Product getProduct(String id) {
		return this.products.getProduct(id);
	}
	
	public void addProduct(Product product) {
		this.products.addProduct(product);
	}

	public void editProduct(String id, Product pd) {
		this.products.edit(id, pd);
	}

	public void deleteProduct(String id) {
		this.products.delete(id);
		
	}
}
