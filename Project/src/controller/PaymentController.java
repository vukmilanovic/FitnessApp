package controller;

import static spark.Spark.post;
import static spark.Spark.get;

import com.google.gson.Gson;

import DTO.FeeDTO;
import services.PaymentService;

public class PaymentController {

	private static Gson g = new Gson();
	private static PaymentService paymentService = new PaymentService();
	
	public static void CreateMembershipFee() {
		post("rest/customers/createFee", (req, res) -> {
			res.type("application/json");
			FeeDTO dto = g.fromJson(req.body(), FeeDTO.class);
			return g.toJson(paymentService.CreateMembershipFee(dto));
		});
	}
	
	public static void checkIfMembershipFeeExpired() {
		get("rest/payment/check/:username", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			return g.toJson(paymentService.checkIfMembershipFeeExpired(username));
		});
	}
	
}
