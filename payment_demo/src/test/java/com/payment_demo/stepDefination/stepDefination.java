package com.payment_demo.stepDefination;

import org.junit.Assert;
import static org.junit.Assert.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.List;
import java.util.stream.Collectors;

public class stepDefination {

    private boolean isActive;
    private Double debtorAccountBalance;
    private String clearingHouseName;
    private String clearingHouseStatus;
    private String creditorName;
    private Long creditorAccountNumber;
    private Double amount;

    private List<String> sanctionList;
    private List<Long> fraudList;

    private boolean initiationStatus;
    private boolean qualificationStatus;
    private boolean sanctionStatus;
    private boolean fraudStatus;
    private boolean fundStatus;
    private boolean clearingHouseCheck;
    private boolean transactionStatus;

    @Given("the debtor account is {string}")
    public void debtorAccountIs(String isActiveStr)
    {
        this.isActive = Boolean.parseBoolean(isActiveStr);
        if(this.isActive)
        {
            Assert.assertTrue("Debtor account must be active", this.isActive);
        }
        else
        {
            Assert.assertTrue("Debtor account is not active", !this.isActive);
        }
    }

    @And("the debtor account balance is {string}")
    public void debtorAccountBalanceIs(String balanceStr) {
        this.debtorAccountBalance = Double.parseDouble(balanceStr);
    }

    

    @And("the clearing house name is {string}")
    public void clearingHouseNameIs(String name)
    {
        this.clearingHouseName = name;
    }

    @And("the clearing house status is {string}")
    public void clearingHouseStatusIs(String status)
    {
        this.clearingHouseStatus = status;
    }

    @And("the creditor name is {string}")
    public void creditorNameIs(String name)
    {
        this.creditorName = name;
    }

    @And("the creditor account number is {string}")
    public void creditorAccountNumberIs(String accountNumberStr) {
        this.creditorAccountNumber = Long.parseLong(accountNumberStr);
        Assert.assertNotNull("Creditor account number should be set", this.creditorAccountNumber);
    }

    

    @And("the amount is {string}")
    public void amountIs(String amountStr) {
        this.amount = Double.parseDouble(amountStr);
    }

    

    @And("the sanction list is:")
    public void sanctionListIs(io.cucumber.datatable.DataTable table)
    {
        this.sanctionList = table.asList(String.class);
    }


    @And("the fraud list is:")
    public void fraudListIs(io.cucumber.datatable.DataTable table)
    {
        List<String> strList = table.asList();
    this.fraudList = strList.stream()
                            .map(Long::parseLong)
                            .toList();
    }

    @When("the payment is initiated")
    public void paymentIsInitiated() {
        if (!isActive) {
            this.initiationStatus = false;
            Assert.fail("Payment cannot be initiated: Debtor account is not active");
        } else {
            this.initiationStatus = true;
            Assert.assertTrue("Payment initiated", initiationStatus);
        }
    }


    @Then("check qualification status for debtor account {string} with clearing house {string}, amount {string}, creditor name {string}, creditor account number {string}")
    public void qualificationCheck(String isActiveStr, String clearingHouseName, String amountStr, String creditorName, String creditorAccountNumberStr) {
        if (!initiationStatus) {
            this.qualificationStatus = false;
            Assert.fail("Skipping qualification check: Payment not initiated");
            return;
        }

        try {
            boolean isActive = Boolean.parseBoolean(isActiveStr);
            double amount = Double.parseDouble(amountStr);
            long creditorAccountNumber = Long.parseLong(creditorAccountNumberStr);

            Assert.assertTrue("Debtor must be active", isActive);
            Assert.assertNotNull(clearingHouseName);
            Assert.assertNotNull(creditorName);

            this.qualificationStatus = true;
        } catch (Exception e) {
            this.qualificationStatus = false;
            Assert.fail("Qualification failed: " + e.getMessage());
        }
    }


    

    @Then("sanction check for creditor {string}")
    public void sanctionCheck(String creditorName) {
        if (!qualificationStatus) {
            this.sanctionStatus = false;
            Assert.fail("Skipping sanction check: Qualification failed");
            return;
        }

        if (sanctionList.contains(creditorName)) {
            this.sanctionStatus = false;
            Assert.fail("Sanction check failed for creditor: " + creditorName);
        } else {
            this.sanctionStatus = true;
            Assert.assertTrue("Sanction check passed", true);
        }
    }

    @Then("fraud check for creditor account number {string}")
    public void fraudCheck(String creditorAccountNumberStr) {
        if (!sanctionStatus) {
            this.fraudStatus = false;
            Assert.fail("Skipping fraud check: Sanction check failed");
            return;
        }

        long creditorAccountNumber = Long.parseLong(creditorAccountNumberStr);
        if (fraudList.contains(creditorAccountNumber)) {
            this.fraudStatus = false;
            Assert.fail("Fraud detected for creditor account: " + creditorAccountNumber);
        } else {
            this.fraudStatus = true;
            Assert.assertTrue("Fraud check passed", true);
        }
    }




    @Then("funds are available in debtor account {string}")
    public void fundsAvailable(String debtorAccountNumberStr) {
        if (!fraudStatus) {
            this.fundStatus = false;
            Assert.fail("Skipping fund check: Fraud check failed");
            return;
        }

        long debtorAccountNumber = Long.parseLong(debtorAccountNumberStr);
        if (debtorAccountBalance >= amount) {
            this.fundStatus = true;
            Assert.assertTrue("Sufficient funds available", true);
        } else {
            this.fundStatus = false;
            Assert.fail("Insufficient funds");
        }
    }

 
    @Then("clearing house check for {string} with status {string}")
    public void clearingHouseCheck(String clearingHouseName, String status) {
        if (!fundStatus) {
            this.clearingHouseCheck = false;
            Assert.fail("Skipping clearing house check: Fund check failed");
            return;
        }

        if (clearingHouseName.equals(this.clearingHouseName) && status.equals(this.clearingHouseStatus)) {
            this.clearingHouseCheck = true;
            Assert.assertTrue("Clearing house check passed", true);
        } else {
            this.clearingHouseCheck = false;
            Assert.fail("Clearing house check failed");
        }
    }

    @Then("transaction status should be evaluated")
    public void transactionStatus() {
        if (initiationStatus && qualificationStatus && sanctionStatus && fraudStatus && fundStatus && clearingHouseCheck) {
            this.transactionStatus = true;
            Assert.assertTrue("Transaction completed successfully", true);
        } else {
            System.out.println("Transaction failed at one of the steps:");
            System.out.println("initiationStatus: " + initiationStatus);
            System.out.println("qualificationStatus: " + qualificationStatus);
            System.out.println("sanctionStatus: " + sanctionStatus);
            System.out.println("fraudStatus: " + fraudStatus);
            System.out.println("fundStatus: " + fundStatus);
            System.out.println("clearingHouseCheck: " + clearingHouseCheck);

            this.transactionStatus = false;
            Assert.fail("Transaction failed due to one or more checks failing");
        }
    }
}



