package beans;
import enums.CustomerTypeName;

public class CustomerType {

	private CustomerTypeName typeName;
	private double discount;
	private int requiredNumberOfPoints;
	
	public CustomerType() {}
	
	public CustomerType(CustomerTypeName typeName, double discount, int requiredNumberOfPoints) {
		super();
		this.typeName = typeName;
		this.discount = discount;
		this.requiredNumberOfPoints = requiredNumberOfPoints;
	}

	public CustomerTypeName getTypeName() {
		return typeName;
	}

	public void setTypeName(CustomerTypeName typeName) {
		this.typeName = typeName;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getRequiredNumberOfPoints() {
		return requiredNumberOfPoints;
	}

	public void setRequiredNumberOfPoints(int requiredNumberOfPoints) {
		this.requiredNumberOfPoints = requiredNumberOfPoints;
	}

}
