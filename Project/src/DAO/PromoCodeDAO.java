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

import beans.PromoCode;
import utils.GlobalPaths;

public class PromoCodeDAO {

	private static PromoCodeDAO instance = null;
	
	public static PromoCodeDAO getInstance() {
		if(instance == null)
			instance = new PromoCodeDAO();
	
		return instance;
	}
	
	private Gson g = new Gson();
	private HashMap<Integer, PromoCode> codes;
	
	public PromoCodeDAO() {
		Load();
		
//		PromoCode code1 = new PromoCode(1, "abc", new DateTime(5, 7, 2022, 0, 0), new DateTime(15, 7, 2022, 0, 0), 1, 0.10);
//		PromoCode code2 = new PromoCode(2, "123", new DateTime(5, 6, 2022, 0, 0), new DateTime(23, 6, 2022, 0, 0), 3, 0.20);
//		PromoCode code3 = new PromoCode(3, "1312", new DateTime(1, 7, 2022, 0, 0), new DateTime(22, 7, 2022, 0, 0), 3, 0.15);
//
//		this.codes.put(code1.getId(), code1);
//		this.codes.put(code2.getId(), code2);
//		this.codes.put(code3.getId(), code3);
//		
//		Save();
	}
	
	public int getNewId() {
		if(this.codes.isEmpty())
            return 1;

        int maxId = -1;
        for(int id : this.codes.keySet()) {
            if(maxId < id) {
                maxId = id;
            }
        }
        
        return maxId+1;
	}
	
	public boolean CreatePromoCode(PromoCode code) {
		for (int id : this.codes.keySet()) {
			if(code.getId() == id)
				return false;
		}
		
		this.codes.put(code.getId(), code);
		Save();
		return true;
	}
	
	public Collection<PromoCode> getAllCodes() {
		Collection<PromoCode> codes = new ArrayList<PromoCode>();
		for (PromoCode code : this.codes.values()) {
			if(code.isDeleted() == false) {
				codes.add(code);
			}
		}
		
		return codes;
	}
	
	public void decrementUsageNumber(PromoCode code) {
		code.setUsageNumber(code.getUsageNumber() - 1);
		Save();
	}
	
	public void Save() {
		String json = g.toJson(this.codes);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(GlobalPaths.codesDBPath)));
			writer.write(json);
			writer.close();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Load() {
		String json = "";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(GlobalPaths.codesDBPath)));
			String currentLine;
			while((currentLine = reader.readLine()) != null)
				json += currentLine;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		if(g.fromJson(json, new TypeToken<HashMap<Integer, PromoCode>>(){}.getType()) == null) {
			this.codes = new HashMap<Integer, PromoCode>();
		} else {
			this.codes = g.fromJson(json, new TypeToken<HashMap<Integer, PromoCode>>(){}.getType());
		}
	}
}
