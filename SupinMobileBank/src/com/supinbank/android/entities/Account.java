package com.supinbank.android.entities;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class Account implements Serializable, KvmSerializable{
	
	private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private float balance;
    public static List<Account> listAccount;

    public Account(SoapObject soapObject) {
        setSoapObject(soapObject);
    }

    @Override
    public Object getProperty(int arg0) {
        switch (arg0) {
            case 0:
                return id;
            case 1:
                return name;
            case 2:
                return balance;
        }
        return null;
    }
    
    @Override
    public void setProperty(int arg0, Object object) {
        switch (arg0) {
            case 0:
                this.id = (Long) object;
                break;
            case 1:
                this.name = (String) object;
                break;
            case 2:
                this.balance = (Float) object;
                break;
        }
    }
    @Override
    public int getPropertyCount() {
        return 1;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void getPropertyInfo(int arg0, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (arg0) {
            case 0:
                propertyInfo.type = PropertyInfo.LONG_CLASS;
                propertyInfo.name = "id";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "name";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.OBJECT_CLASS;
                propertyInfo.name = "amount";
        }
    }

    public void setSoapObject(SoapObject soapObject) {

        try {
            this.id = Long.valueOf(soapObject.getProperty("id").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.name = soapObject.getProperty("name").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.balance = Float.valueOf(soapObject.getProperty("amount").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getId() {
		return id;
	}

	public static List<Account> getListAccount() {
		return listAccount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public static void setListAccount(List<Account> listAccount) {
		Account.listAccount = listAccount;
	}

	public String getName() {
        return name;
    }
    public Float getBalance() {
        return balance;
    }

}
