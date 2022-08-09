package controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.post;

import com.google.gson.Gson;

import beans.Product;
import services.ProductService;

public class ProductController {
	
	private static Gson g = new Gson();
	private static ProductService productService = new ProductService();
	
	public static void getProducts() {
		get("rest/products/", (req, res) -> {
			res.type("application/json");
			return g.toJson(productService.getProducts());
		});
	}
	
	public static void getProduct() {
		get("rest/products/:id", (req, res) -> {
			res.type("application/json");
			String id = req.params("id");
			return g.toJson(productService.getProduct(id));
		});
	}
	
	public static void addProduct() {
		post("rest/products/add", (req, res) -> {
			res.type("application/json");
			Product pd = g.fromJson(req.body(), Product.class);
			productService.addProduct(pd);
			return "SUCCESS";
		});
	}
	
	public static void editProduct() {
		put("rest/products/edit/:id", (req, res) -> {
			res.type("application/json");
			String id = req.params("id");
			Product pd = g.fromJson(req.body(), Product.class);
			productService.editProduct(id, pd);
			return "SUCCESS";
		});
	}
	
	public static void deleteProduct() {
		delete("rest/products/delete/:id", (req, res) -> {
			res.type("application/json");
			String id = req.params("id");
			productService.deleteProduct(id);
			return "SUCCESS";
		});
	}
}
