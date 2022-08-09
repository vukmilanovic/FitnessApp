package services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import DAO.MembershipFeeDAO;
import DAO.PromoCodeDAO;
import DAO.TrainingHistoryDAO;
import DAO.UserDAO;
import DTO.FeeDTO;
import beans.CustomerType;
import beans.DateTime;
import beans.MembershipFee;
import beans.PromoCode;
import beans.TrainingHistory;
import beans.User;
import enums.CustomerTypeName;
import enums.MembershipFeeStatus;
import enums.MembershipFeeType;
import enums.Role;

public class PaymentService {

	private MembershipFeeDAO membershipFeeDAO;
	private UserDAO userDAO;
	private TrainingHistoryDAO trainingHistoryDAO;
	private PromoCodeDAO promoCodeDAO;
	
	public PaymentService() {
		this.userDAO = UserDAO.getInstance();
		this.membershipFeeDAO = MembershipFeeDAO.getInstance();
		this.trainingHistoryDAO = TrainingHistoryDAO.getInstance();
		this.promoCodeDAO = PromoCodeDAO.getInstance();
	}
	
	private double checkPromoCodeValidation(String enteredCode) {
		LocalDate d = LocalDate.now();
		DateTime dt = new DateTime(d.getDayOfMonth(), d.getMonthValue(), d.getYear(), 0, 0);
		for (PromoCode code : this.promoCodeDAO.getAllCodes()) {
			if(code.getCode().equals(enteredCode) && code.getUsageNumber() != 0 && code.getStartValidityDate().isBefore(dt) && code.getEndValidityDate().isAfter(dt)) {
				this.promoCodeDAO.decrementUsageNumber(code);
				return code.getDiscount();
			}
		}
		return 1;
	}
	
	public int CreateMembershipFee(FeeDTO dto) {
		
		double discount = 0;
		
		if(!dto.getCode().isEmpty()) {
			discount = checkPromoCodeValidation(dto.getCode());
			if(discount == 1) {
				return 2;
			}
		}
		
		for (MembershipFee mFee : this.membershipFeeDAO.getAllFees()) {
			if(mFee.getCustomerUsername().equals(dto.getCustomerUsername()) && mFee.getMembershipFeeStatus() == MembershipFeeStatus.Active) {
				mFee.setMembershipFeeStatus(MembershipFeeStatus.Inactive);
				break;
			}
		}
		
		User customer = this.userDAO.getUser(dto.getCustomerUsername());
		double customerTypeDiscount = customer.getCustomerType().getDiscount();
		LocalDate d = LocalDate.now();
		LocalDate dateValidity = LocalDate.now();
		
		if(dto.getType() == MembershipFeeType.Monthly) {
			dateValidity = d.plusMonths(1);
		} else if(dto.getType() == MembershipFeeType.Yearly) {
			dateValidity = d.plusYears(1);
		}
		
		DateTime dt = new DateTime(d.getDayOfMonth(), d.getMonthValue(), d.getYear(), 0, 0);
		DateTime validityDT = new DateTime(dateValidity.getDayOfMonth(), dateValidity.getMonthValue(), dateValidity.getYear(), 0, 0);
		
		double newPrice = dto.getPrice() - dto.getPrice()*discount;
		newPrice = newPrice - newPrice*customerTypeDiscount;
		int numberOfRemainingAppointments = dto.getTotalAppereances();
		
		MembershipFee newFee = new MembershipFee(this.membershipFeeDAO.getNewId(), dto.getType(), dt, validityDT, newPrice, customer.getUsername(), MembershipFeeStatus.Active, dto.getNumberOfEntries(), numberOfRemainingAppointments, dto.getTotalAppereances());
		
		this.userDAO.setCustomersFee(customer, newFee);
		if(this.membershipFeeDAO.newMembershipFee(newFee)) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public boolean checkIfMembershipFeeExpired(String username) {
		
		User user = this.userDAO.getUser(username);
		if(user.getRole() != Role.Customer) {
			return false;
		}
		
		if(checkIfUserHasActiveFee(username)) {
			LocalDateTime dt = LocalDateTime.now();
			DateTime dateTime = new DateTime(dt.getDayOfMonth(), dt.getMonthValue(), dt.getYear(), dt.getHour(), dt.getMinute());
			MembershipFee membershipFee = null;	
			for (MembershipFee fee : this.membershipFeeDAO.getAllFees()) {
				if(fee.getCustomerUsername().equals(username) && fee.getMembershipFeeStatus() == MembershipFeeStatus.Active) {
					membershipFee = fee;
					break;
				}
			}
			if(dateTime.isAfter(membershipFee.getDateAndTimeOfValidity()) || membershipFee.getNumberOfRemainingAppointements() == 0) {
				this.membershipFeeDAO.setFeeStatus(membershipFee, MembershipFeeStatus.Inactive);
				calculatePoints(membershipFee);
				return true;
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	public void calculatePoints(MembershipFee membershipFee) {
		User customer = this.userDAO.getUser(membershipFee.getCustomerUsername());
		
		//broj dolazaka (total appereances)
		double price = membershipFee.getPrice();
		int points = customer.getNumberOfCollectedPoints();
		int totalAppereances = membershipFee.getTotalAppereances();
		int attendancesNumber = totalAppereances - membershipFee.getNumberOfRemainingAppointements();
		
		//moglo je i totalAppereances - numberOfRemainingAppointments
	
		if(attendancesNumber < totalAppereances/3) {
			points -= (price/1000) * 133 * 4;
			if(points < 0) {
				points = 0;
			}
		} else {
			points += (price/1000) * attendancesNumber;
		}
		
		if(points >= 1000) {
			customer.setCustomerType(new CustomerType(CustomerTypeName.Gold, 0.15, points));
		} else if(points >= 500) {
			customer.setCustomerType(new CustomerType(CustomerTypeName.Silver, 0.10, points));
		} else if(points >= 0) {
			customer.setCustomerType(new CustomerType(CustomerTypeName.Bronze, 0.05, points));
		}
		
		this.userDAO.setNewNumberOfPoints(customer, points);
	}
	
	public boolean checkIfUserHasActiveFee(String username) {
		for (MembershipFee fee : this.membershipFeeDAO.getAllFees()) {
			if(fee.getCustomerUsername().equals(username) && fee.getMembershipFeeStatus() == MembershipFeeStatus.Active) {
				return true;
			}
		}
		return false;
	}
	
	public int numberOfUsedTrainings(MembershipFee membershipFee) {
		int attendancesNumber = 0;
		User customer = this.userDAO.getUser(membershipFee.getCustomerUsername());
		
		for (TrainingHistory tHistory : this.trainingHistoryDAO.getUsersTrainingHistory(customer.getUsername())) {
			if(tHistory.getDateAndTimeOfCheckIn().isAfter(membershipFee.getPaymentDate())) {
				attendancesNumber++;
			}
		}
		
		return attendancesNumber;
	}
	
}
