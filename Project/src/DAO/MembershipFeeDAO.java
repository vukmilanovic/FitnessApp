package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.DateTime;
import beans.MembershipFee;
import beans.User;
import enums.MembershipFeeStatus;
import enums.MembershipFeeType;
import utils.GlobalPaths;

public class MembershipFeeDAO {

	private static MembershipFeeDAO instance = null;
	
	public static MembershipFeeDAO getInstance() {
		if(instance == null)
			instance = new MembershipFeeDAO();
		
		return instance;
	}
	
	private Gson g = new Gson();
	private HashMap<Integer, MembershipFee> membershipFees;
	private UserDAO userDAO;
	
	public MembershipFeeDAO() {
		Load();
//		this.userDAO = UserDAO.getInstance();
//		this.membershipFees = new HashMap<Integer, MembershipFee>();
//		
//		DateTime dt = new DateTime(1, 7, 2022, 0, 0);
//		DateTime dt2 = new DateTime(1, 8, 2022, 0, 0);
//		User customer = this.userDAO.getUser("spikaLudi");
//		customer2 = this.userDAO.getUser("ckepatroni");
//	
//		MembershipFee fee1 = new MembershipFee(1, MembershipFeeType.Monthly, dt, dt2, 2500, customer.getUsername(), MembershipFeeStatus.Active, 1, 30, 30);
//		MembershipFee fee2 = new MembershipFee(1, MembershipFeeType.Monthly, new DateTime(1, 6, 2022, 0, 0), new DateTime(1, 7, 2022, 0, 0), 2500, customer2.getUsername(), MembershipFeeStatus.Active, 1, 5, 30);
//		
//		this.membershipFees.put(fee1.getId(), fee1);
//		this.membershipFees.put(fee2.getId(), fee2);
//		
//		Save();
	}
	
	public void setFeeStatus(MembershipFee fee, MembershipFeeStatus status) {
		fee.setMembershipFeeStatus(status);
		Save();
	}
	
	public int getNewId() {
        if(membershipFees.isEmpty())
            return 1;
        int maxId = -1;
        for(int id : membershipFees.keySet()) {
            if(maxId < id) {
                maxId = id;
            }
        }
        return maxId+1;
    }
	
	public boolean newMembershipFee(MembershipFee newMembershipFee) {
		for(int id : membershipFees.keySet()) {
			if(newMembershipFee.getId() == id) {
				return false;
			}
		}
		
		this.membershipFees.put(newMembershipFee.getId(), newMembershipFee);
		Save();
		return true;
	}
	
	public void editMembershipFee(int id, MembershipFee membershipFee) {
		this.membershipFees.put(id, membershipFee);
		Save();
	}
	
	public void deleteMembershipFee(int id) {
		for(MembershipFee fee : this.membershipFees.values()) {
			if(fee.getId() == id) {
				fee.setDeleted(true);
				break;
			}
		}
		Save();
	}
	
	public Collection<MembershipFee> getAllFees() {
		Collection<MembershipFee> fees = new ArrayList<MembershipFee>();
		for (MembershipFee membershipFee : this.membershipFees.values()) {
			if(membershipFee.isDeleted() == false)
				fees.add(membershipFee);
		}
		return fees;
	}
	
	public MembershipFee getMembershipFee(int id) {
		for (MembershipFee fee : getAllFees()) {
			if(fee.getId() == id) {
				return fee;
			}
		}
		return null;
	}
	
	private void Save() {
		String json = g.toJson(this.membershipFees);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(GlobalPaths.membershipFeesDBPath)));
			writer.write(json);
			writer.close(); 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void Load() {
		String json = "";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(GlobalPaths.membershipFeesDBPath)));
			String currentLine;
			while((currentLine = reader.readLine()) != null)
				json += currentLine;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}
		
		if(g.fromJson(json, new TypeToken<HashMap<Integer, MembershipFee>>(){}.getType()) == null) {
			this.membershipFees = new HashMap<Integer, MembershipFee>();
		} else {
			this.membershipFees = g.fromJson(json, new TypeToken<HashMap<Integer, MembershipFee>>(){}.getType());
		}
		
	}
}
