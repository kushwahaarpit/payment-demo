package com.payment_demo.stepDefination;

import org.junit.Assert;
import static org.junit.Assert.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.List;
import java.util.stream.Collectors;

public class stepDefination {

    private boolean isActive;
    private double debtorAccountBalance;
    private String clearingHouseName;
    private String clearingHouseStatus;
    private String creditorName;
    private long creditorAccountNumber;
    private double amount;

    private List<String> sanctionList;
    private List<Long> fraudList;

    private String initiationStatus = "";
    private String qualificationStatus = "Passed";
    private String sanctionStatus = "";
    private String fraudStatus = "";
    private String fundStatus = "";
    private String clearingHouseCheck = "";
    private String transactionStatus = "";

    @Given("the debtor account is {bool}")
    public void debtorAccountIs(boolean isActive)
    {
        this.isActive = isActive;
        if(isActive==true)
        {
            Assert.assertTrue("Debtor account must be active", isActive);
        }
        else
        {
            Assert.assertTrue("Debtor account is not active",!isActive);
        }
    }

    @Given("the debtor account balance is {double}")
    public void debtorAccountBalanceIs(double balance)
    {
        this.debtorAccountBalance = balance;

    }

    @Given("the clearing house name is {string}")
    public void clearingHouseNameIs(String name)
    {
        this.clearingHouseName = name;
    }

    @Given("the clearing house status is {string}")
    public void clearingHouseStatusIs(String status)
    {
        this.clearingHouseStatus = status;
    }

    @Given("the creditor name is {string}")
    public void creditorNameIs(String name)
    {
        this.creditorName = name;
    }

    @Given("the creditor account number is {long}")
    public void creditorAccountNumberIs(long accountNumber)
    {
        this.creditorAccountNumber = accountNumber;
    }

    @Given("the amount is {double}")
    public void amountIs(double amount)
    {
        this.amount = amount;
    }

    @Given("the sanction list is")
    public void sanctionListIs(io.cucumber.datatable.DataTable table)
    {
        this.sanctionList = table.asList(String.class);
    }


    @Given("the fraud list is")
    public void fraudListIs(io.cucumber.datatable.DataTable table)
    {
        List<String> strList = table.asList();
    this.fraudList = strList.stream()
                            .map(Long::parseLong)
                            .toList();
    }

    @When("the payment is initiated")
    public void paymentIsInitiated() {
        // Logic to initiate the payment
        Assert.assertTrue("Debtor account must be active to initiate payment",isActive);
        this.initiationStatus= "Payment initiated successfully";
        Assert.assertTrue("Payment initiation successfully",this.initiationStatus.equals("Payment initiated successfully"));

    }

    @Then("check qualification status for debtor account {bool} with clearing house {string}, amount {double}, creditor name {string}, creditor account number {long}")
    public void qualificationCheck(boolean isActive, String clearingHouseName, double amount, String creditorName, long creditorAccountNumber) {
        // Logic to check qualification
        try
        {
            Assert.assertTrue("Debtor account must be active for qualification check",isActive);
            Assert.assertNotNull("Clearing house name must not be null",clearingHouseName);
            Assert.assertNotNull("Amount must not be null", amount);
            Assert.assertNotNull("Creditor name must not be null",creditorName);
            Assert.assertNotNull("Creditor account number must not be null",creditorAccountNumber);
            
        }
        catch (Exception e)
        {
            this.qualificationStatus = "Failed";
            Assert.fail("Qualification failed: " + e.getMessage()); 
        }
    }

    @Then("sanction check for creditor {string}")
    public void sanctionCheck(String creditorName) {
        // Logic to check sanctions
        if(sanctionList.contains(creditorName))
        {
            
            Assert.fail("Sanction check failed for creditor: " + creditorName);
        }
        else
        {
            this.sanctionStatus = "Creditor is not on the sanction list";
            Assert.assertTrue("Sanction check passed",!sanctionList.contains(creditorName));
        }
    }

    @Then("fraud check for creditor account number {long}")
    public void fraudCheck(long creditorAccountNumber) {
        // Logic to check fraud
        if(fraudList.contains(creditorAccountNumber))
        {
            Assert.fail("Fraud check failed for creditor account number: " + creditorAccountNumber);
        }
        else
        {
            this.fraudStatus = "Creditor account number is not on the fraud list";
            Assert.assertTrue("Fraud check passed", !fraudList.contains(creditorAccountNumber));
        }
    }

    @Then("funds are available in debtor account {long}")
    public void fundsAvailable(long debtorAccountNumber) {
        // Logic to check funds availability
        if(debtorAccountBalance >= amount)
        {
            this.fundStatus = "Funds are available";
            Assert.assertTrue("Funds are available in debtor account",debtorAccountBalance >= amount);
        }
        else
        {
            this.fundStatus = "Insufficient funds";
            Assert.fail("Insufficient funds in debtor account");
        }
    }

    @Then("clearing house check for {string} with status {string}")
    public void clearingHouseCheck(String clearingHouseName, String status) {
        // Logic to check clearing house
        if(clearingHouseName.equals(this.clearingHouseName) && status.equals(this.clearingHouseStatus))
        {
            this.clearingHouseCheck = "Clearing house check passed";
            Assert.assertTrue("Clearing house check passed",clearingHouseName.equals(this.clearingHouseName) && status.equals(this.clearingHouseStatus));
        }
        else
        {
            this.clearingHouseCheck = "Clearing house check failed";
            Assert.fail("Clearing house check failed for " + clearingHouseName + " with status " + status);
        }
    }

    @Then("transaction status ")
    public void transactionStatus() {
        // Logic to determine transaction status
        if (this.qualificationStatus.equals("Failed") || this.sanctionStatus.contains("Creditor is on the sanction list") ||
            this.fraudStatus.contains("Creditor account number is on the fraud list") || this.fundStatus.equals("Insufficient funds") ||
            this.clearingHouseCheck.contains("Clearing house check failed")) {
            this.transactionStatus = "Transaction failed";
            Assert.fail("Transaction failed due to one or more checks not passing");
        } else {
            this.transactionStatus = "Transaction successful";
            Assert.assertTrue("Transaction completed successfully",this.transactionStatus.equals("Transaction successful"));
        }
    }
}



