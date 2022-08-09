package beans;

public class PromoCode extends Entity {

	private int id;
	private String code;
	private DateTime startValidityDate;
	private DateTime endValidityDate;
	private int usageNumber;
	private double discount;
	
	public PromoCode() {
		super.setDeleted(false);
	}

	public PromoCode(int id, String code, DateTime startValidityDate, DateTime endValidityDate, int usageNumber,
			double discount) {
		super.setDeleted(false);;
		this.id = id;
		this.code = code;
		this.startValidityDate = startValidityDate;
		this.endValidityDate = endValidityDate;
		this.usageNumber = usageNumber;
		this.discount = discount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public DateTime getStartValidityDate() {
		return startValidityDate;
	}

	public void setStartValidityDate(DateTime startValidityDate) {
		this.startValidityDate = startValidityDate;
	}

	public DateTime getEndValidityDate() {
		return endValidityDate;
	}

	public void setEndValidityDate(DateTime endValidityDate) {
		this.endValidityDate = endValidityDate;
	}

	public int getUsageNumber() {
		return usageNumber;
	}

	public void setUsageNumber(int usageNumber) {
		this.usageNumber = usageNumber;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
}
